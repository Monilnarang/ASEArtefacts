package org.apache.druid.guice;

import com.google.common.collect.ImmutableList;
import com.google.inject.Injector;
import org.apache.druid.data.input.impl.DimensionSchema;
import org.apache.druid.segment.DefaultColumnFormatConfig;
import org.apache.druid.segment.DimensionHandlerProvider;
import org.apache.druid.segment.DimensionHandlerUtils;
import org.apache.druid.segment.NestedCommonFormatColumnHandler;
import org.apache.druid.segment.nested.NestedDataComplexTypeSerde;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import javax.annotation.Nullable;
import java.util.Properties;

public class BuiltInTypesModuleTest_Purified {

    @Nullable
    private static DimensionHandlerProvider DEFAULT_HANDLER_PROVIDER;

    @BeforeClass
    public static void setup() {
        DEFAULT_HANDLER_PROVIDER = DimensionHandlerUtils.DIMENSION_HANDLER_PROVIDERS.get(NestedDataComplexTypeSerde.TYPE_NAME);
        DimensionHandlerUtils.DIMENSION_HANDLER_PROVIDERS.remove(NestedDataComplexTypeSerde.TYPE_NAME);
    }

    @AfterClass
    public static void teardown() {
        if (DEFAULT_HANDLER_PROVIDER == null) {
            DimensionHandlerUtils.DIMENSION_HANDLER_PROVIDERS.remove(NestedDataComplexTypeSerde.TYPE_NAME);
        } else {
            DimensionHandlerUtils.DIMENSION_HANDLER_PROVIDERS.put(NestedDataComplexTypeSerde.TYPE_NAME, DEFAULT_HANDLER_PROVIDER);
        }
    }

    private Injector makeInjector(Properties props) {
        StartupInjectorBuilder bob = new StartupInjectorBuilder().forTests().withProperties(props);
        bob.addAll(ImmutableList.of(binder -> {
            JsonConfigProvider.bind(binder, "druid.indexing.formats", DefaultColumnFormatConfig.class);
        }, new BuiltInTypesModule()));
        return bob.build();
    }

    @Test
    public void testDefaults_1() {
        DimensionHandlerUtils.DIMENSION_HANDLER_PROVIDERS.remove(NestedDataComplexTypeSerde.TYPE_NAME);
        DimensionHandlerProvider provider = DimensionHandlerUtils.DIMENSION_HANDLER_PROVIDERS.get(NestedDataComplexTypeSerde.TYPE_NAME);
        Assert.assertTrue(provider.get("test") instanceof NestedCommonFormatColumnHandler);
    }

    @Test
    public void testDefaults_2() {
        Assert.assertEquals(DimensionSchema.MultiValueHandling.SORTED_ARRAY, BuiltInTypesModule.getStringMultiValueHandlingMode());
    }
}
