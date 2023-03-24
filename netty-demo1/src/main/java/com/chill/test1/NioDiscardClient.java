package com.chill.test1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioDiscardClient {
    private static final Logger Logger = LoggerFactory.getLogger(NioDiscardClient.class);


    public static void startClient() throws IOException {
        InetSocketAddress address =
                new InetSocketAddress("127.0.0.1", 10003);
        // 1.获取通道 
        SocketChannel socketChannel = SocketChannel.open(address);
        // 2.切换成非阻塞模式 
        socketChannel.configureBlocking(false);
        //不断地自旋、等待连接完成，或者做一些其他的事情
        while (!socketChannel.finishConnect()) {
        }
        Logger.info("客户端连接成功");
        // 3.分配指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("hello world".getBytes());
        byteBuffer.flip();
        //发送到服务器
        socketChannel.write(byteBuffer);
        socketChannel.shutdownOutput();
        socketChannel.close();
    }

    public static void main(String[] args) throws IOException {
        startClient();
    }
}