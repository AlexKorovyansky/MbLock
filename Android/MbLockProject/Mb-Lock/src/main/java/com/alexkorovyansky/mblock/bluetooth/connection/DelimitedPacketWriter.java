package com.alexkorovyansky.mblock.bluetooth.connection;

import java.nio.ByteBuffer;

/**
 * DelimitedPacketWriter
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class DelimitedPacketWriter extends PacketWriter {

    private String mDelimiter;

    private DelimitedPacketWriter(String delimiter) {
        this.mDelimiter = delimiter;
    }

    @Override
    public byte[] makePacket(byte[] packet) {
        final ByteBuffer bb = ByteBuffer.allocate(packet.length + mDelimiter.length());
        bb.put(packet, 0, packet.length);
        bb.put(mDelimiter.getBytes(), 0, mDelimiter.getBytes().length);
        return bb.array();
    }
}
