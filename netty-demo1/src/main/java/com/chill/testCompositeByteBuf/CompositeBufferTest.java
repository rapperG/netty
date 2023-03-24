package com.chill.testCompositeByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class CompositeBufferTest {
    static Charset utf8 = Charset.forName("UTF-8");

    public void byteBufComposite() {
        CompositeByteBuf cbuf =
                ByteBufAllocator.DEFAULT.compositeBuffer();
        //消息头 
        ByteBuf headerBuf = Unpooled.copiedBuffer("cggg:", utf8);         //消息体 1
        ByteBuf bodyBuf = Unpooled.copiedBuffer(" Netty", utf8);
        cbuf.addComponents(headerBuf, bodyBuf);
        sendMsg(cbuf);
        //在 refCnt 为 0 前, retain
        headerBuf.retain();
        cbuf.release();

        cbuf = ByteBufAllocator.DEFAULT.compositeBuffer();
        //消息体 2
        bodyBuf = Unpooled.copiedBuffer("111chill", utf8);
        cbuf.addComponents(headerBuf, bodyBuf);
        sendMsg(cbuf);
        cbuf.release();
    }

    private void sendMsg(CompositeByteBuf cbuf) {
        //处理整个消息
        for (ByteBuf b : cbuf) {
            int length = b.readableBytes();
            byte[] array = new byte[length];
            //将 CompositeByteBuf 中的数据，统一复制到数组中
            b.getBytes(b.readerIndex(), array);
            //处理一下数组中的数据
            System.out.print(new String(array, utf8));
        }
        System.out.println();
    }
}