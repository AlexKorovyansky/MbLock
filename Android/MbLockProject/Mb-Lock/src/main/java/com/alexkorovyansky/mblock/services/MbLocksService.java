package com.alexkorovyansky.mblock.services;

import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import com.alexkorovyansky.mblock.app.base.MbLockApplication;
import com.alexkorovyansky.mblock.app.base.MbLockService;
import com.alexkorovyansky.mblock.app.events.DiscoveryFinishedEvent;
import com.alexkorovyansky.mblock.app.events.MakeDiscoveryEvent;
import com.alexkorovyansky.mblock.classes.Callback;
import com.alexkorovyansky.mblock.model.MbLock;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

/**
 * MbLockService
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class MbLocksService extends MbLockService{

    @Inject
    MbLockDiscoveryClient mbLockDiscoveryService;

    @Inject
    Handler handler;

    @Inject
    Bus bus;

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
