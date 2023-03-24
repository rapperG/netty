package com.chill.testDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class StringIntegerHeaderDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext,
                          ByteBuf buf, List<Object> out) throws UnsupportedEncodingException {
        //可读字节小于 4，消息头还没读满，返回 
        if (buf.readableBytes() < 4) {
            return;
        }
        //消息头已经完整 
        //在真正开始从缓冲区读取数据之前，调用 markReaderIndex()设置 mark 标记 
        buf.markReaderIndex();
        int length = buf.readInt();
        //从缓冲区中读出消息头的大小，这会导致 readIndex 读指针变化 
        //如果剩余长度不够消息体，还需要 reset 读指针，下一次从相同的位置处理 
        if (buf.readableBytes() < length) {
            //读指针 reset 到消息头的 readIndex 位置处 
            buf.resetReaderIndex();
            return;
        }
        // 读取数据，编码成字符串 
        byte[] inBytes = new byte[length];
        buf.readBytes(inBytes, 0, length);
        out.add(new String(inBytes, "UTF-8"));
    }
}