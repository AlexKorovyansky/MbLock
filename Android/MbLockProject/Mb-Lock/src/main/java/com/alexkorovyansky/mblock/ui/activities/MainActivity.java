package com.alexkorovyansky.mblock.ui.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.actionbarsherlock.view.Menu;
import com.alexkorovyansky.mblock.R;
import com.alexkorovyansky.mblock.app.base.MbLockActivity;
import com.alexkorovyansky.mblock.app.events.DiscoveryFinishedEvent;
import com.alexkorovyansky.mblock.app.events.MakeDiscoveryEvent;
import com.alexkorovyansky.mblock.services.MbLockBoundService;
import com.alexkorovyansky.mblock.ui.fragments.DiscoveryFragment;
import com.alexkorovyansky.mblock.ui.fragments.DiscoveryNoResultsFragment;
import com.squareup.otto.Subscribe;

/**
 * MainActivity
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class MainActivity extends MbLockActivity {

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            postToBus(new MakeDiscoveryEvent());
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_placeholder, new DiscoveryFragment(), "discovery")
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        bindService(new Intent(this, MbLockBoundService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        unbindService(mServiceConnection);
    }

    @Subscribe
    public void discoveryFinished(DiscoveryFinishedEvent discoveryFinishedEvent){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_placeholder, new DiscoveryNoResultsFragment(), "no_results")
                .addToBackStack("no_results")
                .commit();
    }

}
