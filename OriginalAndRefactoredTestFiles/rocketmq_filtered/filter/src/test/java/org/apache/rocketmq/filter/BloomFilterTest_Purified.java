package org.apache.rocketmq.filter;

import org.apache.rocketmq.filter.util.BitsArray;
import org.apache.rocketmq.filter.util.BloomFilter;
import org.apache.rocketmq.filter.util.BloomFilterData;
import org.junit.Test;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;

public class BloomFilterTest_Purified {

    private String randomString(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append((char) ((new Random(System.nanoTime())).nextInt(123 - 97) + 97));
        }
        return stringBuilder.toString();
    }

    @Test
    public void testBloomFilterData_1_testMerged_1() {
        BloomFilterData bloomFilterData = new BloomFilterData(new int[] { 1, 2, 3 }, 128);
        BloomFilterData bloomFilterData1 = new BloomFilterData(new int[] { 1, 2, 3 }, 128);
        BloomFilterData bloomFilterData2 = new BloomFilterData(new int[] { 1, 2, 3 }, 129);
        assertThat(bloomFilterData).isEqualTo(bloomFilterData1);
        assertThat(bloomFilterData2).isNotEqualTo(bloomFilterData);
        assertThat(bloomFilterData2).isNotEqualTo(bloomFilterData1);
        assertThat(bloomFilterData.hashCode()).isEqualTo(bloomFilterData1.hashCode());
        assertThat(bloomFilterData2.hashCode()).isNotEqualTo(bloomFilterData.hashCode());
        assertThat(bloomFilterData2.hashCode()).isNotEqualTo(bloomFilterData1.hashCode());
        assertThat(bloomFilterData.getBitPos()).isEqualTo(bloomFilterData2.getBitPos());
        assertThat(bloomFilterData.getBitNum()).isEqualTo(bloomFilterData1.getBitNum());
        assertThat(bloomFilterData.getBitNum()).isNotEqualTo(bloomFilterData2.getBitNum());
        bloomFilterData2.setBitNum(128);
        assertThat(bloomFilterData).isEqualTo(bloomFilterData2);
        bloomFilterData2.setBitPos(new int[] { 1, 2, 3, 4 });
        assertThat(bloomFilterData).isNotEqualTo(bloomFilterData2);
        BloomFilter bloomFilter = BloomFilter.createByFn(1, 300);
        assertThat(bloomFilter).isNotNull();
        assertThat(bloomFilter.isValid(bloomFilterData)).isFalse();
    }

    @Test
    public void testBloomFilterData_12_testMerged_2() {
        BloomFilterData nullData = new BloomFilterData();
        assertThat(nullData.getBitNum()).isEqualTo(0);
        assertThat(nullData.getBitPos()).isNull();
    }
}
