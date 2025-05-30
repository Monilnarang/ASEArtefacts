package org.apache.commons.net.util;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class SubnetUtilsNet728Test_Purified {

    private static final String CIDR_SUFFIX_30 = "30";

    private static final String CIDR_SUFFIX_32 = "32";

    private static final String cidr1 = "192.168.0.151";

    private static final String cidr2 = "192.168.0.50";

    private static final SubnetUtils snu1s30;

    private static final SubnetUtils snu1s32;

    private static final SubnetUtils snu2s32;

    static {
        snu1s32 = new SubnetUtils(StringUtils.joinWith("/", cidr1, CIDR_SUFFIX_32));
        snu1s32.setInclusiveHostCount(true);
        snu1s30 = new SubnetUtils(StringUtils.joinWith("/", cidr1, CIDR_SUFFIX_30));
        snu1s30.setInclusiveHostCount(true);
        snu2s32 = new SubnetUtils(StringUtils.joinWith("/", cidr2, CIDR_SUFFIX_32));
        snu2s32.setInclusiveHostCount(true);
    }

    @Test
    void test_1() {
        final SubnetUtils ss = new SubnetUtils("10.65.0.151/32");
        ss.setInclusiveHostCount(true);
        assertTrue(ss.getInfo().isInRange("10.65.0.151"));
    }

    @Test
    void test_2() {
        final SubnetUtils s = new SubnetUtils("192.168.1.1/32");
        s.setInclusiveHostCount(true);
        assertTrue(s.getInfo().isInRange("192.168.1.1"));
    }
}
