package com.alexkorovyansky.mblock.bluetooth.connection;

import java.nio.ByteBuffer;

/**
 * PacketReader
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public abstract class PacketReader {
    public abstract byte[] catchPacket(ByteBuffer buffer);
}
