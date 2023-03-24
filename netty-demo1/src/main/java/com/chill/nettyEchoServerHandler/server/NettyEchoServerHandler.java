package com.chill.nettyEchoServerHandler.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * NettyEchoServerHandler加了一个特殊的Netty注解：@ChannelHandler.Sharable。这个注解的作用是标注一个Handler实例可以被多个通道安全地共享。什么叫Sharable呢？就是多个通道的流水线可以加入同一个Handler业务处理器实例。而这种共享操作，Netty默认是不允许的。
 * 但是，很多应用场景需要Handler业务处理器实例能共享。例如，一个服务器处理十万以上的通道，如果一个通道都新建很多重复的Handler实例，就需要上十万以上重复的Handler 实例，这就会浪费很多宝贵的空间，降低了服务器的性能。所以，如果在Handler实例中，没有与特定通道强相关的数据或者状态，建议设计成共享的模式。
 * 反过来，如果没有加@ChannelHandler.Sharable注解，试图将同一个Handler实例添加到多个ChannelPipeline通道流水线时，Netty将会抛出异常。
 * 如何判断一个Handler是否为@Sharable共享呢？ChannelHandlerAdapter提供了实用方法——isSharable()。如果其对应的实现加上了@Sharable注解，那么这个方法将返回true，表示它可以被添加到多个ChannelPipeline通道流水线中。
 * NettyEchoServerHandler回显服务器处理器没有保存与任何通道连接相关的数据，也没有内部的其他数据需要保存。所以，该处理器不仅仅可以用来共享，而且不需要做任何的同步控制。在这里，为它加上了@Sharable注解表示可以共享，更进一步，这里还设计了一个通用的INSTANCE静态实例，所有的通道直接使用这个INSTANCE实例即可。
 */
@ChannelHandler.Sharable
public class NettyEchoServerHandler extends ChannelInboundHandlerAdapter {
    private final static org.slf4j.Logger Logger = LoggerFactory.getLogger(NettyEchoServerHandler.class);

    public static final NettyEchoServerHandler INSTANCE
            = new NettyEchoServerHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
        ByteBuf in = (ByteBuf) msg;
        Logger.info("msg type: " + (in.hasArray() ? "堆内存" : "直接内存"));
        int len = in.readableBytes();
        byte[] arr = new byte[len];
        in.getBytes(0, arr);
        Logger.info("server received: " + new String(arr, "UTF-8"));


        in.readBytes(arr);
        Logger.info("readIndex:{},writeIndex:{},capacity：{}", ((ByteBuf) msg).readerIndex(), ((ByteBuf) msg).writerIndex(), ((ByteBuf) msg).maxCapacity());

        Logger.info("写回前，msg.refCnt:" + ((ByteBuf) msg).refCnt());
        //写回数据，异步任务
        ChannelFuture f = ctx.writeAndFlush(msg);
        f.addListener((future) -> {
            Logger.info("写回后，msg.refCnt:" + ((ByteBuf) msg).refCnt());
        });
    }
}
