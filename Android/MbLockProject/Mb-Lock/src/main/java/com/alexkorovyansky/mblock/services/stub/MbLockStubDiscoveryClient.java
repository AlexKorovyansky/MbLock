package com.alexkorovyansky.mblock.services.stub;

import com.alexkorovyansky.mblock.classes.Callback;
import com.alexkorovyansky.mblock.model.MbLock;
import com.alexkorovyansky.mblock.model.User;
import com.alexkorovyansky.mblock.services.MbLockDiscoveryClient;
import com.alexkorovyansky.mblock.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * MbLockStubDiscoveryClient
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class MbLockStubDiscoveryClient implements MbLockDiscoveryClient {


    private final User mUser;

    @Inject
    public MbLockStubDiscoveryClient(User user) {
        mUser = user;
    }

    @Override
    public void discover(final Callback<MbLock> progressCallback, final Callback<List<MbLock>> resultCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<MbLock> result = new ArrayList<MbLock>();

                ThreadUtils.sleep(1000);
                final MbLock users = new MbLock(UUID.randomUUID().toString(), mUser.id, mUser.name);
                progressCallback.onResult(users);
                result.add(users);

                ThreadUtils.sleep(1000);
                final MbLock unknown = new MbLock(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "Unknown Person");
                progressCallback.onResult(users);
                result.add(unknown);

                ThreadUtils.sleep(2000);
                resultCallback.onResult(result);
            }
        }).start();
    }
}
