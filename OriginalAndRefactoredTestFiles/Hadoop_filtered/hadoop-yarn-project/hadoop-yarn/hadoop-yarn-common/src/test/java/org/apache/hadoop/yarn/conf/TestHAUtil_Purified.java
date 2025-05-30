package org.apache.hadoop.yarn.conf;

import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.StringUtils;
import org.apache.hadoop.yarn.exceptions.YarnRuntimeException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TestHAUtil_Purified {

    private Configuration conf;

    private static final String RM1_ADDRESS_UNTRIMMED = "  \t\t\n 1.2.3.4:8021  \n\t ";

    private static final String RM1_ADDRESS = RM1_ADDRESS_UNTRIMMED.trim();

    private static final String RM2_ADDRESS = "localhost:8022";

    private static final String RM3_ADDRESS = "localhost:8033";

    private static final String RM1_NODE_ID_UNTRIMMED = "rm1 ";

    private static final String RM1_NODE_ID = RM1_NODE_ID_UNTRIMMED.trim();

    private static final String RM2_NODE_ID = "rm2";

    private static final String RM3_NODE_ID = "rm3";

    private static final String RM_INVALID_NODE_ID = ".rm";

    private static final String RM_NODE_IDS_UNTRIMMED = RM1_NODE_ID_UNTRIMMED + "," + RM2_NODE_ID;

    private static final String RM_NODE_IDS = RM1_NODE_ID + "," + RM2_NODE_ID;

    @BeforeEach
    public void setUp() {
        conf = new Configuration();
        conf.set(YarnConfiguration.RM_HA_IDS, RM_NODE_IDS_UNTRIMMED);
        conf.set(YarnConfiguration.RM_HA_ID, RM1_NODE_ID_UNTRIMMED);
        for (String confKey : YarnConfiguration.getServiceAddressConfKeys(conf)) {
            conf.set(HAUtil.addSuffix(confKey, RM1_NODE_ID), RM1_ADDRESS_UNTRIMMED);
            conf.set(HAUtil.addSuffix(confKey, RM2_NODE_ID), RM2_ADDRESS);
        }
    }

    @Test
    void testGetConfKeyForRMInstance_1() {
        assertTrue(HAUtil.getConfKeyForRMInstance(YarnConfiguration.RM_ADDRESS, conf).contains(HAUtil.getRMHAId(conf)), "RM instance id is not suffixed");
    }

    @Test
    void testGetConfKeyForRMInstance_2() {
        assertFalse(HAUtil.getConfKeyForRMInstance(YarnConfiguration.NM_ADDRESS, conf).contains(HAUtil.getRMHAId(conf)), "RM instance id is suffixed");
    }
}
