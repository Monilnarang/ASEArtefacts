package org.apache.flink.api.java.typeutils;

import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CompositeTypeTest_Parameterized {

    private final TupleTypeInfo<?> tupleTypeInfo = new TupleTypeInfo<Tuple4<Integer, Integer, Integer, Integer>>(BasicTypeInfo.INT_TYPE_INFO, BasicTypeInfo.INT_TYPE_INFO, BasicTypeInfo.INT_TYPE_INFO, BasicTypeInfo.INT_TYPE_INFO);

    private final TupleTypeInfo<Tuple3<Integer, String, Long>> inNestedTuple1 = new TupleTypeInfo<Tuple3<Integer, String, Long>>(BasicTypeInfo.INT_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.LONG_TYPE_INFO);

    private final TupleTypeInfo<Tuple2<Double, Double>> inNestedTuple2 = new TupleTypeInfo<Tuple2<Double, Double>>(BasicTypeInfo.DOUBLE_TYPE_INFO, BasicTypeInfo.DOUBLE_TYPE_INFO);

    private final TupleTypeInfo<?> nestedTypeInfo = new TupleTypeInfo<Tuple4<Integer, Tuple3<Integer, String, Long>, Integer, Tuple2<Double, Double>>>(BasicTypeInfo.INT_TYPE_INFO, inNestedTuple1, BasicTypeInfo.INT_TYPE_INFO, inNestedTuple2);

    private final TupleTypeInfo<Tuple2<Integer, Tuple2<Integer, Integer>>> inNestedTuple3 = new TupleTypeInfo<Tuple2<Integer, Tuple2<Integer, Integer>>>(BasicTypeInfo.INT_TYPE_INFO, new TupleTypeInfo<Tuple2<Integer, Integer>>(BasicTypeInfo.INT_TYPE_INFO, BasicTypeInfo.INT_TYPE_INFO));

    private final TupleTypeInfo<?> deepNestedTupleTypeInfo = new TupleTypeInfo<Tuple3<Integer, Tuple2<Integer, Tuple2<Integer, Integer>>, Integer>>(BasicTypeInfo.INT_TYPE_INFO, inNestedTuple3, BasicTypeInfo.INT_TYPE_INFO);

    private final PojoTypeInfo<?> pojoTypeInfo = ((PojoTypeInfo<?>) TypeExtractor.getForClass(MyPojo.class));

    private final TupleTypeInfo<?> pojoInTupleTypeInfo = new TupleTypeInfo<Tuple2<Integer, MyPojo>>(BasicTypeInfo.INT_TYPE_INFO, pojoTypeInfo);

    public static class MyPojo {

        public String a;

        public int b;
    }

    @Test
    void testGetFlatFields_9() {
        assertThat(nestedTypeInfo.getFlatFields("0").get(0).getPosition()).isZero();
    }

    @Test
    void testGetFlatFields_36() {
        assertThat(nestedTypeInfo.getFlatFields("0").get(0).getType()).isEqualTo(BasicTypeInfo.INT_TYPE_INFO);
    }

    @Test
    void testGetFlatFields_37() {
        assertThat(nestedTypeInfo.getFlatFields("1.1").get(0).getType()).isEqualTo(BasicTypeInfo.STRING_TYPE_INFO);
    }

    @Test
    void testGetFlatFields_38() {
        assertThat(nestedTypeInfo.getFlatFields("1").get(2).getType()).isEqualTo(BasicTypeInfo.LONG_TYPE_INFO);
    }

    @Test
    void testGetFlatFields_39() {
        assertThat(nestedTypeInfo.getFlatFields("3").get(1).getType()).isEqualTo(BasicTypeInfo.DOUBLE_TYPE_INFO);
    }

    @Test
    void testGetFlatFields_45() {
        assertThat(deepNestedTupleTypeInfo.getFlatFields("*").get(0).getPosition()).isZero();
    }

    @Test
    void testGetFlatFields_52() {
        assertThat(pojoTypeInfo.getFlatFields("*")).hasSize(2);
    }

    @Test
    void testGetFlatFields_64() {
        assertThat(pojoInTupleTypeInfo.getFlatFields("*").get(0).getPosition()).isZero();
    }

    @Test
    void testFieldAtStringRef_7() {
        assertThat(nestedTypeInfo.getTypeAt("1.1")).isEqualTo(BasicTypeInfo.STRING_TYPE_INFO);
    }

    @Test
    void testFieldAtStringRef_8() {
        assertThat(nestedTypeInfo.getTypeAt("1.2")).isEqualTo(BasicTypeInfo.LONG_TYPE_INFO);
    }

    @Test
    void testFieldAtStringRef_18() {
        assertThat(deepNestedTupleTypeInfo.getTypeAt("1")).isEqualTo(inNestedTuple3);
    }

    @Test
    void testFieldAtStringRef_19() {
        assertThat(pojoTypeInfo.getTypeAt("a")).isEqualTo(BasicTypeInfo.STRING_TYPE_INFO);
    }

    @Test
    void testFieldAtStringRef_20() {
        assertThat(pojoTypeInfo.getTypeAt("b")).isEqualTo(BasicTypeInfo.INT_TYPE_INFO);
    }

    @Test
    void testFieldAtStringRef_21() {
        assertThat(pojoInTupleTypeInfo.getTypeAt("f1.a")).isEqualTo(BasicTypeInfo.STRING_TYPE_INFO);
    }

    @Test
    void testFieldAtStringRef_22() {
        assertThat(pojoInTupleTypeInfo.getTypeAt("1.b")).isEqualTo(BasicTypeInfo.INT_TYPE_INFO);
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFlatFields_1_5")
    void testGetFlatFields_1_5(int param1, int param2) {
        assertThat(tupleTypeInfo.getFlatFields(param2).get(param1).getPosition()).isZero();
    }

    static public Stream<Arguments> Provider_testGetFlatFields_1_5() {
        return Stream.of(arguments(0, 0), arguments(0, "f0"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFlatFields_2_6")
    void testGetFlatFields_2_6(int param1, int param2) {
        assertThat(tupleTypeInfo.getFlatFields(param2).get(param1).getPosition()).isOne();
    }

    static public Stream<Arguments> Provider_testGetFlatFields_2_6() {
        return Stream.of(arguments(0, 1), arguments(0, "f1"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFlatFields_3to4_7to8")
    void testGetFlatFields_3to4_7to8(int param1, int param2, int param3) {
        assertThat(tupleTypeInfo.getFlatFields(param3).get(param2).getPosition()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testGetFlatFields_3to4_7to8() {
        return Stream.of(arguments(2, 0, 2), arguments(3, 0, 3), arguments(2, 0, "f2"), arguments(3, 0, "f3"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFlatFields_10_19_23_30")
    void testGetFlatFields_10_19_23_30(int param1, double param2) {
        assertThat(nestedTypeInfo.getFlatFields(param2).get(param1).getPosition()).isOne();
    }

    static public Stream<Arguments> Provider_testGetFlatFields_10_19_23_30() {
        return Stream.of(arguments(0, 1.0), arguments(0, 1), arguments(0, "1.*"), arguments(0, "f1"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFlatFields_11to17_20to21_24to25_27to28_31to32_34to35")
    void testGetFlatFields_11to17_20to21_24to25_27to28_31to32_34to35(int param1, int param2, double param3) {
        assertThat(nestedTypeInfo.getFlatFields(param3).get(param2).getPosition()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testGetFlatFields_11to17_20to21_24to25_27to28_31to32_34to35() {
        return Stream.of(arguments(2, 0, 1.1), arguments(3, 0, 1.2), arguments(4, 0, 2), arguments(5, 0, 3.0), arguments(6, 0, 3.1), arguments(4, 0, "f2"), arguments(5, 0, "f3.f0"), arguments(2, 1, 1), arguments(3, 2, 1), arguments(2, 1, "1.*"), arguments(3, 2, "1.*"), arguments(5, 0, 3), arguments(6, 1, 3), arguments(2, 1, "f1"), arguments(3, 2, "f1"), arguments(5, 0, "f3"), arguments(6, 1, "f3"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFlatFields_18_22_26_29_33")
    void testGetFlatFields_18_22_26_29_33(int param1, int param2) {
        assertThat(nestedTypeInfo.getFlatFields(param2)).hasSize(param1);
    }

    static public Stream<Arguments> Provider_testGetFlatFields_18_22_26_29_33() {
        return Stream.of(arguments(3, 1), arguments(3, "1.*"), arguments(2, 3), arguments(3, "f1"), arguments(2, "f3"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFlatFields_40_44")
    void testGetFlatFields_40_44(int param1, int param2) {
        assertThat(deepNestedTupleTypeInfo.getFlatFields(param2)).hasSize(param1);
    }

    static public Stream<Arguments> Provider_testGetFlatFields_40_44() {
        return Stream.of(arguments(3, 1), arguments(5, "*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFlatFields_41_46")
    void testGetFlatFields_41_46(int param1, int param2) {
        assertThat(deepNestedTupleTypeInfo.getFlatFields(param2).get(param1).getPosition()).isOne();
    }

    static public Stream<Arguments> Provider_testGetFlatFields_41_46() {
        return Stream.of(arguments(0, 1), arguments(1, "*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFlatFields_42to43_47to49")
    void testGetFlatFields_42to43_47to49(int param1, int param2, int param3) {
        assertThat(deepNestedTupleTypeInfo.getFlatFields(param3).get(param2).getPosition()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testGetFlatFields_42to43_47to49() {
        return Stream.of(arguments(2, 1, 1), arguments(3, 2, 1), arguments(2, 2, "*"), arguments(3, 3, "*"), arguments(4, 4, "*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFlatFields_50_53")
    void testGetFlatFields_50_53(int param1, String param2) {
        assertThat(pojoTypeInfo.getFlatFields(param2).get(param1).getPosition()).isZero();
    }

    static public Stream<Arguments> Provider_testGetFlatFields_50_53() {
        return Stream.of(arguments(0, "a"), arguments(0, "*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFlatFields_51_54")
    void testGetFlatFields_51_54(int param1, String param2) {
        assertThat(pojoTypeInfo.getFlatFields(param2).get(param1).getPosition()).isOne();
    }

    static public Stream<Arguments> Provider_testGetFlatFields_51_54() {
        return Stream.of(arguments(0, "b"), arguments(1, "*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFlatFields_55_58_61_65")
    void testGetFlatFields_55_58_61_65(int param1, String param2) {
        assertThat(pojoInTupleTypeInfo.getFlatFields(param2).get(param1).getPosition()).isOne();
    }

    static public Stream<Arguments> Provider_testGetFlatFields_55_58_61_65() {
        return Stream.of(arguments(0, "f1.a"), arguments(0, "1.*"), arguments(0, "f1.*"), arguments(1, "*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFlatFields_56_59_62_66")
    void testGetFlatFields_56_59_62_66(int param1, int param2, String param3) {
        assertThat(pojoInTupleTypeInfo.getFlatFields(param3).get(param2).getPosition()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testGetFlatFields_56_59_62_66() {
        return Stream.of(arguments(2, 0, "1.b"), arguments(2, 1, 1), arguments(2, 1, "f1"), arguments(2, 2, "*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFlatFields_57_60_63")
    void testGetFlatFields_57_60_63(int param1, int param2) {
        assertThat(pojoInTupleTypeInfo.getFlatFields(param2)).hasSize(param1);
    }

    static public Stream<Arguments> Provider_testGetFlatFields_57_60_63() {
        return Stream.of(arguments(2, 1), arguments(2, "f1.*"), arguments(3, "*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFieldAtStringRef_1to4")
    void testFieldAtStringRef_1to4(int param1) {
        assertThat(tupleTypeInfo.getTypeAt(param1)).isEqualTo(BasicTypeInfo.INT_TYPE_INFO);
    }

    static public Stream<Arguments> Provider_testFieldAtStringRef_1to4() {
        return Stream.of(arguments(0), arguments(2), arguments("f1"), arguments("f3"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFieldAtStringRef_5to6_9_12")
    void testFieldAtStringRef_5to6_9_12(int param1) {
        assertThat(nestedTypeInfo.getTypeAt(param1)).isEqualTo(BasicTypeInfo.INT_TYPE_INFO);
    }

    static public Stream<Arguments> Provider_testFieldAtStringRef_5to6_9_12() {
        return Stream.of(arguments(0), arguments(1.0), arguments(2), arguments("f2"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFieldAtStringRef_10to11_13")
    void testFieldAtStringRef_10to11_13(double param1) {
        assertThat(nestedTypeInfo.getTypeAt(param1)).isEqualTo(BasicTypeInfo.DOUBLE_TYPE_INFO);
    }

    static public Stream<Arguments> Provider_testFieldAtStringRef_10to11_13() {
        return Stream.of(arguments(3.0), arguments(3.1), arguments("f3.f0"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFieldAtStringRef_14_16")
    void testFieldAtStringRef_14_16(int param1) {
        assertThat(nestedTypeInfo.getTypeAt(param1)).isEqualTo(inNestedTuple1);
    }

    static public Stream<Arguments> Provider_testFieldAtStringRef_14_16() {
        return Stream.of(arguments(1), arguments("f1"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFieldAtStringRef_15_17")
    void testFieldAtStringRef_15_17(int param1) {
        assertThat(nestedTypeInfo.getTypeAt(param1)).isEqualTo(inNestedTuple2);
    }

    static public Stream<Arguments> Provider_testFieldAtStringRef_15_17() {
        return Stream.of(arguments(3), arguments("f3"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFieldAtStringRef_23to24")
    void testFieldAtStringRef_23to24(int param1) {
        assertThat(pojoInTupleTypeInfo.getTypeAt(param1)).isEqualTo(pojoTypeInfo);
    }

    static public Stream<Arguments> Provider_testFieldAtStringRef_23to24() {
        return Stream.of(arguments(1), arguments("f1"));
    }
}
