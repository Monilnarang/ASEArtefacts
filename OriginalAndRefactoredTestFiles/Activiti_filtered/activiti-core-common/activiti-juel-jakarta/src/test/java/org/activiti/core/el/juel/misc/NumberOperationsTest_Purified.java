package org.activiti.core.el.juel.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.activiti.core.el.juel.test.TestCase;
import org.junit.jupiter.api.Test;

public class NumberOperationsTest_Purified extends TestCase {

    private TypeConverter converter = TypeConverter.DEFAULT;

    @Test
    public void testAdd_1() {
        assertEquals(Long.valueOf(0), NumberOperations.add(converter, null, null));
    }

    @Test
    public void testAdd_2_testMerged_2() {
        BigDecimal bd1 = new BigDecimal(1);
        Integer i1 = Integer.valueOf(1);
        Long l1 = Long.valueOf(1);
        Float f1 = Float.valueOf(1);
        Double d1 = Double.valueOf(1);
        String e1 = "1e0";
        String s1 = "1";
        BigInteger bi1 = new BigInteger("1");
        Long l2 = Long.valueOf(2);
        BigDecimal bd2 = new BigDecimal(2);
        Double d2 = Double.valueOf(2);
        BigInteger bi2 = new BigInteger("2");
        assertEquals(bd2, NumberOperations.add(converter, l1, bd1));
        assertEquals(bd2, NumberOperations.add(converter, bd1, l1));
        assertEquals(bd2, NumberOperations.add(converter, f1, bi1));
        assertEquals(bd2, NumberOperations.add(converter, bi1, f1));
        assertEquals(d2, NumberOperations.add(converter, f1, l1));
        assertEquals(d2, NumberOperations.add(converter, l1, f1));
        assertEquals(bd2, NumberOperations.add(converter, d1, bi1));
        assertEquals(bd2, NumberOperations.add(converter, bi1, d1));
        assertEquals(d2, NumberOperations.add(converter, d1, l1));
        assertEquals(d2, NumberOperations.add(converter, l1, d1));
        assertEquals(bd2, NumberOperations.add(converter, e1, bi1));
        assertEquals(bd2, NumberOperations.add(converter, bi1, e1));
        assertEquals(d2, NumberOperations.add(converter, e1, l1));
        assertEquals(d2, NumberOperations.add(converter, l1, e1));
        assertEquals(bi2, NumberOperations.add(converter, l1, bi1));
        assertEquals(bi2, NumberOperations.add(converter, bi1, l1));
        assertEquals(l2, NumberOperations.add(converter, i1, l1));
        assertEquals(l2, NumberOperations.add(converter, l1, i1));
        assertEquals(l2, NumberOperations.add(converter, i1, s1));
        assertEquals(l2, NumberOperations.add(converter, s1, i1));
    }

    @Test
    public void testSub_1() {
        assertEquals(Long.valueOf(0), NumberOperations.sub(converter, null, null));
    }

    @Test
    public void testSub_2_testMerged_2() {
        BigDecimal bd1 = new BigDecimal(1);
        Integer i1 = Integer.valueOf(1);
        Long l1 = Long.valueOf(1);
        Float f1 = Float.valueOf(1);
        Double d1 = Double.valueOf(1);
        String e1 = "1e0";
        String s1 = "1";
        BigInteger bi1 = new BigInteger("1");
        Long l2 = Long.valueOf(0);
        BigDecimal bd2 = new BigDecimal(0);
        Double d2 = Double.valueOf(0);
        BigInteger bi2 = new BigInteger("0");
        assertEquals(bd2, NumberOperations.sub(converter, l1, bd1));
        assertEquals(bd2, NumberOperations.sub(converter, bd1, l1));
        assertEquals(bd2, NumberOperations.sub(converter, f1, bi1));
        assertEquals(bd2, NumberOperations.sub(converter, bi1, f1));
        assertEquals(d2, NumberOperations.sub(converter, f1, l1));
        assertEquals(d2, NumberOperations.sub(converter, l1, f1));
        assertEquals(bd2, NumberOperations.sub(converter, d1, bi1));
        assertEquals(bd2, NumberOperations.sub(converter, bi1, d1));
        assertEquals(d2, NumberOperations.sub(converter, d1, l1));
        assertEquals(d2, NumberOperations.sub(converter, l1, d1));
        assertEquals(bd2, NumberOperations.sub(converter, e1, bi1));
        assertEquals(bd2, NumberOperations.sub(converter, bi1, e1));
        assertEquals(d2, NumberOperations.sub(converter, e1, l1));
        assertEquals(d2, NumberOperations.sub(converter, l1, e1));
        assertEquals(bi2, NumberOperations.sub(converter, l1, bi1));
        assertEquals(bi2, NumberOperations.sub(converter, bi1, l1));
        assertEquals(l2, NumberOperations.sub(converter, i1, l1));
        assertEquals(l2, NumberOperations.sub(converter, l1, i1));
        assertEquals(l2, NumberOperations.sub(converter, i1, s1));
        assertEquals(l2, NumberOperations.sub(converter, s1, i1));
    }

