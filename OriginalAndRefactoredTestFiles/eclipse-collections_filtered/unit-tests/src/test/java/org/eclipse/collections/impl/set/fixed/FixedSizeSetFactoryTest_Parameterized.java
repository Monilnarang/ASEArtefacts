package org.eclipse.collections.impl.set.fixed;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.factory.set.FixedSizeSetFactory;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.FixedSizeSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.block.factory.Procedures2;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.test.domain.Key;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class FixedSizeSetFactoryTest_Parameterized {

    private FixedSizeSetFactory setFactory;

    @BeforeEach
    public void setUp() {
        this.setFactory = FixedSizeSetFactoryImpl.INSTANCE;
    }

    private void assertCreateSet(FixedSizeSet<String> undertest, String... expected) {
        assertEquals(UnifiedSet.newSetWith(expected), undertest);
        Verify.assertInstanceOf(FixedSizeSet.class, undertest);
    }

    @Test
    public void testCreateWith3Args_1() {
        this.assertCreateSet(this.setFactory.of("a", "a"), "a");
    }

    @ParameterizedTest
    @MethodSource("Provider_testCreateWith3Args_2to4")
    public void testCreateWith3Args_2to4(String param1, String param2, String param3, String param4, String param5) {
        this.assertCreateSet(this.setFactory.of(param3, param4, param5), param1, param2);
    }

    static public Stream<Arguments> Provider_testCreateWith3Args_2to4() {
        return Stream.of(arguments("a", "c", "a", "a", "c"), arguments("a", "b", "a", "b", "a"), arguments("a", "b", "a", "b", "b"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCreateWith4Args_1to6")
    public void testCreateWith4Args_1to6(String param1, String param2, String param3, String param4, String param5, String param6, String param7) {
        this.assertCreateSet(this.setFactory.of(param4, param5, param6, param7), param1, param2, param3);
    }

    static public Stream<Arguments> Provider_testCreateWith4Args_1to6() {
        return Stream.of(arguments("a", "c", "d", "a", "a", "c", "d"), arguments("a", "b", "d", "a", "b", "a", "d"), arguments("a", "b", "c", "a", "b", "c", "a"), arguments("a", "b", "d", "a", "b", "b", "d"), arguments("a", "b", "c", "a", "b", "c", "b"), arguments("a", "b", "c", "a", "b", "c", "c"));
    }
}
