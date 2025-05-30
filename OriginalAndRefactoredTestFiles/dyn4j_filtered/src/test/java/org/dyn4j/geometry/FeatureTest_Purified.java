package org.dyn4j.geometry;

import org.junit.Test;
import junit.framework.TestCase;

public class FeatureTest_Purified {

    @Test
    public void copy_1_testMerged_1() {
        PointFeature pf = new PointFeature(new Vector2(5, -4), 1);
        PointFeature cpf = pf.copy();
        TestCase.assertNotSame(pf, cpf);
        TestCase.assertNotSame(pf.point, cpf.point);
        TestCase.assertEquals(pf.index, cpf.index);
        TestCase.assertEquals(pf.point.x, cpf.point.x);
        TestCase.assertEquals(pf.point.y, cpf.point.y);
    }

    @Test
    public void copy_6_testMerged_2() {
        PointFeature pf1 = new PointFeature(new Vector2(5, -4), 1);
        PointFeature pf2 = new PointFeature(new Vector2(5, -4), 2);
        EdgeFeature ef = new EdgeFeature(pf1, pf2, pf1, new Vector2(-1, 0), 1);
        EdgeFeature cef = ef.copy();
        TestCase.assertNotSame(ef, cef);
        TestCase.assertNotSame(ef.vertex1, cef.vertex1);
        TestCase.assertNotSame(ef.vertex1.point, cef.vertex1.point);
        TestCase.assertEquals(ef.vertex1.index, cef.vertex1.index);
        TestCase.assertEquals(ef.vertex1.point.x, cef.vertex1.point.x);
        TestCase.assertEquals(ef.vertex1.point.y, cef.vertex1.point.y);
        TestCase.assertNotSame(ef.vertex2, cef.vertex2);
        TestCase.assertNotSame(ef.vertex2.point, cef.vertex2.point);
        TestCase.assertEquals(ef.vertex2.index, cef.vertex2.index);
        TestCase.assertEquals(ef.vertex2.point.x, cef.vertex2.point.x);
        TestCase.assertEquals(ef.vertex2.point.y, cef.vertex2.point.y);
        TestCase.assertNotSame(ef.max, cef.max);
        TestCase.assertNotSame(ef.max.point, cef.max.point);
        TestCase.assertEquals(ef.max.index, cef.max.index);
        TestCase.assertEquals(ef.max.point.x, cef.max.point.x);
        TestCase.assertEquals(ef.max.point.y, cef.max.point.y);
        TestCase.assertNotSame(ef.edge, cef.edge);
        TestCase.assertEquals(ef.edge.x, cef.edge.x);
        TestCase.assertEquals(ef.edge.y, cef.edge.y);
        TestCase.assertEquals(ef.index, cef.index);
    }
}
