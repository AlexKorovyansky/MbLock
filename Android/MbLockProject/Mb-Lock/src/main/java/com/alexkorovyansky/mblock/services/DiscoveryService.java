package com.alexkorovyansky.mblock.services;

import com.alexkorovyansky.mblock.model.MbLock;

import java.util.Set;

/**
 * DiscoveryService
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public interface DiscoveryService {
    public void discover(Callback<MbLock> progressCallback, Callback<Set<MbLock>> resultCallback);
    public void cancel();
}
