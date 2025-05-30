package org.apache.druid.common.guava;

import com.google.common.primitives.Longs;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.apache.druid.java.util.common.concurrent.Execs;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class GuavaUtilsTest_Purified {

    enum MyEnum {

        ONE, TWO, BUCKLE_MY_SHOE
    }

    @Test
    public void testParseLong_1() {
        Assert.assertNull(Longs.tryParse("+100"));
    }

    @Test
    public void testParseLong_2() {
        Assert.assertNull(GuavaUtils.tryParseLong(""));
    }

    @Test
    public void testParseLong_3() {
        Assert.assertNull(GuavaUtils.tryParseLong(null));
    }

    @Test
    public void testParseLong_4() {
        Assert.assertNull(GuavaUtils.tryParseLong("+"));
    }

    @Test
    public void testParseLong_5() {
        Assert.assertNull(GuavaUtils.tryParseLong("++100"));
    }

    @Test
    public void testParseLong_6() {
        Assert.assertEquals((Object) Long.parseLong("+100"), GuavaUtils.tryParseLong("+100"));
    }

    @Test
    public void testParseLong_7() {
        Assert.assertEquals((Object) Long.parseLong("-100"), GuavaUtils.tryParseLong("-100"));
    }

    @Test
    public void testParseLong_8() {
        Assert.assertNotEquals(new Long(100), GuavaUtils.tryParseLong("+101"));
    }

    @Test
    public void testGetEnumIfPresent_1() {
        Assert.assertEquals(MyEnum.ONE, GuavaUtils.getEnumIfPresent(MyEnum.class, "ONE"));
    }

    @Test
    public void testGetEnumIfPresent_2() {
        Assert.assertEquals(MyEnum.TWO, GuavaUtils.getEnumIfPresent(MyEnum.class, "TWO"));
    }

    @Test
    public void testGetEnumIfPresent_3() {
        Assert.assertEquals(MyEnum.BUCKLE_MY_SHOE, GuavaUtils.getEnumIfPresent(MyEnum.class, "BUCKLE_MY_SHOE"));
    }

    @Test
    public void testGetEnumIfPresent_4() {
        Assert.assertEquals(null, GuavaUtils.getEnumIfPresent(MyEnum.class, "buckle_my_shoe"));
    }
}
