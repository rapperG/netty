package com.chill.testDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

public class ByteToMessageCodecIntegerTest extends ByteToMessageCodec<Integer> {
    @Override
    public void encode(ChannelHandlerContext ctx,
                       Integer msg, ByteBuf out) {
        out.writeInt(msg);
        System.out.println("write Integer = " + msg);
    }

    @Override
    public void decode(ChannelHandlerContext ctx,
                       ByteBuf in, List<Object> out) {
        if (in.readableBytes() >= 4) {
            int i = in.readInt();
            System.out.println("Decoder i= " + i);
            out.add(i);
        }
    }
}
