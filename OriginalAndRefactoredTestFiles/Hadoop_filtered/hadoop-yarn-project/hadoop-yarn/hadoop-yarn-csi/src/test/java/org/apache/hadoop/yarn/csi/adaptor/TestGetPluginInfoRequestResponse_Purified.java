package org.apache.hadoop.yarn.csi.adaptor;

import org.apache.hadoop.yarn.api.protocolrecords.GetPluginInfoResponse;
import org.apache.hadoop.yarn.api.protocolrecords.impl.pb.GetPluginInfoRequestPBImpl;
import org.apache.hadoop.yarn.api.protocolrecords.impl.pb.GetPluginInfoResponsePBImpl;
import org.apache.hadoop.yarn.proto.CsiAdaptorProtos;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestGetPluginInfoRequestResponse_Purified {

    @Test
    void testGetPluginInfoResponsePBRecord_1_testMerged_1() {
        CsiAdaptorProtos.GetPluginInfoResponse responseProto = CsiAdaptorProtos.GetPluginInfoResponse.newBuilder().setName("test-driver").setVendorVersion("1.0.1").build();
        GetPluginInfoResponsePBImpl pbImpl = new GetPluginInfoResponsePBImpl(responseProto);
        assertEquals("test-driver", pbImpl.getDriverName());
        assertEquals("1.0.1", pbImpl.getVersion());
        assertEquals(responseProto, pbImpl.getProto());
    }

    @Test
    void testGetPluginInfoResponsePBRecord_4_testMerged_2() {
        GetPluginInfoResponse pbImpl2 = GetPluginInfoResponsePBImpl.newInstance("test-driver", "1.0.1");
        assertEquals("test-driver", pbImpl2.getDriverName());
        assertEquals("1.0.1", pbImpl2.getVersion());
        CsiAdaptorProtos.GetPluginInfoResponse proto = ((GetPluginInfoResponsePBImpl) pbImpl2).getProto();
        assertEquals("test-driver", proto.getName());
        assertEquals("1.0.1", proto.getVendorVersion());
    }
}
