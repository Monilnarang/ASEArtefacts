package org.apache.rocketmq.common.utils;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NameServerAddressUtilsTest_Purified {

    private static String endpoint1 = "http://127.0.0.1:9876";

    private static String endpoint2 = "127.0.0.1:9876";

    private static String endpoint3 = "http://MQ_INST_123456789_BXXUzaee.xxx:80";

    private static String endpoint4 = "MQ_INST_123456789_BXXUzaee.xxx:80";

    @Test
    public void testValidateInstanceEndpoint_1() {
        assertThat(NameServerAddressUtils.validateInstanceEndpoint(endpoint1)).isEqualTo(false);
    }

    @Test
    public void testValidateInstanceEndpoint_2() {
        assertThat(NameServerAddressUtils.validateInstanceEndpoint(endpoint2)).isEqualTo(false);
    }

    @Test
    public void testValidateInstanceEndpoint_3() {
        assertThat(NameServerAddressUtils.validateInstanceEndpoint(endpoint3)).isEqualTo(true);
    }

    @Test
    public void testValidateInstanceEndpoint_4() {
        assertThat(NameServerAddressUtils.validateInstanceEndpoint(endpoint4)).isEqualTo(true);
    }

    @Test
    public void testParseInstanceIdFromEndpoint_1() {
        assertThat(NameServerAddressUtils.parseInstanceIdFromEndpoint(endpoint3)).isEqualTo("MQ_INST_123456789_BXXUzaee");
    }

    @Test
    public void testParseInstanceIdFromEndpoint_2() {
        assertThat(NameServerAddressUtils.parseInstanceIdFromEndpoint(endpoint4)).isEqualTo("MQ_INST_123456789_BXXUzaee");
    }

    @Test
    public void testGetNameSrvAddrFromNamesrvEndpoint_1() {
        assertThat(NameServerAddressUtils.getNameSrvAddrFromNamesrvEndpoint(endpoint1)).isEqualTo("127.0.0.1:9876");
    }

    @Test
    public void testGetNameSrvAddrFromNamesrvEndpoint_2() {
        assertThat(NameServerAddressUtils.getNameSrvAddrFromNamesrvEndpoint(endpoint2)).isEqualTo("127.0.0.1:9876");
    }

    @Test
    public void testGetNameSrvAddrFromNamesrvEndpoint_3() {
        assertThat(NameServerAddressUtils.getNameSrvAddrFromNamesrvEndpoint(endpoint3)).isEqualTo("MQ_INST_123456789_BXXUzaee.xxx:80");
    }

    @Test
    public void testGetNameSrvAddrFromNamesrvEndpoint_4() {
        assertThat(NameServerAddressUtils.getNameSrvAddrFromNamesrvEndpoint(endpoint4)).isEqualTo("MQ_INST_123456789_BXXUzaee.xxx:80");
    }
}
