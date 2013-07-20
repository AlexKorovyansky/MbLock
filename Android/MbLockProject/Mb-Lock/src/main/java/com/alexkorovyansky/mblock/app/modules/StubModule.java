package com.alexkorovyansky.mblock.app.modules;

import android.os.Handler;
import android.os.Looper;

import com.alexkorovyansky.mblock.model.User;
import com.alexkorovyansky.mblock.services.MbLockBoundService;
import com.alexkorovyansky.mblock.services.MbLockDiscoveryService;
import com.alexkorovyansky.mblock.services.stub.MbLockStubDiscoveryService;
import com.alexkorovyansky.mblock.ui.activities.MainActivity;
import com.squareup.otto.Bus;

import java.util.UUID;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * StubModule
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
@Module(
        injects = { MbLockBoundService.class, MainActivity.class, MbLockStubDiscoveryService.class }
)
public class StubModule {

    @Provides
    public User provideUser() {
        final User user = new User();
        user.id = UUID.randomUUID().toString();
        user.name = "Alex Korovyansky";
        user.email = "korovyansk@gmail.com";
        return user;
    }

    @Provides
    public MbLockDiscoveryService provideDiscoveryService() {
        return new MbLockStubDiscoveryService();
    }

    @Provides @Singleton
    Handler provideHandler() {
        return new Handler(Looper.getMainLooper());
    }

    @Provides @Singleton
    Bus provideBus() {
        return new Bus();
    }
}