    @Test
    public void testMul_1() {
        assertEquals(Long.valueOf(0), NumberOperations.mul(converter, null, null));
    }

    @Test
    public void testMul_2_testMerged_2() {
        BigDecimal bd1 = new BigDecimal(1);
        Integer i1 = Integer.valueOf(1);
        Long l1 = Long.valueOf(1);
        Float f1 = Float.valueOf(1);
        Double d1 = Double.valueOf(1);
        String e1 = "1e0";
        String s1 = "1";
        BigInteger bi1 = new BigInteger("1");
        Long l2 = Long.valueOf(1);
        BigDecimal bd2 = new BigDecimal(1);
        Double d2 = Double.valueOf(1);
        BigInteger bi2 = new BigInteger("1");
        assertEquals(bd2, NumberOperations.mul(converter, l1, bd1));
        assertEquals(bd2, NumberOperations.mul(converter, bd1, l1));
        assertEquals(bd2, NumberOperations.mul(converter, f1, bi1));
        assertEquals(bd2, NumberOperations.mul(converter, bi1, f1));
        assertEquals(d2, NumberOperations.mul(converter, f1, l1));
        assertEquals(d2, NumberOperations.mul(converter, l1, f1));
        assertEquals(bd2, NumberOperations.mul(converter, d1, bi1));
        assertEquals(bd2, NumberOperations.mul(converter, bi1, d1));
        assertEquals(d2, NumberOperations.mul(converter, d1, l1));
        assertEquals(d2, NumberOperations.mul(converter, l1, d1));
        assertEquals(bd2, NumberOperations.mul(converter, e1, bi1));
        assertEquals(bd2, NumberOperations.mul(converter, bi1, e1));
        assertEquals(d2, NumberOperations.mul(converter, e1, l1));
        assertEquals(d2, NumberOperations.mul(converter, l1, e1));
        assertEquals(bi2, NumberOperations.mul(converter, l1, bi1));
        assertEquals(bi2, NumberOperations.mul(converter, bi1, l1));
        assertEquals(l2, NumberOperations.mul(converter, i1, l1));
        assertEquals(l2, NumberOperations.mul(converter, l1, i1));
        assertEquals(l2, NumberOperations.mul(converter, i1, s1));
        assertEquals(l2, NumberOperations.mul(converter, s1, i1));
    }

    @Test
    public void testDiv_1() {
        assertEquals(Long.valueOf(0), NumberOperations.div(converter, null, null));
    }

    @Test
    public void testDiv_2_testMerged_2() {
        BigDecimal bd1 = new BigDecimal(1);
        Integer i1 = Integer.valueOf(1);
        Long l1 = Long.valueOf(1);
        Float f1 = Float.valueOf(1);
        Double d1 = Double.valueOf(1);
        String e1 = "1e0";
        String s1 = "1";
        BigInteger bi1 = new BigInteger("1");
        BigDecimal bd2 = new BigDecimal(1);
        Double d2 = Double.valueOf(1);
        assertEquals(bd2, NumberOperations.div(converter, l1, bd1));
        assertEquals(bd2, NumberOperations.div(converter, bd1, l1));
        assertEquals(bd2, NumberOperations.div(converter, f1, bi1));
        assertEquals(bd2, NumberOperations.div(converter, bi1, f1));
        assertEquals(d2, NumberOperations.div(converter, f1, l1));
        assertEquals(d2, NumberOperations.div(converter, l1, f1));
        assertEquals(d2, NumberOperations.div(converter, d1, l1));
        assertEquals(d2, NumberOperations.div(converter, l1, d1));
        assertEquals(d2, NumberOperations.div(converter, e1, l1));
        assertEquals(d2, NumberOperations.div(converter, l1, e1));
        assertEquals(d2, NumberOperations.div(converter, i1, l1));
        assertEquals(d2, NumberOperations.div(converter, l1, i1));
        assertEquals(d2, NumberOperations.div(converter, i1, s1));
        assertEquals(d2, NumberOperations.div(converter, s1, i1));
    }

