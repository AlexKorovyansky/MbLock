package com.alexkorovyansky.mblock.app.base;

import android.app.Activity;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragment;
import com.alexkorovyansky.mblock.app.MbLockApplication;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * MbLockFragment
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class MbLockFragment extends SherlockFragment {

    @Inject Bus bus;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MbLockApplication.logLifeCycle(this, "onAttach to " + activity.getClass().getSimpleName());
        MbLockApplication.inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MbLockApplication.logLifeCycle(this, "onCreate " + (savedInstanceState == null ? "new " : "restored"));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MbLockApplication.logLifeCycle(this, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        MbLockApplication.logLifeCycle(this, "onStart");
        bus.register(this);
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

    @Override
    public void onDetach() {
        super.onDetach();
        MbLockApplication.logLifeCycle(this, "onDetach");
    }

    protected void postToBus(Object event) {
        bus.post(event);
    }
}
