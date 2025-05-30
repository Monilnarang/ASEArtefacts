package org.apache.druid.indexing.kinesis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import org.apache.druid.guice.StartupInjectorBuilder;
import org.apache.druid.indexing.overlord.DataSourceMetadata;
import org.apache.druid.indexing.seekablestream.SeekableStreamEndSequenceNumbers;
import org.apache.druid.indexing.seekablestream.SeekableStreamStartSequenceNumbers;
import org.apache.druid.initialization.CoreInjectorBuilder;
import org.apache.druid.initialization.DruidModule;
import org.junit.Assert;
import org.junit.Test;
import java.util.Map;
import java.util.Set;

public class KinesisDataSourceMetadataTest_Purified {

    private static final KinesisDataSourceMetadata START0 = simpleStartMetadata(ImmutableMap.of());

    private static final KinesisDataSourceMetadata START1 = simpleStartMetadata(ImmutableMap.of("0", "2L", "1", "3L"));

    private static final KinesisDataSourceMetadata START2 = simpleStartMetadata(ImmutableMap.of("0", "2L", "1", "4L", "2", "5L"));

    private static final KinesisDataSourceMetadata START3 = simpleStartMetadata(ImmutableMap.of("0", "2L", "2", "5L"));

    private static final KinesisDataSourceMetadata START4 = startMetadata(ImmutableMap.of("0", "2L", "2", "5L"), ImmutableSet.of());

    private static final KinesisDataSourceMetadata START5 = startMetadata(ImmutableMap.of("0", "2L", "1", "4L", "2", "5L"), ImmutableSet.of("0", "1"));

    private static final KinesisDataSourceMetadata END0 = endMetadata(ImmutableMap.of());

    private static final KinesisDataSourceMetadata END1 = endMetadata(ImmutableMap.of("0", "2L", "2", "5L"));

    private static final KinesisDataSourceMetadata END2 = endMetadata(ImmutableMap.of("0", "2L", "1", "4L"));

    private static KinesisDataSourceMetadata simpleStartMetadata(Map<String, String> sequences) {
        return startMetadata(sequences, sequences.keySet());
    }

    private static KinesisDataSourceMetadata startMetadata(Map<String, String> sequences, Set<String> exclusivePartitions) {
        return new KinesisDataSourceMetadata(new SeekableStreamStartSequenceNumbers<>("foo", sequences, exclusivePartitions));
    }

    private static KinesisDataSourceMetadata endMetadata(Map<String, String> sequences) {
        return new KinesisDataSourceMetadata(new SeekableStreamEndSequenceNumbers<>("foo", sequences));
    }

    private static ObjectMapper createObjectMapper() {
        DruidModule module = new KinesisIndexingServiceModule();
        final Injector injector = new CoreInjectorBuilder(new StartupInjectorBuilder().build()).addModule(binder -> {
            binder.bindConstant().annotatedWith(Names.named("serviceName")).to("test");
            binder.bindConstant().annotatedWith(Names.named("servicePort")).to(8000);
            binder.bindConstant().annotatedWith(Names.named("tlsServicePort")).to(9000);
        }).build();
        ObjectMapper objectMapper = injector.getInstance(ObjectMapper.class);
        module.getJacksonModules().forEach(objectMapper::registerModule);
        return objectMapper;
    }

    @Test
    public void testMatches_1() {
        Assert.assertTrue(START0.matches(START0));
    }

    @Test
    public void testMatches_2() {
        Assert.assertTrue(START0.matches(START1));
    }

    @Test
    public void testMatches_3() {
        Assert.assertTrue(START0.matches(START2));
    }

    @Test
    public void testMatches_4() {
        Assert.assertTrue(START0.matches(START3));
    }

    @Test
    public void testMatches_5() {
        Assert.assertTrue(START0.matches(START4));
    }

    @Test
    public void testMatches_6() {
        Assert.assertTrue(START0.matches(START5));
    }

    @Test
    public void testMatches_7() {
        Assert.assertTrue(START1.matches(START0));
    }

