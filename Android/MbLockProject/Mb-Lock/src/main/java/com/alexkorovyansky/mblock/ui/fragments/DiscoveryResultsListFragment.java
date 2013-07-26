package com.alexkorovyansky.mblock.ui.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ArrayAdapter;

import com.alexkorovyansky.mblock.app.base.MbLockListFragment;
import com.alexkorovyansky.mblock.model.MbLock;

import java.util.ArrayList;
import java.util.List;

/**
 * DiscoveryFragment
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class DiscoveryResultsListFragment extends MbLockListFragment{

    private ArrayList<MbLock> mItems;

    public static DiscoveryResultsListFragment newInstance(List<MbLock> results) {
        final DiscoveryResultsListFragment f = new DiscoveryResultsListFragment();
        final Bundle arguments = new Bundle();
        arguments.putParcelableArrayList("items", new ArrayList<Parcelable>(results));
        f.setArguments(arguments);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFromArguments(savedInstanceState == null ? getArguments() : savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ArrayAdapter<MbLock> adapter = new ArrayAdapter<MbLock>(getActivity(),
                android.R.layout.simple_list_item_1, mItems);

        setListAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("items", mItems);
    }

    private void initFromArguments(Bundle arguments) {
        mItems = arguments.getParcelableArrayList("items");
    }



}
