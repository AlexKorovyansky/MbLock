package com.alexkorovyansky.mblock.services.real;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import android.util.Log;

import com.alexkorovyansky.mblock.app.Config;
import com.alexkorovyansky.mblock.model.MbLock;
import com.alexkorovyansky.mblock.services.Callback;
import com.alexkorovyansky.mblock.services.DiscoveryService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Log.v(TAG, "--discovery started");
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.v(TAG, "discovered: " + device.getName() + " " + device.getAddress());
                mDiscoveredDevices.add(device);
                Log.v(TAG, "known uuids: ");
                if (device.getUuids() != null && device.getUuids().length > 0) {
                    for (ParcelUuid parcelUuid: device.getUuids()) {
                        Log.v(TAG, parcelUuid.getUuid().toString());
                    }
                } else {
                    Log.v(TAG, "empty");
                }
            } else if (BluetoothDevice.ACTION_UUID.equals(action)) {
                final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.v(TAG, "discovered uuids for " + device.getName());
                if (device.getUuids() != null && device.getUuids().length > 0) {
                    for (ParcelUuid parcelUuid: device.getUuids()) {
                        Log.v(TAG, parcelUuid.getUuid().toString());
                        if (Config.MBLOCK_UUID.equals(parcelUuid.getUuid())) {
                            mDiscoveredLocks.add(new MbLock(device.getName(), device.getAddress()));
                        }
                    }
                } else {
                    Log.v(TAG, "empty");
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                if (mDiscoveredDevices.size() > 0) {
                    mHandler.postDelayed(mFetchUuidTimeoutRunnable, 10000);
                    for (BluetoothDevice discoveredDevice: mDiscoveredDevices) {
                        discoveredDevice.fetchUuidsWithSdp();
                    }
                } else {
                    mDiscoveryRunning = false;
                    mContext.unregisterReceiver(mDiscoveryReceiver);
                    mResultCallback.onResult(new HashSet<MbLock>());
                }
                Log.v(TAG, "--discovery finished");
            }
        }
    };

    private BluetoothAdapter mBluetoothAdapter;
    private Context mContext;
    private List<BluetoothDevice> mDiscoveredDevices = new ArrayList<BluetoothDevice>();
    private Set<MbLock> mDiscoveredLocks;
    private Callback<Set<MbLock>> mResultCallback;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private boolean mDiscoveryRunning;

    private Runnable mFetchUuidTimeoutRunnable = new Runnable() {
        @Override
        public void run() {
            timeoutFetchUuid();
        }
    };

    public BluetoothDiscoveryService(Context context) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mContext = context;
        mDiscoveredLocks = new HashSet<MbLock>();
        mDiscoveryRunning = false;
    }

    @Override
    public void discover(Callback<MbLock> progressCallback, Callback<Set<MbLock>> resultCallback) {
        if (mDiscoveryRunning) {
            throw new RuntimeException("cannot start discovery, because discovery is already running");
        }

        mDiscoveryRunning = true;

        mResultCallback = resultCallback;

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_UUID);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        mDiscoveredDevices.clear();
        mDiscoveredLocks.clear();

        mContext.registerReceiver(mDiscoveryReceiver, intentFilter);
        mBluetoothAdapter.startDiscovery();
    }

    @Override
    public void cancel() {
        if (mDiscoveryRunning) {
            mBluetoothAdapter.cancelDiscovery();
            mContext.unregisterReceiver(mDiscoveryReceiver);
        }
        mHandler.removeCallbacks(mFetchUuidTimeoutRunnable);
    }

    private void timeoutFetchUuid(){
        mDiscoveryRunning = false;
        mContext.unregisterReceiver(mDiscoveryReceiver);
        mDiscoveryRunning = false;
        mResultCallback.onResult(mDiscoveredLocks);
    }
}
