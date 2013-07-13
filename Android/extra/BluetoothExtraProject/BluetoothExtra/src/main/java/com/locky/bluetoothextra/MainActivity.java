package com.locky.bluetoothextra;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.InjectView;
import butterknife.Views;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @InjectView(R.id.button_discover) Button mDiscoverButton;

    private BluetoothAdapter mBluetoothAdapter;

    private final BroadcastReceiver mDiscoveryReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                Log.v(TAG, "discovered: " + device.getName() + "\n" + device.getAddress());
                if(device.getName().contains("SAMSUNG")){
                    mBluetoothAdapter.cancelDiscovery();
                    new BluetoothClientThread(device).start();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Views.inject(this);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            finish();
        }

        mDiscoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBluetoothAdapter.startDiscovery();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mDiscoveryReceiver, filter); // Don't forget to unregister during onDestroy
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mDiscoveryReceiver);
    }
}