    @Test
    public void testMatches_8() {
        Assert.assertTrue(START1.matches(START1));
    }

    @Test
    public void testMatches_9() {
        Assert.assertFalse(START1.matches(START2));
    }

    @Test
    public void testMatches_10() {
        Assert.assertTrue(START1.matches(START3));
    }

    @Test
    public void testMatches_11() {
        Assert.assertFalse(START1.matches(START4));
    }

    @Test
    public void testMatches_12() {
        Assert.assertFalse(START1.matches(START5));
    }

    @Test
    public void testMatches_13() {
        Assert.assertTrue(START2.matches(START0));
    }

    @Test
    public void testMatches_14() {
        Assert.assertFalse(START2.matches(START1));
    }

    @Test
    public void testMatches_15() {
        Assert.assertTrue(START2.matches(START2));
    }

    @Test
    public void testMatches_16() {
        Assert.assertTrue(START2.matches(START3));
    }

    @Test
    public void testMatches_17() {
        Assert.assertFalse(START2.matches(START4));
    }

    @Test
    public void testMatches_18() {
        Assert.assertFalse(START2.matches(START5));
    }

    @Test
    public void testMatches_19() {
        Assert.assertTrue(START3.matches(START0));
    }

    @Test
    public void testMatches_20() {
        Assert.assertTrue(START3.matches(START1));
    }

    @Test
    public void testMatches_21() {
        Assert.assertTrue(START3.matches(START2));
    }

    @Test
    public void testMatches_22() {
        Assert.assertTrue(START3.matches(START3));
    }

    @Test
    public void testMatches_23() {
        Assert.assertFalse(START3.matches(START4));
    }

    @Test
    public void testMatches_24() {
        Assert.assertFalse(START3.matches(START5));
    }

    @Test
    public void testMatches_25() {
        Assert.assertTrue(START4.matches(START0));
    }

    @Test
    public void testMatches_26() {
        Assert.assertFalse(START4.matches(START1));
    }

    @Test
    public void testMatches_27() {
        Assert.assertFalse(START4.matches(START2));
    }

    @Test
    public void testMatches_28() {
        Assert.assertFalse(START4.matches(START3));
    }

    @Test
    public void testMatches_29() {
        Assert.assertTrue(START4.matches(START4));
    }

    @Test
    public void testMatches_30() {
        Assert.assertFalse(START4.matches(START5));
    }

    @Test
    public void testMatches_31() {
        Assert.assertTrue(START5.matches(START0));
    }

    @Test
    public void testMatches_32() {
        Assert.assertFalse(START5.matches(START1));
    }

    @Test
    public void testMatches_33() {
        Assert.assertFalse(START5.matches(START2));
    }

    @Test
    public void testMatches_34() {
        Assert.assertFalse(START5.matches(START3));
    }

    @Test
    public void testMatches_35() {
        Assert.assertFalse(START5.matches(START4));
    }

    @Test
    public void testMatches_36() {
        Assert.assertTrue(START5.matches(START5));
    }

    @Test
    public void testMatches_37() {
        Assert.assertTrue(END0.matches(END0));
    }

    @Test
    public void testMatches_38() {
        Assert.assertTrue(END0.matches(END1));
    }

    @Test
    public void testMatches_39() {
        Assert.assertTrue(END0.matches(END2));
    }

    @Test
    public void testMatches_40() {
        Assert.assertTrue(END1.matches(END0));
    }

    @Test
    public void testMatches_41() {
        Assert.assertTrue(END1.matches(END1));
    }

    @Test
    public void testMatches_42() {
        Assert.assertTrue(END1.matches(END2));
    }

    @Test
    public void testMatches_43() {
        Assert.assertTrue(END2.matches(END0));
    }

    @Test
    public void testMatches_44() {
        Assert.assertTrue(END2.matches(END1));
    }

    @Test
    public void testMatches_45() {
        Assert.assertTrue(END2.matches(END2));
    }

