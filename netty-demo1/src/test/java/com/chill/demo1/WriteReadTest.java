package com.chill.demo1;

import com.chill.test1.NioDiscardClient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WriteReadTest {
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(WriteReadTest.class);


    @Test
    public void testWriteRead() { 
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("动作：分配 ByteBuf(9, 100)", buffer);
        buffer.writeBytes(new byte[]{1, 2, 3, 4}); 
        print("动作：写入 4 个字节 (1,2,3,4)", buffer); 
        Logger.info("start==========:get=========="); 
        getByteBuf(buffer); 
        print("动作：取数据 ByteBuf", buffer); 
        Logger.info("start==========:read=========="); 
        readByteBuf(buffer); 
        print("动作：读完 ByteBuf", buffer); 
    }

    private void print(String s, ByteBuf buffer) {
    }

    //取字节 
    private void readByteBuf(ByteBuf buffer) { 
        while (buffer.isReadable()) { 
            Logger.info("取一个字节:" + buffer.readByte()); 
        } 
    } 
    //读字节，不改变指针 
    private void getByteBuf(ByteBuf buffer) { 
        for (int i = 0; i<buffer.readableBytes(); i++) { 
            Logger.info("读一个字节:" + buffer.getByte(i)); 
        } 
    } 
} 