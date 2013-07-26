package com.alexkorovyansky.mblock.services.mock;

import android.os.Handler;
import android.os.Looper;

import com.alexkorovyansky.mblock.classes.Callback;
import com.alexkorovyansky.mblock.model.MbLock;
import com.alexkorovyansky.mblock.services.DiscoveryService;
import com.alexkorovyansky.mblock.utils.ThreadUtils;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

/**
 * MockDiscoveryService
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class MockDiscoveryService implements DiscoveryService {


    private Handler mHandler;

    @Inject
    public MockDiscoveryService() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void discover(final Callback<MbLock> progressCallback, final Callback<Set<MbLock>> resultCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Set<MbLock> result = new HashSet<MbLock>();

                ThreadUtils.sleep(1000);

                final MbLock users = new MbLock("My", "mac1");
                result.add(users);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressCallback.onResult(users);
                    }
                });

                ThreadUtils.sleep(1000);

                final MbLock unknown = new MbLock("Unknown", "mac2");
                result.add(unknown);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressCallback.onResult(unknown);
                    }
                });

                ThreadUtils.sleep(2000);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        resultCallback.onResult(result);
                    }
                });
            }
        }).start();
    }

    @Override
    public void cancel() {

    }
}
