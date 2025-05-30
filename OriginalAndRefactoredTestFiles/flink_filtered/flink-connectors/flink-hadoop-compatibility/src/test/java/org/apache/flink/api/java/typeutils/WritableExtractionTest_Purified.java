package org.apache.flink.api.java.typeutils;

import org.apache.flink.api.common.functions.InvalidTypesException;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparator;
import org.junit.jupiter.api.Test;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Fail.fail;

class WritableExtractionTest_Purified {

    private interface ExtendedWritable extends Writable {
    }

    private abstract static class AbstractWritable implements Writable {
    }

    private static class DirectWritable implements Writable {

        @Override
        public void write(DataOutput dataOutput) throws IOException {
        }

        @Override
        public void readFields(DataInput dataInput) throws IOException {
        }
    }

    private static class ViaInterfaceExtension implements ExtendedWritable {

        @Override
        public void write(DataOutput dataOutput) throws IOException {
        }

        @Override
        public void readFields(DataInput dataInput) throws IOException {
        }
    }

    private static class ViaAbstractClassExtension extends AbstractWritable {

        @Override
        public void write(DataOutput dataOutput) throws IOException {
        }

        @Override
        public void readFields(DataInput dataInput) throws IOException {
        }
    }

    public static class PojoWithWritable {

        public String str;

        public DirectWritable hadoopCitizen;
    }

    @Test
    void testDetectWritable_1() {
        assertThat(TypeExtractor.isHadoopWritable(Writable.class)).isFalse();
    }

    @Test
    void testDetectWritable_2() {
        assertThat(TypeExtractor.isHadoopWritable(DirectWritable.class)).isTrue();
    }

    @Test
    void testDetectWritable_3() {
        assertThat(TypeExtractor.isHadoopWritable(ViaInterfaceExtension.class)).isTrue();
    }

    @Test
    void testDetectWritable_4() {
        assertThat(TypeExtractor.isHadoopWritable(ViaAbstractClassExtension.class)).isTrue();
    }

    @Test
    void testDetectWritable_5() {
        assertThat(TypeExtractor.isHadoopWritable(String.class)).isFalse();
    }

    @Test
    void testDetectWritable_6() {
        assertThat(TypeExtractor.isHadoopWritable(List.class)).isFalse();
    }

    @Test
    void testDetectWritable_7() {
        assertThat(TypeExtractor.isHadoopWritable(WritableComparator.class)).isFalse();
    }
}
