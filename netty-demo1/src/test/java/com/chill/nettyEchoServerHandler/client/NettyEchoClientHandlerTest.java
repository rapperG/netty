package com.chill.nettyEchoServerHandler.client;

import org.junit.jupiter.api.BeforeEach;

class NettyEchoClientHandlerTest {

    private NettyEchoClientHandler nettyEchoClientHandlerUnderTest;

    @BeforeEach
    void setUp() {
        nettyEchoClientHandlerUnderTest = new NettyEchoClientHandler();
    }
}
