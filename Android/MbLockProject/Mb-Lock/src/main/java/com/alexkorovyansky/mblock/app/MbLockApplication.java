package com.alexkorovyansky.mblock.app;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.alexkorovyansky.mblock.app.modules.AppModule;
import com.alexkorovyansky.mblock.app.modules.real.RealDiscoveryModule;

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

    public static void inject(Activity activity) {
        ((MbLockApplication)activity.getApplication()).injectInner(activity);
    }

    public static void inject(Service service) {
        ((MbLockApplication)service.getApplication()).injectInner(service);
    }

    public static void inject(Fragment fragment) {
        ((MbLockApplication)fragment.getActivity().getApplication()).injectInner(fragment);
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

    private void injectInner(Object component) {
        getObjectGraph().inject(component);
    }
}
