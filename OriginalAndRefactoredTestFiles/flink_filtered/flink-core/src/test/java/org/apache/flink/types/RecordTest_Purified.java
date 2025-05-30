package org.apache.flink.types;

import org.apache.flink.core.memory.DataInputView;
import org.apache.flink.core.memory.DataInputViewStreamWrapper;
import org.apache.flink.core.memory.DataOutputView;
import org.apache.flink.core.memory.DataOutputViewStreamWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Arrays;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;

public class RecordTest_Purified {

    private static final long SEED = 354144423270432543L;

    private final Random rand = new Random(RecordTest.SEED);

    private DataInputView in;

    private DataOutputView out;

    private final StringValue origVal1 = new StringValue("Hello World!");

    private final DoubleValue origVal2 = new DoubleValue(Math.PI);

    private final IntValue origVal3 = new IntValue(1337);

    @BeforeEach
    void setUp() throws Exception {
        PipedInputStream pipeIn = new PipedInputStream(1024 * 1024);
        PipedOutputStream pipeOut = new PipedOutputStream(pipeIn);
        this.in = new DataInputViewStreamWrapper(pipeIn);
        this.out = new DataOutputViewStreamWrapper(pipeOut);
    }

    private Record generateFilledDenseRecord(int numFields) {
        Record record = new Record();
        for (int i = 0; i < numFields; i++) {
            record.addField(new IntValue(this.rand.nextInt()));
        }
        return record;
    }

    private long generateRandomBitmask(int numFields) {
        long bitmask = 0L;
        long tmp;
        for (int i = 0; i < numFields; i++) {
            tmp = this.rand.nextBoolean() ? 1L : 0L;
            bitmask = bitmask | (tmp << i);
        }
        return bitmask;
    }

    static void blackboxTestRecordWithValues(Value[] values, Random rnd, DataInputView reader, DataOutputView writer) throws Exception {
        final int[] permutation1 = createPermutation(rnd, values.length);
        final int[] permutation2 = createPermutation(rnd, values.length);
        Record rec = new Record();
        for (int i = 0; i < values.length; i++) {
            final int pos = permutation1[i];
            rec.setField(pos, values[pos]);
        }
        testAllRetrievalMethods(rec, permutation2, values);
        rec = new Record();
        for (int i = 0; i < values.length; i++) {
            final int pos = permutation1[i];
            rec.setField(pos, values[pos]);
        }
        rec.updateBinaryRepresenation();
        testAllRetrievalMethods(rec, permutation2, values);
        rec = new Record();
        int updatePos = rnd.nextInt(values.length + 1);
        for (int i = 0; i < values.length; i++) {
            if (i == updatePos) {
                rec.updateBinaryRepresenation();
            }
            final int pos = permutation1[i];
            rec.setField(pos, values[pos]);
        }
        if (updatePos == values.length) {
            rec.updateBinaryRepresenation();
        }
        testAllRetrievalMethods(rec, permutation2, values);
        rec = new Record();
        for (int i = 0; i < values.length; i++) {
            final int pos = permutation1[i];
            rec.setField(pos, values[pos]);
        }
        rec.write(writer);
        rec = new Record();
        rec.read(reader);
        testAllRetrievalMethods(rec, permutation2, values);
        rec = new Record();
        for (int i = 0; i < values.length; i++) {
            final int pos = permutation1[i];
            rec.setField(pos, values[pos]);
        }
        rec.write(writer);
        rec.read(reader);
        testAllRetrievalMethods(rec, permutation2, values);
        rec = new Record();
        updatePos = rnd.nextInt(values.length + 1);
        for (int i = 0; i < values.length; i++) {
            if (i == updatePos) {
                rec.write(writer);
                rec = new Record();
                rec.read(reader);
            }
            final int pos = permutation1[i];
            rec.setField(pos, values[pos]);
        }
        if (updatePos == values.length) {
            rec.write(writer);
            rec = new Record();
            rec.read(reader);
        }
        testAllRetrievalMethods(rec, permutation2, values);
        rec = new Record();
        updatePos = rnd.nextInt(values.length + 1);
        for (int i = 0; i < values.length; i++) {
            if (i == updatePos) {
                rec.write(writer);
                rec.read(reader);
            }
            final int pos = permutation1[i];
            rec.setField(pos, values[pos]);
        }
        if (updatePos == values.length) {
            rec.write(writer);
            rec.read(reader);
        }
        testAllRetrievalMethods(rec, permutation2, values);
        rec = new Record();
        updatePos = rnd.nextInt(values.length + 1);
        for (int i = 0; i < values.length; i++) {
            if (i == updatePos) {
                rec.write(writer);
                rec = new Record();
                rec.read(reader);
            }
            final int pos = permutation1[i];
            rec.setField(pos, values[pos]);
        }
        rec.write(writer);
        rec = new Record();
        rec.read(reader);
        testAllRetrievalMethods(rec, permutation2, values);
        rec = new Record();
        updatePos = rnd.nextInt(values.length + 1);
        for (int i = 0; i < values.length; i++) {
            if (i == updatePos) {
                rec.write(writer);
                rec.read(reader);
            }
            final int pos = permutation1[i];
            rec.setField(pos, values[pos]);
        }
        rec.write(writer);
        rec.read(reader);
        testAllRetrievalMethods(rec, permutation2, values);
    }

