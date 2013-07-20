package com.alexkorovyansky.mblock.app.base;

import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.alexkorovyansky.mblock.app.MbLockApplication;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * MbLockActivity
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class MbLockActivity extends SherlockFragmentActivity {

    @Inject
    Bus bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(MbLockTags.TAG_LIFE_CYCLE_EVENTS, getClass().getSimpleName() + " onCreate");
        ((MbLockApplication)getApplication()).inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(MbLockTags.TAG_LIFE_CYCLE_EVENTS, getClass().getSimpleName() + " onStart");
        bus.register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(MbLockTags.TAG_LIFE_CYCLE_EVENTS, getClass().getSimpleName() + " onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(MbLockTags.TAG_LIFE_CYCLE_EVENTS, getClass().getSimpleName() + " onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(MbLockTags.TAG_LIFE_CYCLE_EVENTS, getClass().getSimpleName() + " onStop");
        bus.unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(MbLockTags.TAG_LIFE_CYCLE_EVENTS, getClass().getSimpleName() + " onDestroy");
    }

    protected void postToBus(Object object) {
        bus.post(object);
    }
}
