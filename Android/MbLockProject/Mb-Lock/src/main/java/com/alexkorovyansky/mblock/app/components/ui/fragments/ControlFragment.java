package com.alexkorovyansky.mblock.app.components.ui.fragments;

import android.os.Bundle;

import com.alexkorovyansky.mblock.app.base.MbLockFragment;
import com.alexkorovyansky.mblock.model.MbLock;

/**
 * DiscoveryFragment
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class ControlFragment extends MbLockFragment {

    private MbLock mMbLock;

    public static ControlFragment newInstance(MbLock mbLock) {
        final ControlFragment f = new ControlFragment();
        final Bundle arguments = new Bundle();
        arguments.putParcelable("mbLock", mbLock);
        f.setArguments(arguments);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFromArguments(savedInstanceState == null ? getArguments() : savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("mbLock", mMbLock);
    }

    private void initFromArguments(Bundle arguments) {
        mMbLock = arguments.getParcelable("mbLock");
    }



}
