package com.alexkorovyansky.mblock.app.components.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexkorovyansky.mblock.R;
import com.alexkorovyansky.mblock.app.base.MbLockFragment;

/**
 * DiscoveryFragment
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class DiscoveryNoResultsFragment extends MbLockFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discovery_no_results, container, false);
    }
}
