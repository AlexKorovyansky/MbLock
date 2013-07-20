package com.alexkorovyansky.mblock.classes;

/**
 * Callback
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public interface Callback<T> {
    public void onResult(T t);
    public void onError(Exception e);
}
