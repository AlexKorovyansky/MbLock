package com.alexkorovyansky.mblock.app.events;

import com.alexkorovyansky.mblock.model.MbLock;

import java.util.List;

/**
 * MakeDiscoveryEvent
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class DiscoveryFinishedEvent {
    public List<MbLock> discoveredMbLocks;
    public Exception exception;

    public DiscoveryFinishedEvent(List<MbLock> discoveredMbLocks, Exception exception) {
        this.discoveredMbLocks = discoveredMbLocks;
        this.exception = exception;
    }
}
