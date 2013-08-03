package com.alexkorovyansky.mblock.app.events;

import com.alexkorovyansky.mblock.model.MbLock;

/**
 * DiscoveryResultItemSelectedEvent
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class DiscoveryResultItemSelectedEvent {
    public final MbLock mbLock;

    public DiscoveryResultItemSelectedEvent(MbLock mbLock) {
        this.mbLock = mbLock;
    }
}
