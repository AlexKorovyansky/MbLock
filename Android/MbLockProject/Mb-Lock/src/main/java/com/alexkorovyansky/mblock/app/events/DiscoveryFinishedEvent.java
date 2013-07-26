package com.alexkorovyansky.mblock.app.events;

import com.alexkorovyansky.mblock.model.MbLock;

import java.util.Set;

/**
 * MakeDiscoveryEvent
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class DiscoveryFinishedEvent {
    public Set<MbLock> discoveredMbLocks;
    public Exception exception;

    public DiscoveryFinishedEvent(Set<MbLock> discoveredMbLocks, Exception exception) {
        this.discoveredMbLocks = discoveredMbLocks;
        this.exception = exception;
    }
}
