package com.chill.nettyEchoServerHandler.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NettyEchoClientTest {

    private NettyEchoClient nettyEchoClientUnderTest;

    @BeforeEach
    void setUp() {
        nettyEchoClientUnderTest = new NettyEchoClient("ip", 0);
    }

    @Test
    void testRunClient() {
        // Setup
        // Run the test
        nettyEchoClientUnderTest.runClient();

        // Verify the results
    }

    @Test
    void testMain() {
        // Setup
        // Run the test
        NettyEchoClient.main(new String[]{"args"});

        // Verify the results
    }
}
