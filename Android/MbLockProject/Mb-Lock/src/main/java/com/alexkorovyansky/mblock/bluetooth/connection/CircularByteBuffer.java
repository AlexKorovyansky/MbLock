package com.alexkorovyansky.mblock.bluetooth.connection;

import java.util.Arrays;

/**
 * Robust implementation of circular byte buffer
 *
 * Example:
 *
 * CircularByteBuffer cbb = new CircularByteBuffer(3);
 * cbb.add(1);
 * // state of buffer = [*1, ?, ?]
 * cbb.add(2);
 * // state of buffer = [1, *2, ?]
 * cbb.add(3);
 * // state of buffer = [1, 2, *3]
 * cbb.add(4);
 * // state of buffer = [*4, 2, 3]
 * cbb.add(5);
 * // state of buffer = [4, *5, 3]
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class CircularByteBuffer {
    private byte[] mBuffer;
    private int mNextAddPosition = 0;
    private int mAddCounter;

    /**
     * Constructor for CircularByteBuffer
     * @param size - size of the buffer
     */
    public CircularByteBuffer(int size) {
        mBuffer = new byte[size];
    }

    /**
     * Adds byte to buffer
     * @param b
     */
    public void add(byte b) {
        mBuffer[mNextAddPosition] = b;
        mNextAddPosition = (mNextAddPosition + 1) % mBuffer.length;
        ++mAddCounter;
    }

    /**
     * Compares current state of CircularByteBuffer with byte array.
     * It's only possible to compare CircularByteBuffer with byte arrays, that have length equals to buffer size.
     *
     * Example:
     *
     * CircularByteBuffer cbb = new CircularByteBuffer(3);
     * cbb.add(1);
     * // state of buffer = [*1, ?, ?], isEqual() will always return false
     * cbb.add(2);
     * // state of buffer = [1, *2, ?], isEqual() will always return false
     * cbb.add(3);
     * // state of buffer = [1, 2, *3], isEqual() for [1, 2, 3] will returns true, in other parameters false
     * cbb.add(4);
     * // state of buffer = [*4, 2, 3], isEqual() for [2, 3, 4] will returns true, in other parameters false
     * cbb.add(5);
     * // state of buffer = [4, *5, 3], isEqual() for [3, 4, 5] will returns true, in other parameters false
     *
     * @param other - bytes array to be compared
     */
    public boolean isEqual(byte[] other) {
        if (other.length != mBuffer.length) {
            throw new IllegalArgumentException("other should have length equals to buffer size, but other.length="
                    + other.length + ", buffer.size = " + mBuffer.length);
        }
        if (mAddCounter < mBuffer.length) {
            return false;
        }
        for (int i = 0; i < mBuffer.length; ++i) {
            int k = (mNextAddPosition + i) % mBuffer.length;
            if (other[i] != mBuffer[k]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "CircularByteBuffer{" +
                "size=" + mBuffer.length +
                ", addCounter=" + mAddCounter +
                ", currentState=" + Arrays.toString(currentStateAsStringArray()) +
                '}';
    }

    private String[] currentStateAsStringArray(){
        if (mAddCounter < mBuffer.length) {
            final String[] result = new String[mBuffer.length];
            for (int i = 0; i < mAddCounter; ++i) {
                result[i] = Byte.toString(mBuffer[i]);
            }
            for (int i = mAddCounter; i < mBuffer.length; ++i) {
                result[i] = "?";
            }
            return result;
        } else {
            final String[] result = new String[mBuffer.length];
            for (int i = 0; i < mBuffer.length; ++i) {
                int k = (mNextAddPosition + i) % mBuffer.length;
                result[i] = Byte.toString(mBuffer[k]);
            }
            return result;
        }
    }
}
