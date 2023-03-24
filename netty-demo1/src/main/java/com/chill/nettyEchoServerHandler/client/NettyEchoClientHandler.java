package com.chill.nettyEchoServerHandler.client;

import com.chill.nettyEchoServerHandler.server.NettyEchoServer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.LoggerFactory;

public class NettyEchoClientHandler extends ChannelInboundHandlerAdapter {
    private final static org.slf4j.Logger Logger = LoggerFactory.getLogger(NettyEchoClientHandler.class);

    public final static NettyEchoClientHandler INSTANCE = new NettyEchoClientHandler();


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf out = (ByteBuf) msg;
        Logger.info("msg type: " + (out.hasArray() ? "堆内存" : "直接内存"));
        int len = out.readableBytes();
        byte[] arr = new byte[len];
        out.getBytes(0, arr);
        Logger.info("client write: " + new String(arr, "UTF-8"));

        super.channelRead(ctx, msg);
    }
}
