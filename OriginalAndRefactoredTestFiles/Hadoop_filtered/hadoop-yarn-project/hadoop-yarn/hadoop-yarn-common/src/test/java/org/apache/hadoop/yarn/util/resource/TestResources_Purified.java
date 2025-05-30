package org.apache.hadoop.yarn.util.resource;

import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.api.records.ResourceInformation;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import static org.apache.hadoop.yarn.util.resource.Resources.add;
import static org.apache.hadoop.yarn.util.resource.Resources.componentwiseMax;
import static org.apache.hadoop.yarn.util.resource.Resources.componentwiseMin;
import static org.apache.hadoop.yarn.util.resource.Resources.fitsIn;
import static org.apache.hadoop.yarn.util.resource.Resources.multiply;
import static org.apache.hadoop.yarn.util.resource.Resources.multiplyAndAddTo;
import static org.apache.hadoop.yarn.util.resource.Resources.multiplyAndRoundDown;
import static org.apache.hadoop.yarn.util.resource.Resources.multiplyAndRoundUp;
import static org.apache.hadoop.yarn.util.resource.Resources.subtract;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestResources_Purified {

    private static final String INVALID_RESOURCE_MSG = "Invalid resource value";

    static class ExtendedResources extends Resources {

        public static Resource unbounded() {
            return new FixedValueResource("UNBOUNDED", Long.MAX_VALUE);
        }

        public static Resource none() {
            return new FixedValueResource("NONE", 0L);
        }
    }

    private static final String EXTRA_RESOURCE_TYPE = "resource2";

    private String resourceTypesFile;

    private void setupExtraResourceType() throws Exception {
        Configuration conf = new YarnConfiguration();
        resourceTypesFile = TestResourceUtils.setupResourceTypes(conf, "resource-types-3.xml");
    }

    private void unsetExtraResourceType() {
        deleteResourceTypesFile();
        ResourceUtils.resetResourceTypes();
    }

    private void deleteResourceTypesFile() {
        if (resourceTypesFile != null && !resourceTypesFile.isEmpty()) {
            File resourceFile = new File(resourceTypesFile);
            resourceFile.delete();
        }
    }

    @BeforeEach
    public void setup() throws Exception {
        setupExtraResourceType();
    }

    @AfterEach
    public void teardown() {
        deleteResourceTypesFile();
    }

    public Resource createResource(long memory, int vCores) {
        return Resource.newInstance(memory, vCores);
    }

    public Resource createResource(long memory, int vCores, long resource2) {
        Resource ret = Resource.newInstance(memory, vCores);
        ret.setResourceInformation(EXTRA_RESOURCE_TYPE, ResourceInformation.newInstance(EXTRA_RESOURCE_TYPE, resource2));
        return ret;
    }

    @Test
    @Timeout(10000)
    void testCompareToWithNoneResource_1() {
        assertTrue(Resources.none().compareTo(createResource(0, 0)) == 0);
    }

    @Test
    @Timeout(10000)
    void testCompareToWithNoneResource_2() {
        assertTrue(Resources.none().compareTo(createResource(1, 0)) < 0);
    }

    @Test
    @Timeout(10000)
    void testCompareToWithNoneResource_3() {
        assertTrue(Resources.none().compareTo(createResource(0, 1)) < 0);
    }

    @Test
    @Timeout(10000)
    void testCompareToWithNoneResource_4() {
        assertTrue(Resources.none().compareTo(createResource(0, 0, 0)) == 0);
    }

    @Test
    @Timeout(10000)
    void testCompareToWithNoneResource_5() {
        assertTrue(Resources.none().compareTo(createResource(1, 0, 0)) < 0);
    }

    @Test
    @Timeout(10000)
    void testCompareToWithNoneResource_6() {
        assertTrue(Resources.none().compareTo(createResource(0, 1, 0)) < 0);
    }

    @Test
    @Timeout(10000)
    void testCompareToWithNoneResource_7() {
        assertTrue(Resources.none().compareTo(createResource(0, 0, 1)) < 0);
    }

    @Test
    @Timeout(1000)
    void testFitsIn_1() {
        assertTrue(fitsIn(createResource(1, 1), createResource(2, 2)));
    }

    @Test
    @Timeout(1000)
    void testFitsIn_2() {
        assertTrue(fitsIn(createResource(2, 2), createResource(2, 2)));
    }

    @Test
    @Timeout(1000)
    void testFitsIn_3() {
        assertFalse(fitsIn(createResource(2, 2), createResource(1, 1)));
    }

    @Test
    @Timeout(1000)
    void testFitsIn_4() {
        assertFalse(fitsIn(createResource(1, 2), createResource(2, 1)));
    }

    @Test
    @Timeout(1000)
    void testFitsIn_5() {
        assertFalse(fitsIn(createResource(2, 1), createResource(1, 2)));
    }

    @Test
    @Timeout(1000)
    void testFitsIn_6() {
        assertTrue(fitsIn(createResource(1, 1, 1), createResource(2, 2, 2)));
    }

    @Test
    @Timeout(1000)
    void testFitsIn_7() {
        assertTrue(fitsIn(createResource(1, 1, 0), createResource(2, 2, 0)));
    }

    @Test
    @Timeout(1000)
    void testFitsIn_8() {
        assertTrue(fitsIn(createResource(1, 1, 1), createResource(2, 2, 2)));
    }

    @Test
    @Timeout(1000)
    void testComponentwiseMin_1() {
        assertEquals(createResource(1, 1), componentwiseMin(createResource(1, 1), createResource(2, 2)));
    }

    @Test
    @Timeout(1000)
    void testComponentwiseMin_2() {
        assertEquals(createResource(1, 1), componentwiseMin(createResource(2, 2), createResource(1, 1)));
    }

    @Test
    @Timeout(1000)
    void testComponentwiseMin_3() {
        assertEquals(createResource(1, 1), componentwiseMin(createResource(1, 2), createResource(2, 1)));
    }

    @Test
    @Timeout(1000)
    void testComponentwiseMin_4() {
        assertEquals(createResource(1, 1, 1), componentwiseMin(createResource(1, 1, 1), createResource(2, 2, 2)));
    }

    @Test
    @Timeout(1000)
    void testComponentwiseMin_5() {
        assertEquals(createResource(1, 1, 0), componentwiseMin(createResource(2, 2, 2), createResource(1, 1)));
    }

    @Test
    @Timeout(1000)
    void testComponentwiseMin_6() {
        assertEquals(createResource(1, 1, 2), componentwiseMin(createResource(1, 2, 2), createResource(2, 1, 3)));
    }

    @Test
    void testComponentwiseMax_1() {
        assertEquals(createResource(2, 2), componentwiseMax(createResource(1, 1), createResource(2, 2)));
    }

    @Test
    void testComponentwiseMax_2() {
        assertEquals(createResource(2, 2), componentwiseMax(createResource(2, 2), createResource(1, 1)));
    }

    @Test
    void testComponentwiseMax_3() {
        assertEquals(createResource(2, 2), componentwiseMax(createResource(1, 2), createResource(2, 1)));
    }

    @Test
    void testComponentwiseMax_4() {
        assertEquals(createResource(2, 2, 2), componentwiseMax(createResource(1, 1, 1), createResource(2, 2, 2)));
    }

    @Test
    void testComponentwiseMax_5() {
        assertEquals(createResource(2, 2, 2), componentwiseMax(createResource(2, 2, 2), createResource(1, 1)));
    }

    @Test
    void testComponentwiseMax_6() {
        assertEquals(createResource(2, 2, 3), componentwiseMax(createResource(1, 2, 2), createResource(2, 1, 3)));
    }

    @Test
    void testComponentwiseMax_7() {
        assertEquals(createResource(2, 2, 1), componentwiseMax(createResource(2, 2, 0), createResource(2, 1, 1)));
    }

    @Test
    void testAdd_1() {
        assertEquals(createResource(2, 3), add(createResource(1, 1), createResource(1, 2)));
    }

    @Test
    void testAdd_2() {
        assertEquals(createResource(3, 2), add(createResource(1, 1), createResource(2, 1)));
    }

    @Test
    void testAdd_3() {
        assertEquals(createResource(2, 2, 0), add(createResource(1, 1, 0), createResource(1, 1, 0)));
    }

    @Test
    void testAdd_4() {
        assertEquals(createResource(2, 2, 3), add(createResource(1, 1, 1), createResource(1, 1, 2)));
    }

    @Test
    void testSubtract_1() {
        assertEquals(createResource(1, 0), subtract(createResource(2, 1), createResource(1, 1)));
    }

    @Test
    void testSubtract_2() {
        assertEquals(createResource(0, 1), subtract(createResource(1, 2), createResource(1, 1)));
    }

    @Test
    void testSubtract_3() {
        assertEquals(createResource(2, 2, 0), subtract(createResource(3, 3, 0), createResource(1, 1, 0)));
    }

    @Test
    void testSubtract_4() {
        assertEquals(createResource(1, 1, 2), subtract(createResource(2, 2, 3), createResource(1, 1, 1)));
    }

    @Test
    void testClone_1() {
        assertEquals(createResource(1, 1), Resources.clone(createResource(1, 1)));
    }

    @Test
    void testClone_2() {
        assertEquals(createResource(1, 1, 0), Resources.clone(createResource(1, 1)));
    }

    @Test
    void testClone_3() {
        assertEquals(createResource(1, 1), Resources.clone(createResource(1, 1, 0)));
    }

    @Test
    void testClone_4() {
        assertEquals(createResource(1, 1, 2), Resources.clone(createResource(1, 1, 2)));
    }

    @Test
    void testMultiply_1() {
        assertEquals(createResource(4, 2), multiply(createResource(2, 1), 2));
    }

    @Test
    void testMultiply_2() {
        assertEquals(createResource(4, 2, 0), multiply(createResource(2, 1), 2));
    }

    @Test
    void testMultiply_3() {
        assertEquals(createResource(2, 4), multiply(createResource(1, 2), 2));
    }

    @Test
    void testMultiply_4() {
        assertEquals(createResource(2, 4, 0), multiply(createResource(1, 2), 2));
    }

    @Test
    void testMultiply_5() {
        assertEquals(createResource(6, 6, 0), multiply(createResource(3, 3, 0), 2));
    }

    @Test
    void testMultiply_6() {
        assertEquals(createResource(4, 4, 6), multiply(createResource(2, 2, 3), 2));
    }

    @Test
    void testMultiplyAndRoundUpCustomResources_1() {
        assertEquals(createResource(5, 2, 8), multiplyAndRoundUp(createResource(3, 1, 5), 1.5), INVALID_RESOURCE_MSG);
    }

    @Test
    void testMultiplyAndRoundUpCustomResources_2() {
        assertEquals(createResource(5, 2, 0), multiplyAndRoundUp(createResource(3, 1, 0), 1.5), INVALID_RESOURCE_MSG);
    }

    @Test
    void testMultiplyAndRoundUpCustomResources_3() {
        assertEquals(createResource(5, 5, 0), multiplyAndRoundUp(createResource(3, 3, 0), 1.5), INVALID_RESOURCE_MSG);
    }

    @Test
    void testMultiplyAndRoundUpCustomResources_4() {
        assertEquals(createResource(8, 3, 13), multiplyAndRoundUp(createResource(3, 1, 5), 2.5), INVALID_RESOURCE_MSG);
    }

    @Test
    void testMultiplyAndRoundUpCustomResources_5() {
        assertEquals(createResource(8, 3, 0), multiplyAndRoundUp(createResource(3, 1, 0), 2.5), INVALID_RESOURCE_MSG);
    }

    @Test
    void testMultiplyAndRoundUpCustomResources_6() {
        assertEquals(createResource(8, 8, 0), multiplyAndRoundUp(createResource(3, 3, 0), 2.5), INVALID_RESOURCE_MSG);
    }

    @Test
    void testMultiplyAndRoundDown_1() {
        assertEquals(createResource(4, 1), multiplyAndRoundDown(createResource(3, 1), 1.5), INVALID_RESOURCE_MSG);
    }

    @Test
    void testMultiplyAndRoundDown_2() {
        assertEquals(createResource(4, 1, 0), multiplyAndRoundDown(createResource(3, 1), 1.5), INVALID_RESOURCE_MSG);
    }

    @Test
    void testMultiplyAndRoundDown_3() {
        assertEquals(createResource(1, 4), multiplyAndRoundDown(createResource(1, 3), 1.5), INVALID_RESOURCE_MSG);
    }

    @Test
    void testMultiplyAndRoundDown_4() {
        assertEquals(createResource(1, 4, 0), multiplyAndRoundDown(createResource(1, 3), 1.5), INVALID_RESOURCE_MSG);
    }

    @Test
    void testMultiplyAndRoundDown_5() {
        assertEquals(createResource(7, 7, 0), multiplyAndRoundDown(createResource(3, 3, 0), 2.5), INVALID_RESOURCE_MSG);
    }

    @Test
    void testMultiplyAndRoundDown_6() {
        assertEquals(createResource(2, 2, 7), multiplyAndRoundDown(createResource(1, 1, 3), 2.5), INVALID_RESOURCE_MSG);
    }

    @Test
    void testMultiplyAndAddTo_1() throws Exception {
        assertEquals(createResource(6, 4), multiplyAndAddTo(createResource(3, 1), createResource(2, 2), 1.5));
    }

    @Test
    void testMultiplyAndAddTo_2() throws Exception {
        assertEquals(createResource(6, 4, 0), multiplyAndAddTo(createResource(3, 1), createResource(2, 2), 1.5));
    }

    @Test
    void testMultiplyAndAddTo_3() throws Exception {
        assertEquals(createResource(4, 7), multiplyAndAddTo(createResource(1, 1), createResource(2, 4), 1.5));
    }

    @Test
    void testMultiplyAndAddTo_4() throws Exception {
        assertEquals(createResource(4, 7, 0), multiplyAndAddTo(createResource(1, 1), createResource(2, 4), 1.5));
    }

    @Test
    void testMultiplyAndAddTo_5() throws Exception {
        assertEquals(createResource(6, 4, 0), multiplyAndAddTo(createResource(3, 1, 0), createResource(2, 2, 0), 1.5));
    }

    @Test
    void testMultiplyAndAddTo_6() throws Exception {
        assertEquals(createResource(6, 4, 6), multiplyAndAddTo(createResource(3, 1, 2), createResource(2, 2, 3), 1.5));
    }
}
