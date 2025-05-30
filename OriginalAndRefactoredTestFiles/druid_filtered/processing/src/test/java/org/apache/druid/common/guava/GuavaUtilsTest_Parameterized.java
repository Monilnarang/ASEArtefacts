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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class GuavaUtilsTest_Parameterized {

    enum MyEnum {

        ONE, TWO, BUCKLE_MY_SHOE
    }

    @Test
    public void testParseLong_1() {
        Assert.assertNull(Longs.tryParse("+100"));
    }

    @Test
    public void testParseLong_3() {
        Assert.assertNull(GuavaUtils.tryParseLong(null));
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

    @ParameterizedTest
    @MethodSource("Provider_testParseLong_2_4to5")
    public void testParseLong_2_4to5(String param1) {
        Assert.assertNull(GuavaUtils.tryParseLong(param1));
    }

    static public Stream<Arguments> Provider_testParseLong_2_4to5() {
        return Stream.of(arguments(""), arguments("+"), arguments("++100"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseLong_6to7")
    public void testParseLong_6to7(int param1, int param2) {
        Assert.assertEquals((Object) Long.parseLong(param2), GuavaUtils.tryParseLong(param1));
    }

    static public Stream<Arguments> Provider_testParseLong_6to7() {
        return Stream.of(arguments(+100, +100), arguments(-100, -100));
    }
}
