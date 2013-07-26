package com.alexkorovyansky.mblock.services;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alexkorovyansky.mblock.classes.Callback;
import com.alexkorovyansky.mblock.model.MbLock;

import java.util.ArrayList;
import java.util.List;

/**
 * BluetoothDiscoveryService
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class BluetoothDiscoveryService implements DiscoveryService {

    public static final String TAG = BluetoothDiscoveryService.class.getSimpleName();

    private final BroadcastReceiver mDiscoveryReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                Log.v(TAG, "discovered: " + device.getName() + "\n" + device.getAddress());
                mDiscoveredLocks.add(new MbLock("SET_ME", device.getName(), "SET_ME", "SET_ME"));
            }
        }
    };

    private BluetoothAdapter mBluetoothAdapter;
    private Context mContext;
    private List<MbLock> mDiscoveredLocks;
    private Callback<List<MbLock>> mResultCallback;

    private Handler mHandler;

    private Runnable mDiscoveryTimeoutRunnable = new Runnable() {
        @Override
        public void run() {
            discoveryTimeout();
        }
    };

    public BluetoothDiscoveryService(Context context) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mContext = context;
        mDiscoveredLocks = new ArrayList<MbLock>();
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void discover(Callback<MbLock> progressCallback, Callback<List<MbLock>> resultCallback) {
        if (mBluetoothAdapter.isDiscovering()) {
            throw new RuntimeException("cannot start discovery, because discovery is already running");
        }
        mResultCallback = resultCallback;
        mDiscoveredLocks.clear();
        mContext.registerReceiver(mDiscoveryReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        mHandler.postDelayed(mDiscoveryTimeoutRunnable, 5000);
        mBluetoothAdapter.startDiscovery();
    }

    @Override
    public void cancel() {
        stopDiscovery();
    }

    private void discoveryTimeout() {
        stopDiscovery();
        mResultCallback.onResult(mDiscoveredLocks);
    }

    private void stopDiscovery() {
        if (mBluetoothAdapter.isDiscovering()) {
            mHandler.removeCallbacks(mDiscoveryTimeoutRunnable);
            mBluetoothAdapter.cancelDiscovery();
            mContext.unregisterReceiver(mDiscoveryReceiver);
        }
    }
}
