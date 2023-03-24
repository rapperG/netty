package com.chill.testPipeline.in;

import com.chill.test2.InHandlerDemo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.slf4j.LoggerFactory;

public class InPipeline {
  private final static org.slf4j.Logger Logger = LoggerFactory.getLogger(InPipeline.class);


  //内部类：第一个入站处理器
    static class SimpleInHandlerA extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            Logger.info("入站处理器 A: 被回调 ");
            super.channelRead(ctx, msg);
        }
    }

    //内部类：第二个入站处理器
    static class SimpleInHandlerB extends
            ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            Logger.info("入站处理器 B: 被回调 ");
            super.channelRead(ctx, msg);
        }
    }

    //内部类：第三个入站处理器
    static class SimpleInHandlerC extends
            ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            Logger.info("入站处理器 C: 被回调 ");
            super.channelRead(ctx, msg);
        }
    }

  public static void main(String[] args) {
    final ChannelInitializer i =
            new ChannelInitializer<EmbeddedChannel>() {
              protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(new SimpleInHandlerA());
                ch.pipeline().addLast(new SimpleInHandlerB());
                ch.pipeline().addLast(new SimpleInHandlerC());
              }
            };
    EmbeddedChannel channel = new EmbeddedChannel(i);
    ByteBuf buf = Unpooled.buffer();
    buf.writeInt(1);
    //向通道写一个入站报文（数据包）
    channel.writeInbound(buf);
    channel.flush();
    //通道关闭
    channel.close();
  }
    public void testPipelineInBound() {
        ChannelInitializer i =
                new ChannelInitializer<EmbeddedChannel>() {
                    protected void initChannel(EmbeddedChannel ch) {
                        ch.pipeline().addLast(new SimpleInHandlerA());
                        ch.pipeline().addLast(new SimpleInHandlerB());
                        ch.pipeline().addLast(new SimpleInHandlerC());
                    }
                };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(1);
        //向通道写一个入站报文（数据包）
        channel.writeInbound(buf);
        channel.flush();
        //通道关闭
        channel.close();
    }
}