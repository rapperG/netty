package com.chill.testPipeline.out;

import com.chill.testPipeline.in.InPipeline;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;
import org.slf4j.LoggerFactory;

public class OutPipeline {
    private final static org.slf4j.Logger Logger = LoggerFactory.getLogger(OutPipeline.class);


    //内部类：第一个出站处理器 
    public class SimpleOutHandlerA extends
            ChannelOutboundHandlerAdapter {
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            Logger.info("出站处理器 A: 被回调");
            super.write(ctx, msg, promise);
        }
    }

    //内部类：第二个出站处理器 
    public class SimpleOutHandlerB extends
            ChannelOutboundHandlerAdapter {
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            Logger.info("出站处理器 B: 被回调");
            super.write(ctx, msg, promise);
        }
    }

    //内部类：第三个出站处理器 
    public class SimpleOutHandlerC extends
            ChannelOutboundHandlerAdapter {
        @Override
        public void write(ChannelHandlerContext ctx, Object msg,
                          ChannelPromise promise) throws Exception {
            Logger.info("出站处理器 C: 被回调");
            super.write(ctx, msg, promise);
        }
    }

    public void testPipelineOutBound() {
        ChannelInitializer i =
                new ChannelInitializer<EmbeddedChannel>() {
                    protected void initChannel(EmbeddedChannel ch) {
                        ch.pipeline().addLast(new SimpleOutHandlerA());
                        ch.pipeline().addLast(new SimpleOutHandlerB());
                        ch.pipeline().addLast(new SimpleOutHandlerC());
                    }
                };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(1);
        //向通道写一个出站报文(或数据包) 
        channel.writeOutbound(buf);
        channel.flush();
        //通道关闭
        channel.close();
    }
} 