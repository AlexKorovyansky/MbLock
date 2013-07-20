package com.alexkorovyansky.mblock.model;

/**
 * MbLock
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class MbLock {
    public String id;
    public String majorId;
    public String majorName;

    public MbLock(String id, String majorId, String majorName) {
        this.id = id;
        this.majorId = majorId;
        this.majorName = majorName;
    }
}
