package com.alexkorovyansky.mblock.bluetooth.connection;

import java.nio.ByteBuffer;

/**
 * Delimited-based implementation of BinaryPacketReader
 *
 * Splits packets by specified delimiter
 *
 * Example:
 * delimeter = '\r\n'
 *
 * Input "Foo\r\nBar\r\n" will be split to 2 packets "Foo" and "Bar"
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class DelimitedPacketReader extends PacketReader {

    private CircularByteBuffer mCircularByteBuffer;
    private byte[] mDelimiterBytes;

    /**
     * Constructor for DelimitedPacketReader
     * @param delimiter - delimiter, that will be use for splitting packets
     */
    public DelimitedPacketReader(String delimiter){
        mCircularByteBuffer = new CircularByteBuffer(delimiter.length());
        mDelimiterBytes = delimiter.getBytes();
    }

    @Override
    public byte[] catchPacket(ByteBuffer byteBuffer) {
        byteBuffer.mark();
        int bytesRead = 0;
        while (byteBuffer.remaining() > 0) {
            bytesRead++;
            byte current = byteBuffer.get();
            mCircularByteBuffer.add(current);
            if (mCircularByteBuffer.isEqual(mDelimiterBytes)) {
                byte[] packet = new byte[bytesRead - mDelimiterBytes.length];
                byteBuffer.reset();
                byteBuffer.get(packet);
                for (int i = 0; i < mDelimiterBytes.length; ++i) {
                    byteBuffer.get();
                }
                return packet;
            }
        }
        byteBuffer.reset();
        return null;
    }

}
