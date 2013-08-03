package com.alexkorovyansky.mblock.bluetooth.connection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.alexkorovyansky.mblock.app.Config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * BluetoothConnection
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class BluetoothConnection implements Runnable{

    private static final String TAG = BluetoothConnection.class.getSimpleName();

    public static interface Listener {
        public void onConnected();
        public void onPacketSend(byte[] packet);
        public void onPacketReceived(byte[] packet);
        public void onDisconnected();
    }

    public static class BaseListener implements Listener {

        @Override
        public void onConnected() {

        }

        @Override
        public void onPacketSend(byte[] packet) {

        }

        @Override
        public void onPacketReceived(byte[] packet) {

        }

        @Override
        public void onDisconnected() {

        }
    }
    private String mMacAdress;
    private BluetoothSocket mBluetoothSocket;
    private ByteBuffer mBuffer;
    private PacketReader mPacketReader;
    private Listener mListener;

    public BluetoothConnection(String macAdress, int bufferSize) {
        this.mMacAdress = macAdress;
        this.mBuffer = ByteBuffer.allocate(bufferSize);
        this.mListener = new BaseListener();
        this.mPacketReader = new DelimitedPacketReader("\r\n");
    }

    public void setPacketReader(PacketReader packetReader) {
        mPacketReader = packetReader;
    }

    public void setListener(Listener listener) {
        mListener = listener;
        if (mListener == null) {
            mListener = new BaseListener();
        }
    }

    @Override
    public void run() {
        Log.v(TAG, "starting bluetooth connection with " + mMacAdress);
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        final BluetoothDevice remoteDevice = bluetoothAdapter.getRemoteDevice(mMacAdress);
        try {
            mBluetoothSocket = remoteDevice.createInsecureRfcommSocketToServiceRecord(Config.MBLOCK_UUID);
            mBluetoothSocket.connect();
            final InputStream input = mBluetoothSocket.getInputStream();
            Log.v(TAG, "connection established");
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = input.read(buffer);
                    mBuffer.mark();
                    mBuffer.put(buffer, 0, bytes);
                    mBuffer.reset();

                    final byte[] caughtPacket = mPacketReader.catchPacket(mBuffer);
                    if (caughtPacket != null) {
                        Log.v(TAG, "received " + bytesToHex(caughtPacket));
                        mListener.onPacketReceived(caughtPacket);
                        mBuffer.clear();
                    } else {
                        mBuffer.position(mBuffer.position() + bytes);
                    }
                } catch (IOException e) {
                    Log.v(TAG, "connection while run", e);
                    break;
                }
            }
        } catch (IOException e) {
            Log.v(TAG, "connection error", e);
        }
    }

    public void connect() {
        new Thread(this).start();
    }

    public void sendPacket(byte[] packet) {
        try {
            mBluetoothSocket.getOutputStream().write(packet);
            mBluetoothSocket.getOutputStream().flush();
            Log.v(TAG, "sent " + bytesToHex(packet));
            mListener.onPacketSend(packet);
        } catch (IOException e) {

        }
    }

    public void disconnect() {
        if (mBluetoothSocket != null) {
            try {
                mBluetoothSocket.close();
            } catch(IOException e) {
                Log.v(TAG, "exception while disconnect", e);
            }
        }
        Log.v(TAG, "disconnected");
    }

    private static String bytesToHex(byte[] bytes) {
        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        final StringBuilder sb = new StringBuilder();
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            sb.append(hexArray[v >>> 4]);
            sb.append(hexArray[v & 0x0F]);
            sb.append(" ");
        }
        return sb.toString().trim();
    }
}
