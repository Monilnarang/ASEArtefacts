package org.apache.flink.api.java.typeutils;

import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CompositeTypeTest_Purified {

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
    void testGetFlatFields_1() {
        assertThat(tupleTypeInfo.getFlatFields("0").get(0).getPosition()).isZero();
    }

    @Test
    void testGetFlatFields_2() {
        assertThat(tupleTypeInfo.getFlatFields("1").get(0).getPosition()).isOne();
    }

    @Test
    void testGetFlatFields_3() {
        assertThat(tupleTypeInfo.getFlatFields("2").get(0).getPosition()).isEqualTo(2);
    }

    @Test
    void testGetFlatFields_4() {
        assertThat(tupleTypeInfo.getFlatFields("3").get(0).getPosition()).isEqualTo(3);
    }

    @Test
    void testGetFlatFields_5() {
        assertThat(tupleTypeInfo.getFlatFields("f0").get(0).getPosition()).isZero();
    }

    @Test
    void testGetFlatFields_6() {
        assertThat(tupleTypeInfo.getFlatFields("f1").get(0).getPosition()).isOne();
    }

    @Test
    void testGetFlatFields_7() {
        assertThat(tupleTypeInfo.getFlatFields("f2").get(0).getPosition()).isEqualTo(2);
    }

    @Test
    void testGetFlatFields_8() {
        assertThat(tupleTypeInfo.getFlatFields("f3").get(0).getPosition()).isEqualTo(3);
    }

    @Test
    void testGetFlatFields_9() {
        assertThat(nestedTypeInfo.getFlatFields("0").get(0).getPosition()).isZero();
    }

    @Test
    void testGetFlatFields_10() {
        assertThat(nestedTypeInfo.getFlatFields("1.0").get(0).getPosition()).isOne();
    }

    @Test
    void testGetFlatFields_11() {
        assertThat(nestedTypeInfo.getFlatFields("1.1").get(0).getPosition()).isEqualTo(2);
    }

    @Test
    void testGetFlatFields_12() {
        assertThat(nestedTypeInfo.getFlatFields("1.2").get(0).getPosition()).isEqualTo(3);
    }

    @Test
    void testGetFlatFields_13() {
        assertThat(nestedTypeInfo.getFlatFields("2").get(0).getPosition()).isEqualTo(4);
    }

    @Test
    void testGetFlatFields_14() {
        assertThat(nestedTypeInfo.getFlatFields("3.0").get(0).getPosition()).isEqualTo(5);
    }

    @Test
    void testGetFlatFields_15() {
        assertThat(nestedTypeInfo.getFlatFields("3.1").get(0).getPosition()).isEqualTo(6);
    }

    @Test
    void testGetFlatFields_16() {
        assertThat(nestedTypeInfo.getFlatFields("f2").get(0).getPosition()).isEqualTo(4);
    }

    @Test
    void testGetFlatFields_17() {
        assertThat(nestedTypeInfo.getFlatFields("f3.f0").get(0).getPosition()).isEqualTo(5);
    }

    @Test
    void testGetFlatFields_18() {
        assertThat(nestedTypeInfo.getFlatFields("1")).hasSize(3);
    }

    @Test
    void testGetFlatFields_19() {
        assertThat(nestedTypeInfo.getFlatFields("1").get(0).getPosition()).isOne();
    }

    @Test
    void testGetFlatFields_20() {
        assertThat(nestedTypeInfo.getFlatFields("1").get(1).getPosition()).isEqualTo(2);
    }

    @Test
    void testGetFlatFields_21() {
        assertThat(nestedTypeInfo.getFlatFields("1").get(2).getPosition()).isEqualTo(3);
    }

    @Test
    void testGetFlatFields_22() {
        assertThat(nestedTypeInfo.getFlatFields("1.*")).hasSize(3);
    }

    @Test
    void testGetFlatFields_23() {
        assertThat(nestedTypeInfo.getFlatFields("1.*").get(0).getPosition()).isOne();
    }

    @Test
    void testGetFlatFields_24() {
        assertThat(nestedTypeInfo.getFlatFields("1.*").get(1).getPosition()).isEqualTo(2);
    }

    @Test
    void testGetFlatFields_25() {
        assertThat(nestedTypeInfo.getFlatFields("1.*").get(2).getPosition()).isEqualTo(3);
    }

    @Test
    void testGetFlatFields_26() {
        assertThat(nestedTypeInfo.getFlatFields("3")).hasSize(2);
    }

    @Test
    void testGetFlatFields_27() {
        assertThat(nestedTypeInfo.getFlatFields("3").get(0).getPosition()).isEqualTo(5);
    }

    @Test
    void testGetFlatFields_28() {
        assertThat(nestedTypeInfo.getFlatFields("3").get(1).getPosition()).isEqualTo(6);
    }

    @Test
    void testGetFlatFields_29() {
        assertThat(nestedTypeInfo.getFlatFields("f1")).hasSize(3);
    }

    @Test
    void testGetFlatFields_30() {
        assertThat(nestedTypeInfo.getFlatFields("f1").get(0).getPosition()).isOne();
    }

    @Test
    void testGetFlatFields_31() {
        assertThat(nestedTypeInfo.getFlatFields("f1").get(1).getPosition()).isEqualTo(2);
    }

    @Test
    void testGetFlatFields_32() {
        assertThat(nestedTypeInfo.getFlatFields("f1").get(2).getPosition()).isEqualTo(3);
    }

    @Test
    void testGetFlatFields_33() {
        assertThat(nestedTypeInfo.getFlatFields("f3")).hasSize(2);
    }

    @Test
    void testGetFlatFields_34() {
        assertThat(nestedTypeInfo.getFlatFields("f3").get(0).getPosition()).isEqualTo(5);
    }

    @Test
    void testGetFlatFields_35() {
        assertThat(nestedTypeInfo.getFlatFields("f3").get(1).getPosition()).isEqualTo(6);
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
    void testGetFlatFields_40() {
        assertThat(deepNestedTupleTypeInfo.getFlatFields("1")).hasSize(3);
    }

    @Test
    void testGetFlatFields_41() {
        assertThat(deepNestedTupleTypeInfo.getFlatFields("1").get(0).getPosition()).isOne();
    }

    @Test
    void testGetFlatFields_42() {
        assertThat(deepNestedTupleTypeInfo.getFlatFields("1").get(1).getPosition()).isEqualTo(2);
    }

    @Test
    void testGetFlatFields_43() {
        assertThat(deepNestedTupleTypeInfo.getFlatFields("1").get(2).getPosition()).isEqualTo(3);
    }

    @Test
    void testGetFlatFields_44() {
        assertThat(deepNestedTupleTypeInfo.getFlatFields("*")).hasSize(5);
    }

    @Test
    void testGetFlatFields_45() {
        assertThat(deepNestedTupleTypeInfo.getFlatFields("*").get(0).getPosition()).isZero();
    }

    @Test
    void testGetFlatFields_46() {
        assertThat(deepNestedTupleTypeInfo.getFlatFields("*").get(1).getPosition()).isOne();
    }

    @Test
    void testGetFlatFields_47() {
        assertThat(deepNestedTupleTypeInfo.getFlatFields("*").get(2).getPosition()).isEqualTo(2);
    }

    @Test
    void testGetFlatFields_48() {
        assertThat(deepNestedTupleTypeInfo.getFlatFields("*").get(3).getPosition()).isEqualTo(3);
    }

    @Test
    void testGetFlatFields_49() {
        assertThat(deepNestedTupleTypeInfo.getFlatFields("*").get(4).getPosition()).isEqualTo(4);
    }

    @Test
    void testGetFlatFields_50() {
        assertThat(pojoTypeInfo.getFlatFields("a").get(0).getPosition()).isZero();
    }

    @Test
    void testGetFlatFields_51() {
        assertThat(pojoTypeInfo.getFlatFields("b").get(0).getPosition()).isOne();
    }

    @Test
    void testGetFlatFields_52() {
        assertThat(pojoTypeInfo.getFlatFields("*")).hasSize(2);
    }

    @Test
    void testGetFlatFields_53() {
        assertThat(pojoTypeInfo.getFlatFields("*").get(0).getPosition()).isZero();
    }

    @Test
    void testGetFlatFields_54() {
        assertThat(pojoTypeInfo.getFlatFields("*").get(1).getPosition()).isOne();
    }

    @Test
    void testGetFlatFields_55() {
        assertThat(pojoInTupleTypeInfo.getFlatFields("f1.a").get(0).getPosition()).isOne();
    }

    @Test
    void testGetFlatFields_56() {
        assertThat(pojoInTupleTypeInfo.getFlatFields("1.b").get(0).getPosition()).isEqualTo(2);
    }

    @Test
    void testGetFlatFields_57() {
        assertThat(pojoInTupleTypeInfo.getFlatFields("1")).hasSize(2);
    }

    @Test
    void testGetFlatFields_58() {
        assertThat(pojoInTupleTypeInfo.getFlatFields("1.*").get(0).getPosition()).isOne();
    }

    @Test
    void testGetFlatFields_59() {
        assertThat(pojoInTupleTypeInfo.getFlatFields("1").get(1).getPosition()).isEqualTo(2);
    }

    @Test
    void testGetFlatFields_60() {
        assertThat(pojoInTupleTypeInfo.getFlatFields("f1.*")).hasSize(2);
    }

    @Test
    void testGetFlatFields_61() {
        assertThat(pojoInTupleTypeInfo.getFlatFields("f1.*").get(0).getPosition()).isOne();
    }

    @Test
    void testGetFlatFields_62() {
        assertThat(pojoInTupleTypeInfo.getFlatFields("f1").get(1).getPosition()).isEqualTo(2);
    }

    @Test
    void testGetFlatFields_63() {
        assertThat(pojoInTupleTypeInfo.getFlatFields("*")).hasSize(3);
    }

    @Test
    void testGetFlatFields_64() {
        assertThat(pojoInTupleTypeInfo.getFlatFields("*").get(0).getPosition()).isZero();
    }

    @Test
    void testGetFlatFields_65() {
        assertThat(pojoInTupleTypeInfo.getFlatFields("*").get(1).getPosition()).isOne();
    }

    @Test
    void testGetFlatFields_66() {
        assertThat(pojoInTupleTypeInfo.getFlatFields("*").get(2).getPosition()).isEqualTo(2);
    }

    @Test
    void testFieldAtStringRef_1() {
        assertThat(tupleTypeInfo.getTypeAt("0")).isEqualTo(BasicTypeInfo.INT_TYPE_INFO);
    }

    @Test
    void testFieldAtStringRef_2() {
        assertThat(tupleTypeInfo.getTypeAt("2")).isEqualTo(BasicTypeInfo.INT_TYPE_INFO);
    }

    @Test
    void testFieldAtStringRef_3() {
        assertThat(tupleTypeInfo.getTypeAt("f1")).isEqualTo(BasicTypeInfo.INT_TYPE_INFO);
    }

    @Test
    void testFieldAtStringRef_4() {
        assertThat(tupleTypeInfo.getTypeAt("f3")).isEqualTo(BasicTypeInfo.INT_TYPE_INFO);
    }

    @Test
    void testFieldAtStringRef_5() {
        assertThat(nestedTypeInfo.getTypeAt("0")).isEqualTo(BasicTypeInfo.INT_TYPE_INFO);
    }

    @Test
    void testFieldAtStringRef_6() {
        assertThat(nestedTypeInfo.getTypeAt("1.0")).isEqualTo(BasicTypeInfo.INT_TYPE_INFO);
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
    void testFieldAtStringRef_9() {
        assertThat(nestedTypeInfo.getTypeAt("2")).isEqualTo(BasicTypeInfo.INT_TYPE_INFO);
    }

    @Test
    void testFieldAtStringRef_10() {
        assertThat(nestedTypeInfo.getTypeAt("3.0")).isEqualTo(BasicTypeInfo.DOUBLE_TYPE_INFO);
    }

    @Test
    void testFieldAtStringRef_11() {
        assertThat(nestedTypeInfo.getTypeAt("3.1")).isEqualTo(BasicTypeInfo.DOUBLE_TYPE_INFO);
    }

    @Test
    void testFieldAtStringRef_12() {
        assertThat(nestedTypeInfo.getTypeAt("f2")).isEqualTo(BasicTypeInfo.INT_TYPE_INFO);
    }

    @Test
    void testFieldAtStringRef_13() {
        assertThat(nestedTypeInfo.getTypeAt("f3.f0")).isEqualTo(BasicTypeInfo.DOUBLE_TYPE_INFO);
    }

    @Test
    void testFieldAtStringRef_14() {
        assertThat(nestedTypeInfo.getTypeAt("1")).isEqualTo(inNestedTuple1);
    }

    @Test
    void testFieldAtStringRef_15() {
        assertThat(nestedTypeInfo.getTypeAt("3")).isEqualTo(inNestedTuple2);
    }

    @Test
    void testFieldAtStringRef_16() {
        assertThat(nestedTypeInfo.getTypeAt("f1")).isEqualTo(inNestedTuple1);
    }

    @Test
    void testFieldAtStringRef_17() {
        assertThat(nestedTypeInfo.getTypeAt("f3")).isEqualTo(inNestedTuple2);
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

    @Test
    void testFieldAtStringRef_23() {
        assertThat(pojoInTupleTypeInfo.getTypeAt("1")).isEqualTo(pojoTypeInfo);
    }

    @Test
    void testFieldAtStringRef_24() {
        assertThat(pojoInTupleTypeInfo.getTypeAt("f1")).isEqualTo(pojoTypeInfo);
    }
}
