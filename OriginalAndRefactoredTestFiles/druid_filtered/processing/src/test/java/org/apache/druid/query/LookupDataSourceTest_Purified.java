package org.apache.druid.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.segment.TestHelper;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.Collections;

public class LookupDataSourceTest_Purified {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private final LookupDataSource lookylooDataSource = new LookupDataSource("lookyloo");

    @Test
    public void test_isCacheable_1() {
        Assert.assertFalse(lookylooDataSource.isCacheable(true));
    }

    @Test
    public void test_isCacheable_2() {
        Assert.assertFalse(lookylooDataSource.isCacheable(false));
    }
}
