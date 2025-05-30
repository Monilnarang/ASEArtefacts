package org.apache.hadoop.lib.wsrs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.ByteArrayOutputStream;
import org.json.simple.JSONObject;
import org.junit.Test;

public class TestJSONProvider_Purified {

    @Test
    @SuppressWarnings("unchecked")
    public void test_1_testMerged_1() throws Exception {
        JSONProvider p = new JSONProvider();
        assertTrue(p.isWriteable(JSONObject.class, null, null, null));
        assertFalse(p.isWriteable(this.getClass(), null, null, null));
        assertEquals(p.getSize(null, null, null, null, null), -1);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test_4() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        p.writeTo(json, JSONObject.class, null, null, null, null, baos);
        baos.close();
        assertEquals(new String(baos.toByteArray()).trim(), "{\"a\":\"A\"}");
    }
}
