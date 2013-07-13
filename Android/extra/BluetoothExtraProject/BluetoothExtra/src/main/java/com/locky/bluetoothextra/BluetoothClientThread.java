package com.locky.bluetoothextra;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by akorovyansky on 7/13/13.
 */
public class BluetoothClientThread extends Thread{

    public static final String TAG = BluetoothClientThread.class.getSimpleName();

    private final BluetoothSocket mSocket;
    private final BluetoothDevice mDevice;

    public BluetoothClientThread(BluetoothDevice device) {
        // Use a temporary object that is later assigned to mSocket,
        // because mSocket is final
        BluetoothSocket tmp = null;
        mDevice = device;

        // Get a BluetoothSocket to connect with the given BluetoothDevice
//        try {
//            // MY_UUID is the app's UUID string, also used by the server code
//            Method m = device.getClass().getMethod
//                    ("createRfcommSocket", new Class[] { int.class });
//
//            tmp = (BluetoothSocket) m.invoke(device, 1);
//
//        } catch (NoSuchMethodException e){
//
//        } catch (IllegalAccessException e){
//
//        } catch (InvocationTargetException e){
//
//        }

        try {
            // MY_UUID is the app's UUID string, also used by the server code

            tmp = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));

        } catch (IOException e){

        }

        mSocket = tmp;
    }

    @Override
    public void run() {

        try{
            Thread.sleep(3000);
        } catch (InterruptedException e){

        }

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mSocket.connect();
            Log.v(TAG, "connected");

            mSocket.getOutputStream().write("hello".getBytes());
            mSocket.getOutputStream().flush();
            Log.v(TAG, "sent");

        } catch (IOException e) {
            Log.w(TAG, "exception while run", e);
        }

        try {
            mSocket.close();
        } catch (IOException e) {
            Log.w(TAG, "exception while close", e);
        }

    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mSocket.close();
        } catch (IOException e) {
        }
    }
}
