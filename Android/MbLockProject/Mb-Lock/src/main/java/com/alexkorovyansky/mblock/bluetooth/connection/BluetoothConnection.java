package com.alexkorovyansky.mblock.bluetooth.connection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.alexkorovyansky.mblock.app.Config;

import java.io.IOException;
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

    private String mMacAdress;
    private BluetoothSocket mBluetoothSocket;
    private ByteBuffer mBuffer;
    private PacketReader mPacketReader;
    private Listener mListener;

    public BluetoothConnection(String macAdress, int bufferSize) {
        this.mMacAdress = macAdress;
        this.mBuffer = ByteBuffer.allocate(bufferSize);
    }

    public void setPacketReader(PacketReader packetReader) {
        mPacketReader = packetReader;
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    @Override
    public void run() {
        Log.v(TAG, "starting bluetooth connection with " + mMacAdress);
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        final BluetoothDevice remoteDevice = bluetoothAdapter.getRemoteDevice(mMacAdress);
        try {
            mBluetoothSocket = remoteDevice.createInsecureRfcommSocketToServiceRecord(Config.MBLOCK_UUID);
            Log.v(TAG, "connection established");
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = mBluetoothSocket.getInputStream().read(buffer);
                    mBuffer.mark();
                    mBuffer.put(buffer, 0, bytes);
                    mBuffer.reset();

                    final byte[] caughtPacket = mPacketReader.catchPacket(mBuffer);
                    if (caughtPacket != null) {
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
}
