package com.alexkorovyansky.mblock.app.modules.real;

import android.content.Context;

import com.alexkorovyansky.mblock.services.real.BluetoothDiscoveryService;
import com.alexkorovyansky.mblock.services.DiscoveryService;
import com.alexkorovyansky.mblock.app.components.services.MbLocksBoundService;

import dagger.Module;
import dagger.Provides;

/**
 * MockDiscoveryModule
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
@Module(
        injects = { MbLocksBoundService.class },
        library = true
)
public class RealDiscoveryModule {

    @Provides
    public DiscoveryService provideDiscoveryService(Context context) {
        return new BluetoothDiscoveryService(context);
    }

}
