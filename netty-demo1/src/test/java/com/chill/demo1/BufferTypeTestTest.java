package com.chill.demo1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BufferTypeTestTest {

    private BufferTypeTest bufferTypeTestUnderTest;

    @BeforeEach
    void setUp() {
        bufferTypeTestUnderTest = new BufferTypeTest();
    }

    @Test
    void testTestHeapBuffer() {
        // Setup
        // Run the test
        bufferTypeTestUnderTest.testHeapBuffer();

        // Verify the results
    }

    @Test
    void testTestDirectBuffer() {
        // Setup
        // Run the test
        bufferTypeTestUnderTest.testDirectBuffer();

        // Verify the results
    }
}