    @Test
    public void testMod_1() {
        assertEquals(Long.valueOf(0), NumberOperations.mod(converter, null, null));
    }

    @Test
    public void testMod_2_testMerged_2() {
        BigDecimal bd1 = new BigDecimal(1);
        Integer i1 = Integer.valueOf(1);
        Long l1 = Long.valueOf(1);
        Float f1 = Float.valueOf(1);
        Double d1 = Double.valueOf(1);
        String e1 = "1e0";
        String s1 = "1";
        BigInteger bi1 = new BigInteger("1");
        Long l2 = Long.valueOf(0);
        Double d2 = Double.valueOf(0);
        BigInteger bi2 = new BigInteger("0");
        assertEquals(d2, NumberOperations.mod(converter, l1, bd1));
        assertEquals(d2, NumberOperations.mod(converter, bd1, l1));
        assertEquals(d2, NumberOperations.mod(converter, f1, bi1));
        assertEquals(d2, NumberOperations.mod(converter, bi1, f1));
        assertEquals(d2, NumberOperations.mod(converter, f1, l1));
        assertEquals(d2, NumberOperations.mod(converter, l1, f1));
        assertEquals(d2, NumberOperations.mod(converter, d1, l1));
        assertEquals(d2, NumberOperations.mod(converter, l1, d1));
        assertEquals(d2, NumberOperations.mod(converter, d1, bi1));
        assertEquals(d2, NumberOperations.mod(converter, bi1, d1));
        assertEquals(d2, NumberOperations.mod(converter, e1, bi1));
        assertEquals(d2, NumberOperations.mod(converter, bi1, e1));
        assertEquals(d2, NumberOperations.mod(converter, e1, l1));
        assertEquals(d2, NumberOperations.mod(converter, l1, e1));
        assertEquals(bi2, NumberOperations.mod(converter, l1, bi1));
        assertEquals(bi2, NumberOperations.mod(converter, bi1, l1));
        assertEquals(l2, NumberOperations.mod(converter, i1, l1));
        assertEquals(l2, NumberOperations.mod(converter, l1, i1));
        assertEquals(l2, NumberOperations.mod(converter, i1, s1));
        assertEquals(l2, NumberOperations.mod(converter, s1, i1));
    }

    @Test
    public void testNeg_1() {
        assertEquals(Long.valueOf(0), NumberOperations.neg(converter, null));
    }

    @Test
    public void testNeg_2() {
        BigDecimal bd1 = new BigDecimal(1);
        BigDecimal bd2 = new BigDecimal(-1);
        assertEquals(bd2, NumberOperations.neg(converter, bd1));
    }

    @Test
    public void testNeg_3() {
        BigInteger bi1 = new BigInteger("1");
        BigInteger bi2 = new BigInteger("-1");
        assertEquals(bi2, NumberOperations.neg(converter, bi1));
    }

    @Test
    public void testNeg_4_testMerged_4() {
        Double d1 = Double.valueOf(1);
        String e1 = "1e0";
        Double d2 = Double.valueOf(-1);
        assertEquals(d2, NumberOperations.neg(converter, e1));
        assertEquals(d2, NumberOperations.neg(converter, d1));
    }

    @Test
    public void testNeg_5_testMerged_5() {
        Long l1 = Long.valueOf(1);
        String s1 = "1";
        Long l2 = Long.valueOf(-1);
        assertEquals(l2, NumberOperations.neg(converter, s1));
        assertEquals(l2, NumberOperations.neg(converter, l1));
    }

    @Test
    public void testNeg_6() {
        Integer i1 = Integer.valueOf(1);
        Integer i2 = Integer.valueOf(-1);
        assertEquals(i2, NumberOperations.neg(converter, i1));
    }

    @Test
    public void testNeg_9() {
        Float f1 = Float.valueOf(1);
        Float f2 = Float.valueOf(-1);
        assertEquals(f2, NumberOperations.neg(converter, f1));
    }
}
