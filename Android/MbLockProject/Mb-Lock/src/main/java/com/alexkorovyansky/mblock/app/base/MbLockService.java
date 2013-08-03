package com.alexkorovyansky.mblock.app.base;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.alexkorovyansky.mblock.app.MbLockApplication;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * MbLockService
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class MbLockService extends Service {

    @Inject Bus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        MbLockApplication.logLifeCycle(this, "onCreate");
        ((MbLockApplication)getApplication()).inject(this);
        bus.register(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        MbLockApplication.logLifeCycle(this, "onBind " + intent);
        return new Binder();
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        MbLockApplication.logLifeCycle(this, "onRebind " + intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MbLockApplication.logLifeCycle(this, "onStartCommand " + intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MbLockApplication.logLifeCycle(this, "onDestroy");
        bus.unregister(this);
    }

    protected void postToBus(Object event) {
        bus.post(event);
    }

}
