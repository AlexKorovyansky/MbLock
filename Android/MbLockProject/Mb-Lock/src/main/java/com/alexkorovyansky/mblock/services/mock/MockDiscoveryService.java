package com.alexkorovyansky.mblock.services.mock;

import com.alexkorovyansky.mblock.classes.Callback;
import com.alexkorovyansky.mblock.model.MbLock;
import com.alexkorovyansky.mblock.model.User;
import com.alexkorovyansky.mblock.services.DiscoveryService;
import com.alexkorovyansky.mblock.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * MockDiscoveryService
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class MockDiscoveryService implements DiscoveryService {


    private final User mUser;

    @Inject
    public MockDiscoveryService(User user) {
        mUser = user;
    }

    @Override
    public void discover(final Callback<MbLock> progressCallback, final Callback<List<MbLock>> resultCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<MbLock> result = new ArrayList<MbLock>();

                ThreadUtils.sleep(1000);
                final MbLock users = new MbLock(UUID.randomUUID().toString(), "My", mUser.id, mUser.name);
                progressCallback.onResult(users);
                result.add(users);

                ThreadUtils.sleep(1000);
                final MbLock unknown = new MbLock(UUID.randomUUID().toString(), "Unknown", UUID.randomUUID().toString(), "Unknown Person");
                progressCallback.onResult(users);
                result.add(unknown);

                ThreadUtils.sleep(2000);
                resultCallback.onResult(result);
            }
        }).start();
    }

    @Override
    public void cancel() {

    }
}
