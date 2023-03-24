package com.chill.testDecoder;

import com.chill.testDecoder.domain.User;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

public class ByteToMessageCodecUserTest extends ByteToMessageCodec<User> {
    @Override
    protected void encode(ChannelHandlerContext ctx, User msg, ByteBuf out) throws Exception {

        ctx.writeAndFlush(msg);

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

    }
}
