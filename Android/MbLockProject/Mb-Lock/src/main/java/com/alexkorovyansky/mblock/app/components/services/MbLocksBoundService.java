package com.alexkorovyansky.mblock.app.components.services;

import android.os.Handler;

import com.alexkorovyansky.mblock.app.base.MbLockService;
import com.alexkorovyansky.mblock.app.events.DiscoveryFinishedEvent;
import com.alexkorovyansky.mblock.app.events.MakeDiscoveryEvent;
import com.alexkorovyansky.mblock.services.Callback;
import com.alexkorovyansky.mblock.model.MbLock;
import com.alexkorovyansky.mblock.services.DiscoveryService;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Set;

import javax.inject.Inject;

/**
 * MbLockService
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class MbLocksBoundService extends MbLockService{

    @Inject
    DiscoveryService mbLockDiscoveryService;

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
            }, new Callback<Set<MbLock>>() {
                @Override
                public void onResult(final Set<MbLock> mbLocks) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            postToBus(new DiscoveryFinishedEvent(mbLocks, null));
                        }
                    });
                }

                @Override
                public void onError(Exception e) {
                }
            }
        );
    }

    public void getInfo() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mbLockDiscoveryService.cancel();
    }
}