    @Test
    public void testIsValidStart_1() {
        Assert.assertTrue(START0.isValidStart());
    }

    @Test
    public void testIsValidStart_2() {
        Assert.assertTrue(START1.isValidStart());
    }

    @Test
    public void testIsValidStart_3() {
        Assert.assertTrue(START2.isValidStart());
    }

    @Test
    public void testIsValidStart_4() {
        Assert.assertTrue(START3.isValidStart());
    }

    @Test
    public void testIsValidStart_5() {
        Assert.assertTrue(START4.isValidStart());
    }

    @Test
    public void testIsValidStart_6() {
        Assert.assertTrue(START5.isValidStart());
    }

    @Test
    public void testPlus_1() {
        Assert.assertEquals(simpleStartMetadata(ImmutableMap.of("0", "2L", "1", "3L", "2", "5L")), START1.plus(START3));
    }

    @Test
    public void testPlus_2() {
        Assert.assertEquals(simpleStartMetadata(ImmutableMap.of("0", "2L", "1", "4L", "2", "5L")), START0.plus(START2));
    }

    @Test
    public void testPlus_3() {
        Assert.assertEquals(simpleStartMetadata(ImmutableMap.of("0", "2L", "1", "4L", "2", "5L")), START1.plus(START2));
    }

    @Test
    public void testPlus_4() {
        Assert.assertEquals(simpleStartMetadata(ImmutableMap.of("0", "2L", "1", "3L", "2", "5L")), START2.plus(START1));
    }

    @Test
    public void testPlus_5() {
        Assert.assertEquals(simpleStartMetadata(ImmutableMap.of("0", "2L", "1", "4L", "2", "5L")), START2.plus(START2));
    }

    @Test
    public void testPlus_6() {
        Assert.assertEquals(startMetadata(ImmutableMap.of("0", "2L", "1", "4L", "2", "5L"), ImmutableSet.of("1")), START2.plus(START4));
    }

    @Test
    public void testPlus_7() {
        Assert.assertEquals(startMetadata(ImmutableMap.of("0", "2L", "1", "4L", "2", "5L"), ImmutableSet.of("0", "1")), START2.plus(START5));
    }

    @Test
    public void testPlus_8() {
        Assert.assertEquals(endMetadata(ImmutableMap.of("0", "2L", "2", "5L")), END0.plus(END1));
    }

    @Test
    public void testPlus_9() {
        Assert.assertEquals(endMetadata(ImmutableMap.of("0", "2L", "1", "4L", "2", "5L")), END1.plus(END2));
    }

    @Test
    public void testMinus_1() {
        Assert.assertEquals(simpleStartMetadata(ImmutableMap.of("1", "3L")), START1.minus(START3));
    }

    @Test
    public void testMinus_2() {
        Assert.assertEquals(simpleStartMetadata(ImmutableMap.of()), START0.minus(START2));
    }

    @Test
    public void testMinus_3() {
        Assert.assertEquals(simpleStartMetadata(ImmutableMap.of()), START1.minus(START2));
    }

    @Test
    public void testMinus_4() {
        Assert.assertEquals(simpleStartMetadata(ImmutableMap.of("2", "5L")), START2.minus(START1));
    }

    @Test
    public void testMinus_5() {
        Assert.assertEquals(simpleStartMetadata(ImmutableMap.of()), START2.minus(START2));
    }

    @Test
    public void testMinus_6() {
        Assert.assertEquals(startMetadata(ImmutableMap.of(), ImmutableSet.of()), START4.minus(START2));
    }

    @Test
    public void testMinus_7() {
        Assert.assertEquals(startMetadata(ImmutableMap.of("1", "4L"), ImmutableSet.of("1")), START5.minus(START4));
    }

    @Test
    public void testMinus_8() {
        Assert.assertEquals(endMetadata(ImmutableMap.of("1", "4L")), END2.minus(END1));
    }

    @Test
    public void testMinus_9() {
        Assert.assertEquals(endMetadata(ImmutableMap.of("2", "5L")), END1.minus(END2));
    }
}
