package org.apache.rocketmq.common;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UtilAllTest_Purified {

    @Rule
    public TemporaryFolder tempDir = new TemporaryFolder();

    static class DemoConfig {

        private int demoWidth = 0;

        private int demoLength = 0;

        private boolean demoOK = false;

        private String demoName = "haha";

        int getDemoWidth() {
            return demoWidth;
        }

        public void setDemoWidth(int demoWidth) {
            this.demoWidth = demoWidth;
        }

        public int getDemoLength() {
            return demoLength;
        }

        public void setDemoLength(int demoLength) {
            this.demoLength = demoLength;
        }

        public boolean isDemoOK() {
            return demoOK;
        }

        public void setDemoOK(boolean demoOK) {
            this.demoOK = demoOK;
        }

        public String getDemoName() {
            return demoName;
        }

        public void setDemoName(String demoName) {
            this.demoName = demoName;
        }

        @Override
        public String toString() {
            return "DemoConfig{" + "demoWidth=" + demoWidth + ", demoLength=" + demoLength + ", demoOK=" + demoOK + ", demoName='" + demoName + '\'' + '}';
        }
    }

    static class DemoSubConfig extends DemoConfig {

        private String subField0 = "0";

        public boolean subField1 = true;

        public String getSubField0() {
            return subField0;
        }

        public void setSubField0(String subField0) {
            this.subField0 = subField0;
        }

        public boolean isSubField1() {
            return subField1;
        }

        public void setSubField1(boolean subField1) {
            this.subField1 = subField1;
        }
    }

    private void writeFixedBytesToFile(File file, int size) throws Exception {
        FileOutputStream outputStream = new FileOutputStream(file);
        byte[] bytes = new byte[size];
        outputStream.write(bytes, 0, size);
        outputStream.close();
    }

    @Test
    public void testGetDiskPartitionSpaceUsedPercent_1() {
        assertThat(UtilAll.getDiskPartitionSpaceUsedPercent(null)).isCloseTo(-1, within(0.000001));
    }

    @Test
    public void testGetDiskPartitionSpaceUsedPercent_2() {
        assertThat(UtilAll.getDiskPartitionSpaceUsedPercent("")).isCloseTo(-1, within(0.000001));
    }

    @Test
    public void testGetDiskPartitionSpaceUsedPercent_3() {
        assertThat(UtilAll.getDiskPartitionSpaceUsedPercent("nonExistingPath")).isCloseTo(-1, within(0.000001));
    }

    @Test
    public void testGetDiskPartitionSpaceUsedPercent_4() {
        String tmpDir = System.getProperty("java.io.tmpdir");
        assertThat(UtilAll.getDiskPartitionSpaceUsedPercent(tmpDir)).isNotCloseTo(-1, within(0.000001));
    }

    @Test
    public void testIsBlank_1() {
        assertThat(UtilAll.isBlank("Hello ")).isFalse();
    }

    @Test
    public void testIsBlank_2() {
        assertThat(UtilAll.isBlank(" Hello")).isFalse();
    }

    @Test
    public void testIsBlank_3() {
        assertThat(UtilAll.isBlank("He llo")).isFalse();
    }

    @Test
    public void testIsBlank_4() {
        assertThat(UtilAll.isBlank("  ")).isTrue();
    }

    @Test
    public void testIsBlank_5() {
        assertThat(UtilAll.isBlank("Hello")).isFalse();
    }
}
