package org.apache.dubbo.remoting.buffer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.remoting.buffer.ChannelBuffers.directBuffer;
import static org.apache.dubbo.remoting.buffer.ChannelBuffers.wrappedBuffer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class AbstractChannelBufferTest_Purified {

    private static final int CAPACITY = 4096;

    private static final int BLOCK_SIZE = 128;

    private long seed;

    private Random random;

    private ChannelBuffer buffer;

    protected abstract ChannelBuffer newBuffer(int capacity);

    protected abstract ChannelBuffer[] components();

    protected boolean discardReadBytesDoesNotMoveWritableBytes() {
        return true;
    }

    @BeforeEach
    public void init() {
        buffer = newBuffer(CAPACITY);
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    @AfterEach
    public void dispose() {
        buffer = null;
    }

    @Test
    void initialState_1() {
        assertEquals(CAPACITY, buffer.capacity());
    }

    @Test
    void initialState_2() {
        assertEquals(0, buffer.readerIndex());
    }
}
