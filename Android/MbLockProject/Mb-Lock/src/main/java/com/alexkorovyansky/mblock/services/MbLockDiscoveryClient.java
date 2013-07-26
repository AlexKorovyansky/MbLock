package com.alexkorovyansky.mblock.services;

import com.alexkorovyansky.mblock.classes.Callback;
import com.alexkorovyansky.mblock.model.MbLock;

import java.util.List;

/**
 * MbLockDiscoveryClient
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public interface MbLockDiscoveryClient {
    public void discover(Callback<MbLock> progressCallback, Callback<List<MbLock>> resultCallback);
}
