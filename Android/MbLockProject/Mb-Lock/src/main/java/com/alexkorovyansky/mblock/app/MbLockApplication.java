package com.alexkorovyansky.mblock.app;

import android.app.Application;

import com.alexkorovyansky.mblock.app.modules.StubModule;

import dagger.ObjectGraph;

/**
 * MbLockApplication
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class MbLockApplication extends Application {

    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
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

    private void resetObjectGraph() {
        mObjectGraph = ObjectGraph.create(new StubModule());
    }
}
