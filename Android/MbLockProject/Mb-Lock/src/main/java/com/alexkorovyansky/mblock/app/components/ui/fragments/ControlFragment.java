package com.alexkorovyansky.mblock.app.components.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexkorovyansky.mblock.R;
import com.alexkorovyansky.mblock.app.base.MbLockFragment;
import com.alexkorovyansky.mblock.bluetooth.connection.BluetoothConnection;
import com.alexkorovyansky.mblock.model.MbLock;

import butterknife.OnClick;
import butterknife.Views;

/**
 * DiscoveryFragment
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class ControlFragment extends MbLockFragment {

    private static final String TAG = ControlFragment.class.getSimpleName();

    private MbLock mMbLock;
    private BluetoothConnection mBluetoothConnection;

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
        mBluetoothConnection = new BluetoothConnection(mMbLock.macAddress, 2048);
        mBluetoothConnection.connect();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_control, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Views.inject(this, getActivity());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("mbLock", mMbLock);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBluetoothConnection.disconnect();
    }

    private void initFromArguments(Bundle arguments) {
        mMbLock = arguments.getParcelable("mbLock");
    }

    @OnClick(R.id.control_open)
    public void open() {
        Log.v(TAG, "click open");
        mBluetoothConnection.sendPacket("open\r\n".getBytes());
    }

    @OnClick(R.id.control_close)
    public void close() {
        Log.v(TAG, "click close");
        mBluetoothConnection.sendPacket("close\r\n".getBytes());
    }


}
