package com.alexkorovyansky.mblock.bluetooth.connection;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class ByteLengthFirstPacketReader extends PacketReader {

    public ByteLengthFirstPacketReader(){
    }

    @Override
    public byte[] catchPacket(ByteBuffer byteBuffer) {
        byteBuffer.mark();
        byte length;
        try {
            length = byteBuffer.get();
        } catch (BufferUnderflowException e) {
            return null;
        }
        if(byteBuffer.remaining() >= length){
            final byte[] packet = new byte[length];
            byteBuffer.get(packet);
            return packet;
        }
        byteBuffer.reset();
        return null;
    }

}