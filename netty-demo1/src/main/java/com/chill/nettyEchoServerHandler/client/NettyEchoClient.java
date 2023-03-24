package com.chill.nettyEchoServerHandler.client;

import cn.hutool.core.date.DateUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class NettyEchoClient {

    private int serverPort;
    private String serverIp;
    Bootstrap b = new Bootstrap();

    public NettyEchoClient(String ip, int port) {
        this.serverPort = port;
        this.serverIp = ip;
    }

    public void runClient() {
        //创建反应器轮询组
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();

        try {
            //1 设置反应器 轮询组
            b.group(workerLoopGroup);
            //2 设置 nio 类型的通道
            b.channel(NioSocketChannel.class);
            //3 设置监听端口
            b.remoteAddress(serverIp, serverPort);
            //4 设置通道的参数
            b.option(ChannelOption.ALLOCATOR,
                    PooledByteBufAllocator.DEFAULT);

            //5 装配子通道流水线
            b.handler(new ChannelInitializer<SocketChannel>() {
                //有连接到达时会创建一个通道
                protected void initChannel(SocketChannel ch) {
                    // 管理子通道中的 Handler 业务处理器
                    // 向子通道流水线添加一个 Handler 业务处理器
                    ch.pipeline().addLast(NettyEchoClientHandler.INSTANCE);
                }
            });
            ChannelFuture f = b.connect();
            f.addListener((future) ->
            {
                if (future.isSuccess()) {
                    log.info("EchoClient 客户端连接成功!");
                } else {
                    log.info("EchoClient 客户端连接失败!");
                }
            });

            // 阻塞,直到连接成功
            f.sync();
            Channel channel = f.channel();
            Scanner scanner = new Scanner(System.in);
            log.info("请输入发送内容:");
            while (scanner.hasNext()) {
                //获取输入的内容
                String next = scanner.next();
                byte[] bytes = (DateUtil.now() + " >>"
                        + next).getBytes("UTF-8");
                //发送 ByteBuf
                ByteBuf buffer = channel.alloc().buffer();
                buffer.writeBytes(bytes);
                channel.writeAndFlush(buffer);
                log.info("请输入发送内容:");

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 优雅关闭 EventLoopGroup，
            // 释放掉所有资源，包括创建的线程
            workerLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyEchoClient("127.0.0.1",10001).runClient();
    }
}