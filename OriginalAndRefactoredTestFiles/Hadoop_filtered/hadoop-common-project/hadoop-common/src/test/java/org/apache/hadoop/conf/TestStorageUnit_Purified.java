package org.apache.hadoop.conf;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestStorageUnit_Purified {

    final static double KB = 1024.0;

    final static double MB = KB * 1024.0;

    final static double GB = MB * 1024.0;

    final static double TB = GB * 1024.0;

    final static double PB = TB * 1024.0;

    final static double EB = PB * 1024.0;

    @Test
    public void testByteConversions_1() {
        assertThat(StorageUnit.BYTES.getShortName(), is("b"));
    }

    @Test
    public void testByteConversions_2() {
        assertThat(StorageUnit.BYTES.getSuffixChar(), is("b"));
    }

    @Test
    public void testByteConversions_3() {
        assertThat(StorageUnit.BYTES.getLongName(), is("bytes"));
    }

    @Test
    public void testByteConversions_4() {
        assertThat(StorageUnit.BYTES.toString(), is("bytes"));
    }

    @Test
    public void testByteConversions_5() {
        assertThat(StorageUnit.BYTES.toBytes(1), is(1.0));
    }

    @Test
    public void testByteConversions_6() {
        assertThat(StorageUnit.BYTES.toBytes(1024), is(StorageUnit.BYTES.getDefault(1024)));
    }

    @Test
    public void testByteConversions_7() {
        assertThat(StorageUnit.BYTES.fromBytes(10), is(10.0));
    }

    @Test
    public void testKBConversions_1() {
        assertThat(StorageUnit.KB.getShortName(), is("kb"));
    }

    @Test
    public void testKBConversions_2() {
        assertThat(StorageUnit.KB.getSuffixChar(), is("k"));
    }

    @Test
    public void testKBConversions_3() {
        assertThat(StorageUnit.KB.getLongName(), is("kilobytes"));
    }

    @Test
    public void testKBConversions_4() {
        assertThat(StorageUnit.KB.toString(), is("kilobytes"));
    }

    @Test
    public void testKBConversions_5() {
        assertThat(StorageUnit.KB.toKBs(1024), is(StorageUnit.KB.getDefault(1024)));
    }

    @Test
    public void testKBConversions_6() {
        assertThat(StorageUnit.KB.toBytes(1), is(KB));
    }

    @Test
    public void testKBConversions_7() {
        assertThat(StorageUnit.KB.fromBytes(KB), is(1.0));
    }

    @Test
    public void testKBConversions_8() {
        assertThat(StorageUnit.KB.toKBs(10), is(10.0));
    }

    @Test
    public void testKBConversions_9() {
        assertThat(StorageUnit.KB.toMBs(3.0 * 1024.0), is(3.0));
    }

    @Test
    public void testKBConversions_10() {
        assertThat(StorageUnit.KB.toGBs(1073741824), is(1024.0));
    }

    @Test
    public void testKBConversions_11() {
        assertThat(StorageUnit.KB.toTBs(1073741824), is(1.0));
    }

    @Test
    public void testKBConversions_12() {
        assertThat(StorageUnit.KB.toPBs(1.0995116e+12), is(1.0));
    }

    @Test
    public void testKBConversions_13() {
        assertThat(StorageUnit.KB.toEBs(1.1258999e+15), is(1.0));
    }

    @Test
    public void testMBConversions_1() {
        assertThat(StorageUnit.MB.getShortName(), is("mb"));
    }

    @Test
    public void testMBConversions_2() {
        assertThat(StorageUnit.MB.getSuffixChar(), is("m"));
    }

    @Test
    public void testMBConversions_3() {
        assertThat(StorageUnit.MB.getLongName(), is("megabytes"));
    }

    @Test
    public void testMBConversions_4() {
        assertThat(StorageUnit.MB.toString(), is("megabytes"));
    }

    @Test
    public void testMBConversions_5() {
        assertThat(StorageUnit.MB.toMBs(1024), is(StorageUnit.MB.getDefault(1024)));
    }

    @Test
    public void testMBConversions_6() {
        assertThat(StorageUnit.MB.toBytes(1), is(MB));
    }

    @Test
    public void testMBConversions_7() {
        assertThat(StorageUnit.MB.fromBytes(MB), is(1.0));
    }

    @Test
    public void testMBConversions_8() {
        assertThat(StorageUnit.MB.toKBs(1), is(1024.0));
    }

    @Test
    public void testMBConversions_9() {
        assertThat(StorageUnit.MB.toMBs(10), is(10.0));
    }

    @Test
    public void testMBConversions_10() {
        assertThat(StorageUnit.MB.toGBs(44040192), is(43008.0));
    }

    @Test
    public void testMBConversions_11() {
        assertThat(StorageUnit.MB.toTBs(1073741824), is(1024.0));
    }

    @Test
    public void testMBConversions_12() {
        assertThat(StorageUnit.MB.toPBs(1073741824), is(1.0));
    }

    @Test
    public void testMBConversions_13() {
        assertThat(StorageUnit.MB.toEBs(1 * (EB / MB)), is(1.0));
    }

    @Test
    public void testGBConversions_1() {
        assertThat(StorageUnit.GB.getShortName(), is("gb"));
    }

    @Test
    public void testGBConversions_2() {
        assertThat(StorageUnit.GB.getSuffixChar(), is("g"));
    }

    @Test
    public void testGBConversions_3() {
        assertThat(StorageUnit.GB.getLongName(), is("gigabytes"));
    }

    @Test
    public void testGBConversions_4() {
        assertThat(StorageUnit.GB.toString(), is("gigabytes"));
    }

    @Test
    public void testGBConversions_5() {
        assertThat(StorageUnit.GB.toGBs(1024), is(StorageUnit.GB.getDefault(1024)));
    }

    @Test
    public void testGBConversions_6() {
        assertThat(StorageUnit.GB.toBytes(1), is(GB));
    }

    @Test
    public void testGBConversions_7() {
        assertThat(StorageUnit.GB.fromBytes(GB), is(1.0));
    }

    @Test
    public void testGBConversions_8() {
        assertThat(StorageUnit.GB.toKBs(1), is(1024.0 * 1024));
    }

    @Test
    public void testGBConversions_9() {
        assertThat(StorageUnit.GB.toMBs(10), is(10.0 * 1024));
    }

    @Test
    public void testGBConversions_10() {
        assertThat(StorageUnit.GB.toGBs(44040192.0), is(44040192.0));
    }

    @Test
    public void testGBConversions_11() {
        assertThat(StorageUnit.GB.toTBs(1073741824), is(1048576.0));
    }

    @Test
    public void testGBConversions_12() {
        assertThat(StorageUnit.GB.toPBs(1.07375e+9), is(1024.0078));
    }

    @Test
    public void testGBConversions_13() {
        assertThat(StorageUnit.GB.toEBs(1 * (EB / GB)), is(1.0));
    }

    @Test
    public void testTBConversions_1() {
        assertThat(StorageUnit.TB.getShortName(), is("tb"));
    }

    @Test
    public void testTBConversions_2() {
        assertThat(StorageUnit.TB.getSuffixChar(), is("t"));
    }

    @Test
    public void testTBConversions_3() {
        assertThat(StorageUnit.TB.getLongName(), is("terabytes"));
    }

    @Test
    public void testTBConversions_4() {
        assertThat(StorageUnit.TB.toString(), is("terabytes"));
    }

    @Test
    public void testTBConversions_5() {
        assertThat(StorageUnit.TB.toTBs(1024), is(StorageUnit.TB.getDefault(1024)));
    }

    @Test
    public void testTBConversions_6() {
        assertThat(StorageUnit.TB.toBytes(1), is(TB));
    }

    @Test
    public void testTBConversions_7() {
        assertThat(StorageUnit.TB.fromBytes(TB), is(1.0));
    }

    @Test
    public void testTBConversions_8() {
        assertThat(StorageUnit.TB.toKBs(1), is(1024.0 * 1024 * 1024));
    }

    @Test
    public void testTBConversions_9() {
        assertThat(StorageUnit.TB.toMBs(10), is(10.0 * 1024 * 1024));
    }

    @Test
    public void testTBConversions_10() {
        assertThat(StorageUnit.TB.toGBs(44040192.0), is(45097156608.0));
    }

    @Test
    public void testTBConversions_11() {
        assertThat(StorageUnit.TB.toTBs(1073741824.0), is(1073741824.0));
    }

    @Test
    public void testTBConversions_12() {
        assertThat(StorageUnit.TB.toPBs(1024), is(1.0));
    }

    @Test
    public void testTBConversions_13() {
        assertThat(StorageUnit.TB.toEBs(1 * (EB / TB)), is(1.0));
    }

    @Test
    public void testPBConversions_1() {
        assertThat(StorageUnit.PB.getShortName(), is("pb"));
    }

    @Test
    public void testPBConversions_2() {
        assertThat(StorageUnit.PB.getSuffixChar(), is("p"));
    }

    @Test
    public void testPBConversions_3() {
        assertThat(StorageUnit.PB.getLongName(), is("petabytes"));
    }

    @Test
    public void testPBConversions_4() {
        assertThat(StorageUnit.PB.toString(), is("petabytes"));
    }

    @Test
    public void testPBConversions_5() {
        assertThat(StorageUnit.PB.toPBs(1024), is(StorageUnit.PB.getDefault(1024)));
    }

    @Test
    public void testPBConversions_6() {
        assertThat(StorageUnit.PB.toBytes(1), is(PB));
    }

    @Test
    public void testPBConversions_7() {
        assertThat(StorageUnit.PB.fromBytes(PB), is(1.0));
    }

    @Test
    public void testPBConversions_8() {
        assertThat(StorageUnit.PB.toKBs(1), is(PB / KB));
    }

    @Test
    public void testPBConversions_9() {
        assertThat(StorageUnit.PB.toMBs(10), is(10.0 * (PB / MB)));
    }

    @Test
    public void testPBConversions_10() {
        assertThat(StorageUnit.PB.toGBs(44040192.0), is(44040192.0 * PB / GB));
    }

    @Test
    public void testPBConversions_11() {
        assertThat(StorageUnit.PB.toTBs(1073741824.0), is(1073741824.0 * (PB / TB)));
    }

    @Test
    public void testPBConversions_12() {
        assertThat(StorageUnit.PB.toPBs(1024.0), is(1024.0));
    }

    @Test
    public void testPBConversions_13() {
        assertThat(StorageUnit.PB.toEBs(1024.0), is(1.0));
    }

    @Test
    public void testEBConversions_1() {
        assertThat(StorageUnit.EB.getShortName(), is("eb"));
    }

    @Test
    public void testEBConversions_2() {
        assertThat(StorageUnit.EB.getSuffixChar(), is("e"));
    }

    @Test
    public void testEBConversions_3() {
        assertThat(StorageUnit.EB.getLongName(), is("exabytes"));
    }

    @Test
    public void testEBConversions_4() {
        assertThat(StorageUnit.EB.toString(), is("exabytes"));
    }

    @Test
    public void testEBConversions_5() {
        assertThat(StorageUnit.EB.toEBs(1024), is(StorageUnit.EB.getDefault(1024)));
    }

    @Test
    public void testEBConversions_6() {
        assertThat(StorageUnit.EB.toBytes(1), is(EB));
    }

    @Test
    public void testEBConversions_7() {
        assertThat(StorageUnit.EB.fromBytes(EB), is(1.0));
    }

    @Test
    public void testEBConversions_8() {
        assertThat(StorageUnit.EB.toKBs(1), is(EB / KB));
    }

    @Test
    public void testEBConversions_9() {
        assertThat(StorageUnit.EB.toMBs(10), is(10.0 * (EB / MB)));
    }

    @Test
    public void testEBConversions_10() {
        assertThat(StorageUnit.EB.toGBs(44040192.0), is(44040192.0 * EB / GB));
    }

    @Test
    public void testEBConversions_11() {
        assertThat(StorageUnit.EB.toTBs(1073741824.0), is(1073741824.0 * (EB / TB)));
    }

    @Test
    public void testEBConversions_12() {
        assertThat(StorageUnit.EB.toPBs(1.0), is(1024.0));
    }

    @Test
    public void testEBConversions_13() {
        assertThat(StorageUnit.EB.toEBs(42.0), is(42.0));
    }
}
