package com.alexkorovyansky.mblock.utils;

/**
 * ThreadUtils
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class ThreadUtils {
    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {

        }
    }
}
