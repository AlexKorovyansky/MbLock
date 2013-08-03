package com.alexkorovyansky.mblock.app.modules;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.alexkorovyansky.mblock.app.MbLockApplication;
import com.alexkorovyansky.mblock.app.components.ui.fragments.ControlFragment;
import com.alexkorovyansky.mblock.app.components.ui.fragments.DiscoveryFragment;
import com.alexkorovyansky.mblock.app.components.ui.fragments.DiscoveryNoResultsFragment;
import com.alexkorovyansky.mblock.app.components.ui.fragments.DiscoveryResultsListFragment;
import com.alexkorovyansky.mblock.model.User;
import com.alexkorovyansky.mblock.app.components.services.MbLocksBoundService;
import com.alexkorovyansky.mblock.app.components.ui.activities.MainActivity;
import com.squareup.otto.Bus;

import java.util.UUID;

import javax.inject.Singleton;

import butterknife.Views;
import dagger.Module;
import dagger.Provides;

/**
 * MockDiscoveryModule
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
@Module(
        injects = {
                MbLocksBoundService.class,
                MainActivity.class,
                DiscoveryFragment.class, DiscoveryNoResultsFragment.class,
                DiscoveryResultsListFragment.class, ControlFragment.class
        }
)
public class AppModule {

    private MbLockApplication app;

    public AppModule(MbLockApplication app) {
        Views.setDebug(true);
        this.app = app;
    }

    @Provides
    public Context provideContext() {
        return app;
    }

    @Provides
    public User provideUser() {
        final User user = new User();
        user.id = UUID.randomUUID().toString();
        user.name = "Alex Korovyansky";
        user.email = "korovyansk@gmail.com";
        return user;
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
