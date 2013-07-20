package com.alexkorovyansky.mblock.services.stub;

import com.alexkorovyansky.mblock.classes.Callback;
import com.alexkorovyansky.mblock.model.MbLock;
import com.alexkorovyansky.mblock.model.User;
import com.alexkorovyansky.mblock.services.MbLockDiscoveryService;
import com.alexkorovyansky.mblock.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * MbLockStubDiscoveryService
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class MbLockStubDiscoveryService implements MbLockDiscoveryService {

    @Inject
    User user;

    @Override
    public void discover(final Callback<MbLock> progressCallback, final Callback<List<MbLock>> resultCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<MbLock> result = new ArrayList<MbLock>();

                ThreadUtils.sleep(1000);
                final MbLock users = new MbLock(UUID.randomUUID().toString(), user.id, user.name);
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
