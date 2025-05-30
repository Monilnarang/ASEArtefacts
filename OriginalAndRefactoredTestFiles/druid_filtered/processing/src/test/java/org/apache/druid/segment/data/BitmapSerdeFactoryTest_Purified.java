package org.apache.druid.segment.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.druid.collections.bitmap.ConciseBitmapFactory;
import org.apache.druid.collections.bitmap.RoaringBitmapFactory;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.junit.Assert;
import org.junit.Test;

public class BitmapSerdeFactoryTest_Purified {

    @Test
    public void testForBitmapFactory_1() {
        Assert.assertTrue(BitmapSerde.forBitmapFactory(new RoaringBitmapFactory()) instanceof BitmapSerde.DefaultBitmapSerdeFactory);
    }

    @Test
    public void testForBitmapFactory_2() {
        Assert.assertTrue(BitmapSerde.forBitmapFactory(new ConciseBitmapFactory()) instanceof ConciseBitmapSerdeFactory);
    }
}
