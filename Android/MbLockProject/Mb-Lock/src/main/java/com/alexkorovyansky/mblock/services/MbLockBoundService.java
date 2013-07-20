package com.alexkorovyansky.mblock.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import com.alexkorovyansky.mblock.app.MbLockApplication;
import com.alexkorovyansky.mblock.app.events.DiscoveryFinishedEvent;
import com.alexkorovyansky.mblock.app.events.MakeDiscoveryEvent;
import com.alexkorovyansky.mblock.classes.Callback;
import com.alexkorovyansky.mblock.model.MbLock;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

/**
 * MbLockBoundService
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class MbLockBoundService extends Service {

    @Inject
    MbLockDiscoveryService mbLockDiscoveryService;

    @Inject
    Handler handler;

    @Inject
    Bus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        ((MbLockApplication)getApplication()).inject(this);
        ((MbLockApplication)getApplication()).inject(mbLockDiscoveryService);
        bus.register(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Subscribe
    public void makeDiscovery(MakeDiscoveryEvent makeDiscoveryEvent) {
        mbLockDiscoveryService.discover(new Callback<MbLock>() {
                @Override
                public void onResult(MbLock mbLock) {
                }

                @Override
                public void onError(Exception e) {
                }
            }, new Callback<List<MbLock>>() {
                @Override
                public void onResult(final List<MbLock> mbLocks) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            bus.post(new DiscoveryFinishedEvent(mbLocks, null));
                        }
                    });
                }

                @Override
                public void onError(Exception e) {
                }
            }
        );
    }
}
