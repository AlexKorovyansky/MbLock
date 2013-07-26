package com.alexkorovyansky.mblock.services;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.ParcelUuid;
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

    private List<BluetoothDevice> mDiscoveredDevices = new ArrayList<BluetoothDevice>();

    private final BroadcastReceiver mDiscoveryReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                mDiscoveredDevices.clear();
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
                    }
                } else {
                    Log.v(TAG, "empty");
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                for (BluetoothDevice discoveredDevice: mDiscoveredDevices) {
                    discoveredDevice.fetchUuidsWithSdp();
                }
                mDiscoveredDevices.clear();
                Log.v(TAG, "--discovery finished");
            }
        }
    };

    private BluetoothAdapter mBluetoothAdapter;
    private Context mContext;
    private List<MbLock> mDiscoveredLocks;
    private Callback<List<MbLock>> mResultCallback;

    public BluetoothDiscoveryService(Context context) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mContext = context;
        mDiscoveredLocks = new ArrayList<MbLock>();
    }

    @Override
    public void discover(Callback<MbLock> progressCallback, Callback<List<MbLock>> resultCallback) {
        if (mBluetoothAdapter.isDiscovering()) {
            throw new RuntimeException("cannot start discovery, because discovery is already running");
        }
        mResultCallback = resultCallback;

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_UUID);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        mContext.registerReceiver(mDiscoveryReceiver, intentFilter);
        mBluetoothAdapter.startDiscovery();
    }

    @Override
    public void cancel() {
        if(mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
            mContext.unregisterReceiver(mDiscoveryReceiver);
        }
    }

    private void stopDiscovery() {
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
            mContext.unregisterReceiver(mDiscoveryReceiver);
        }
    }
}