    private static void checkUnionedRecord(Record union, Value[] rec1fields, Value[] rec2fields) {
        for (int i = 0; i < Math.max(rec1fields.length, rec2fields.length); i++) {
            final Value expected;
            if (i < rec1fields.length) {
                if (i < rec2fields.length) {
                    expected = rec1fields[i] == null ? rec2fields[i] : rec1fields[i];
                } else {
                    expected = rec1fields[i];
                }
            } else {
                expected = rec2fields[i];
            }
            if (expected == null) {
                final Value retrieved = union.getField(i, IntValue.class);
                assertThat(retrieved).describedAs("Value at position " + i + " expected to be null in " + Arrays.toString(rec1fields) + " U " + Arrays.toString(rec2fields)).isNull();
            } else {
                final Value retrieved = union.getField(i, expected.getClass());
                assertThat(retrieved).describedAs("Wrong value at position " + i + " in " + Arrays.toString(rec1fields) + " U " + Arrays.toString(rec2fields)).isEqualTo(expected);
            }
        }
    }

    public static Record createRecord(Value[] fields) {
        final Record rec = new Record();
        for (int i = 0; i < fields.length; i++) {
            rec.setField(i, fields[i]);
        }
        return rec;
    }

    public static Value[] createRandomValues(Random rnd, int minNum, int maxNum) {
        final int numFields = rnd.nextInt(maxNum - minNum + 1) + minNum;
        final Value[] values = new Value[numFields];
        for (int i = 0; i < numFields; i++) {
            final int type = rnd.nextInt(7);
            switch(type) {
                case 0:
                    values[i] = new IntValue(rnd.nextInt());
                    break;
                case 1:
                    values[i] = new LongValue(rnd.nextLong());
                    break;
                case 2:
                    values[i] = new DoubleValue(rnd.nextDouble());
                    break;
                case 3:
                    values[i] = NullValue.getInstance();
                    break;
                case 4:
                    values[i] = new StringValue(createRandomString(rnd));
                    break;
                default:
                    values[i] = null;
            }
        }
        return values;
    }

    public static String createRandomString(Random rnd) {
        return createRandomString(rnd, rnd.nextInt(150));
    }

    public static String createRandomString(Random rnd, int length) {
        final StringBuilder sb = new StringBuilder();
        sb.ensureCapacity(length);
        for (int i = 0; i < length; i++) {
            sb.append((char) (rnd.nextInt(26) + 65));
        }
        return sb.toString();
    }

    public static int[] createPermutation(Random rnd, int length) {
        final int[] a = new int[length];
        for (int i = 0; i < length; i++) {
            a[i] = i;
        }
        for (int i = 0; i < length; i++) {
            final int pos1 = rnd.nextInt(length);
            final int pos2 = rnd.nextInt(length);
            int temp = a[pos1];
            a[pos1] = a[pos2];
            a[pos2] = temp;
        }
        return a;
    }

    @Test
    void testClear_1_testMerged_1() throws IOException {
        Record record = new Record(new IntValue(42));
        record.write(this.out);
        assertThat(record.getField(0, IntValue.class).getValue()).isEqualTo(42);
        record.setField(0, new IntValue(23));
        assertThat(record.getField(0, IntValue.class).getValue()).isEqualTo(23);
        record.clear();
        assertThat(record.getNumFields()).isZero();
    }

    @Test
    void testClear_4_testMerged_2() throws IOException {
        Record record2 = new Record(new IntValue(42));
        record2.read(in);
        assertThat(record2.getField(0, IntValue.class).getValue()).isEqualTo(42);
        assertThat(record2.getField(0, IntValue.class).getValue()).isEqualTo(23);
    }
}
