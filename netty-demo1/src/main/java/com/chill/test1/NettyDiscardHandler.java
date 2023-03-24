package com.chill.test1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.LoggerFactory;

public class NettyDiscardHandler extends ChannelInboundHandlerAdapter {
    private final static org.slf4j.Logger Logger = LoggerFactory.getLogger(NettyDiscardHandler.class);


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        try {
            Logger.info("收到消息,丢弃如下:");
            while (in.isReadable()) {
                System.out.print((char) in.readByte());
            }
            System.out.println();//换行
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

}
