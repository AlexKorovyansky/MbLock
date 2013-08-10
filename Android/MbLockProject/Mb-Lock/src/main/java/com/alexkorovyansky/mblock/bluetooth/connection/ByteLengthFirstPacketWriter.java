package com.alexkorovyansky.mblock.bluetooth.connection;

import java.nio.ByteBuffer;

/**
 * ByteLengthFirstPacketWriter
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class ByteLengthFirstPacketWriter extends PacketWriter {

    @Override
    public byte[] makePacket(byte[] packet) {
        if (packet.length > 255) {
            throw new IllegalArgumentException("packet length cannot be > 255 (max byte size)");
        } else {
            final ByteBuffer bb = ByteBuffer.allocate(packet.length + 1);
            bb.put((byte)packet.length);
            bb.put(packet, 0, packet.length);
            return bb.array();
        }
    }
}
