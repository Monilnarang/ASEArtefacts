package org.apache.hadoop.hdfs.web.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import org.apache.hadoop.fs.StorageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeysPublic;
import org.apache.hadoop.fs.Options;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.XAttrCodec;
import org.apache.hadoop.fs.XAttrSetFlag;
import org.apache.hadoop.fs.permission.AclEntry;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.util.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class TestParam_Purified {

    public static final Logger LOG = LoggerFactory.getLogger(TestParam.class);

    final Configuration conf = new Configuration();

    @Test
    public void testSnapshotNameParam_1() {
        final OldSnapshotNameParam s1 = new OldSnapshotNameParam("s1");
        Assert.assertEquals("s1", s1.getValue());
    }

    @Test
    public void testSnapshotNameParam_2() {
        final SnapshotNameParam s2 = new SnapshotNameParam("s2");
        Assert.assertEquals("s2", s2.getValue());
    }
}
