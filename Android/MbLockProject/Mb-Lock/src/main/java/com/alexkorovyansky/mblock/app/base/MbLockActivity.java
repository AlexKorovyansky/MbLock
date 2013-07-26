package com.alexkorovyansky.mblock.app.base;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
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
        MbLockApplication.logLifeCycle(this, "onCreate " + (savedInstanceState == null ? "new" : "restored") + " " + getIntent());
        ((MbLockApplication)getApplication()).inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        MbLockApplication.logLifeCycle(this, "onStart");
        bus.register(this);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        MbLockApplication.logLifeCycle(this, "onRestoreInstanceState");
    }

    @Override
    public void onResume() {
        super.onResume();
        MbLockApplication.logLifeCycle(this, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        MbLockApplication.logLifeCycle(this, "onPause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MbLockApplication.logLifeCycle(this, "onSaveInstanceState");
    }

    @Override
    public void onStop() {
        super.onStop();
        MbLockApplication.logLifeCycle(this, "onStop");
        bus.unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MbLockApplication.logLifeCycle(this, "onDestroy");
    }

    protected void postToBus(Object object) {
        bus.post(object);
    }
}
