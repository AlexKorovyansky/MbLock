package com.alexkorovyansky.mblock.app.modules.mock;

import com.alexkorovyansky.mblock.services.DiscoveryService;
import com.alexkorovyansky.mblock.app.components.services.MbLocksBoundService;
import com.alexkorovyansky.mblock.services.mock.MockDiscoveryService;

import dagger.Module;
import dagger.Provides;

/**
 * MockDiscoveryModule
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
@Module(
        injects = { MbLocksBoundService.class }
)
public class MockDiscoveryModule {

    @Provides
    public DiscoveryService provideDiscoveryClient() {
        return new MockDiscoveryService();
    }

}
