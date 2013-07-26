package com.alexkorovyansky.mblock.app.modules;

import android.os.Handler;
import android.os.Looper;

import com.alexkorovyansky.mblock.model.User;
import com.alexkorovyansky.mblock.services.MbLockDiscoveryClient;
import com.alexkorovyansky.mblock.services.MbLocksService;
import com.alexkorovyansky.mblock.services.stub.MbLockStubDiscoveryClient;
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
        injects = { MbLocksService.class, MainActivity.class }
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
    public MbLockDiscoveryClient provideDiscoveryClient(User user) {
        return new MbLockStubDiscoveryClient(user);
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
