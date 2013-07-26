package com.alexkorovyansky.mblock.app.base;

import android.app.Application;
import android.util.Log;

import com.alexkorovyansky.mblock.app.modules.AppModule;
import com.alexkorovyansky.mblock.app.modules.RealDiscoveryModule;

import dagger.ObjectGraph;

/**
 * MbLockApplication
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class MbLockApplication extends Application {

    /*package*/ static final String TAG_LIFE_CYCLE = MbLockApplication.class.getSimpleName() + ".lifecycle";

    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        MbLockApplication.logLifeCycle(this, "---onCreate---");
    }

    public void inject(Object component) {
        getObjectGraph().inject(component);
    }

    public synchronized ObjectGraph getObjectGraph() {
        if (mObjectGraph == null) {
            resetObjectGraph();
        }
        return mObjectGraph;
    }

    public static void logLifeCycle(Object instance, String method) {
        Log.v(TAG_LIFE_CYCLE, instance.getClass().getSimpleName() + " " + method);
    }

    private void resetObjectGraph() {
        mObjectGraph = ObjectGraph.create(new AppModule(this), new RealDiscoveryModule());
    }
}
