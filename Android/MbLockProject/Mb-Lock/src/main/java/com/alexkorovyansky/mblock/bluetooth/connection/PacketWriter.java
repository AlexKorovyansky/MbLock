package com.alexkorovyansky.mblock.bluetooth.connection;

/**
 * PacketWriter
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public abstract class PacketWriter {
    public abstract byte[] makePacket(byte[] packet);
}
