package com.alexkorovyansky.mblock.app.base;

import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * MbLockActivity
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class MbLockActivity extends SherlockFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(MbLockTags.TAG_LIFE_CYCLE_EVENTS, getClass().getSimpleName() + " onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(MbLockTags.TAG_LIFE_CYCLE_EVENTS, getClass().getSimpleName() + " onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(MbLockTags.TAG_LIFE_CYCLE_EVENTS, getClass().getSimpleName() + " onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(MbLockTags.TAG_LIFE_CYCLE_EVENTS, getClass().getSimpleName() + " onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(MbLockTags.TAG_LIFE_CYCLE_EVENTS, getClass().getSimpleName() + " onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(MbLockTags.TAG_LIFE_CYCLE_EVENTS, getClass().getSimpleName() + " onDestroy");
    }
}
