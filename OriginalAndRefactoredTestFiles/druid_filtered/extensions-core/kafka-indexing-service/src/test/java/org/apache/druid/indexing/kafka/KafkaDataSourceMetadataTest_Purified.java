package org.apache.druid.indexing.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import org.apache.druid.data.input.kafka.KafkaTopicPartition;
import org.apache.druid.guice.StartupInjectorBuilder;
import org.apache.druid.indexing.overlord.DataSourceMetadata;
import org.apache.druid.indexing.seekablestream.SeekableStreamEndSequenceNumbers;
import org.apache.druid.indexing.seekablestream.SeekableStreamStartSequenceNumbers;
import org.apache.druid.initialization.CoreInjectorBuilder;
import org.apache.druid.initialization.DruidModule;
import org.apache.druid.utils.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class KafkaDataSourceMetadataTest_Purified {

    private static final KafkaDataSourceMetadata START0 = startMetadata("foo", ImmutableMap.of());

    private static final KafkaDataSourceMetadata START1 = startMetadata("foo", ImmutableMap.of(0, 2L, 1, 3L));

    private static final KafkaDataSourceMetadata START2 = startMetadata("foo", ImmutableMap.of(0, 2L, 1, 4L, 2, 5L));

    private static final KafkaDataSourceMetadata START3 = startMetadata("foo", ImmutableMap.of(0, 2L, 2, 5L));

    private static final KafkaDataSourceMetadata START4 = startMetadataMultiTopic("foo.*", ImmutableList.of("foo"), ImmutableMap.of());

    private static final KafkaDataSourceMetadata START5 = startMetadataMultiTopic("foo.*", ImmutableList.of("foo"), ImmutableMap.of(0, 2L, 1, 3L));

    private static final KafkaDataSourceMetadata START6 = startMetadataMultiTopic("foo.*", ImmutableList.of("foo", "foo2"), ImmutableMap.of(0, 2L, 1, 3L));

    private static final KafkaDataSourceMetadata START7 = startMetadataMultiTopic("foo.*", ImmutableList.of("foo", "foo2"), ImmutableMap.of(0, 2L, 2, 5L));

    private static final KafkaDataSourceMetadata START8 = startMetadataMultiTopic("foo2.*", ImmutableList.of("foo2"), ImmutableMap.of(0, 2L, 2, 5L));

    private static final KafkaDataSourceMetadata START9 = startMetadataMultiTopic("foo2.*", ImmutableList.of("foo2", "foo22"), ImmutableMap.of(0, 2L, 2, 5L));

    private static final KafkaDataSourceMetadata END0 = endMetadata("foo", ImmutableMap.of());

    private static final KafkaDataSourceMetadata END1 = endMetadata("foo", ImmutableMap.of(0, 2L, 2, 5L));

    private static final KafkaDataSourceMetadata END2 = endMetadata("foo", ImmutableMap.of(0, 2L, 1, 4L));

    private static final KafkaDataSourceMetadata END3 = endMetadata("foo", ImmutableMap.of(0, 2L, 1, 3L));

    private static final KafkaDataSourceMetadata END4 = endMetadataMultiTopic("foo.*", ImmutableList.of("foo"), ImmutableMap.of());

    private static final KafkaDataSourceMetadata END5 = endMetadataMultiTopic("foo.*", ImmutableList.of("foo"), ImmutableMap.of(0, 2L, 2, 5L));

    private static final KafkaDataSourceMetadata END6 = endMetadataMultiTopic("foo.*", ImmutableList.of("foo"), ImmutableMap.of(0, 2L, 1, 4L));

    private static final KafkaDataSourceMetadata END7 = endMetadataMultiTopic("foo.*", ImmutableList.of("foo", "foo2"), ImmutableMap.of(0, 2L, 2, 5L));

    private static final KafkaDataSourceMetadata END8 = endMetadataMultiTopic("foo.*", ImmutableList.of("foo", "foo2"), ImmutableMap.of(0, 2L, 1, 4L));

    private static final KafkaDataSourceMetadata END9 = endMetadataMultiTopic("foo2.*", ImmutableList.of("foo2"), ImmutableMap.of(0, 2L, 2, 5L));

    private static final KafkaDataSourceMetadata END10 = endMetadataMultiTopic("foo2.*", ImmutableList.of("foo2", "foo22"), ImmutableMap.of(0, 2L, 2, 5L));

    private static KafkaDataSourceMetadata startMetadata(Map<Integer, Long> offsets) {
        return startMetadata("foo", offsets);
    }

    private static KafkaDataSourceMetadata startMetadata(String topic, Map<Integer, Long> offsets) {
        Map<KafkaTopicPartition, Long> newOffsets = CollectionUtils.mapKeys(offsets, k -> new KafkaTopicPartition(false, topic, k));
        return new KafkaDataSourceMetadata(new SeekableStreamStartSequenceNumbers<>(topic, newOffsets, ImmutableSet.of()));
    }

    private static KafkaDataSourceMetadata startMetadataMultiTopic(String topicPattern, List<String> topics, Map<Integer, Long> offsets) {
        Assert.assertFalse(topics.isEmpty());
        Pattern pattern = Pattern.compile(topicPattern);
        Assert.assertTrue(topics.stream().allMatch(t -> pattern.matcher(t).matches()));
        Map<KafkaTopicPartition, Long> newOffsets = new HashMap<>();
        for (Map.Entry<Integer, Long> e : offsets.entrySet()) {
            for (String topic : topics) {
                newOffsets.put(new KafkaTopicPartition(true, topic, e.getKey()), e.getValue());
            }
        }
        return new KafkaDataSourceMetadata(new SeekableStreamStartSequenceNumbers<>(topicPattern, newOffsets, ImmutableSet.of()));
    }

    private static KafkaDataSourceMetadata endMetadata(Map<Integer, Long> offsets) {
        return endMetadata("foo", offsets);
    }

    private static KafkaDataSourceMetadata endMetadata(String topic, Map<Integer, Long> offsets) {
        Map<KafkaTopicPartition, Long> newOffsets = CollectionUtils.mapKeys(offsets, k -> new KafkaTopicPartition(false, "foo", k));
        return new KafkaDataSourceMetadata(new SeekableStreamEndSequenceNumbers<>(topic, newOffsets));
    }

    private static KafkaDataSourceMetadata endMetadataMultiTopic(String topicPattern, List<String> topics, Map<Integer, Long> offsets) {
        Assert.assertFalse(topics.isEmpty());
        Pattern pattern = Pattern.compile(topicPattern);
        Assert.assertTrue(topics.stream().allMatch(t -> pattern.matcher(t).matches()));
        Map<KafkaTopicPartition, Long> newOffsets = new HashMap<>();
        for (Map.Entry<Integer, Long> e : offsets.entrySet()) {
            for (String topic : topics) {
                newOffsets.put(new KafkaTopicPartition(true, topic, e.getKey()), e.getValue());
            }
        }
        return new KafkaDataSourceMetadata(new SeekableStreamEndSequenceNumbers<>(topicPattern, newOffsets));
    }

    private static ObjectMapper createObjectMapper() {
        DruidModule module = new KafkaIndexTaskModule();
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
        Assert.assertFalse(START0.matches(START4));
    }

    @Test
    public void testMatches_6() {
        Assert.assertTrue(START0.matches(START5));
    }

    @Test
    public void testMatches_7() {
        Assert.assertFalse(START0.matches(START6));
    }

    @Test
    public void testMatches_8() {
        Assert.assertFalse(START0.matches(START7));
    }

    @Test
    public void testMatches_9() {
        Assert.assertFalse(START0.matches(START8));
    }

    @Test
    public void testMatches_10() {
        Assert.assertFalse(START0.matches(START9));
    }

    @Test
    public void testMatches_11() {
        Assert.assertTrue(START1.matches(START0));
    }

    @Test
    public void testMatches_12() {
        Assert.assertTrue(START1.matches(START1));
    }

    @Test
    public void testMatches_13() {
        Assert.assertFalse(START1.matches(START2));
    }

    @Test
    public void testMatches_14() {
        Assert.assertTrue(START1.matches(START3));
    }

    @Test
    public void testMatches_15() {
        Assert.assertTrue(START1.matches(START5));
    }

    @Test
    public void testMatches_16() {
        Assert.assertFalse(START1.matches(START6));
    }

    @Test
    public void testMatches_17() {
        Assert.assertFalse(START1.matches(START7));
    }

    @Test
    public void testMatches_18() {
        Assert.assertFalse(START1.matches(START8));
    }

    @Test
    public void testMatches_19() {
        Assert.assertFalse(START1.matches(START9));
    }

    @Test
    public void testMatches_20() {
        Assert.assertTrue(START2.matches(START0));
    }

    @Test
    public void testMatches_21() {
        Assert.assertFalse(START2.matches(START1));
    }

    @Test
    public void testMatches_22() {
        Assert.assertTrue(START2.matches(START2));
    }

    @Test
    public void testMatches_23() {
        Assert.assertTrue(START2.matches(START3));
    }

    @Test
    public void testMatches_24() {
        Assert.assertFalse(START2.matches(START4));
    }

    @Test
    public void testMatches_25() {
        Assert.assertFalse(START2.matches(START5));
    }

    @Test
    public void testMatches_26() {
        Assert.assertFalse(START2.matches(START6));
    }

    @Test
    public void testMatches_27() {
        Assert.assertFalse(START2.matches(START7));
    }

    @Test
    public void testMatches_28() {
        Assert.assertTrue(START3.matches(START0));
    }

    @Test
    public void testMatches_29() {
        Assert.assertTrue(START3.matches(START1));
    }

    @Test
    public void testMatches_30() {
        Assert.assertTrue(START3.matches(START2));
    }

    @Test
    public void testMatches_31() {
        Assert.assertTrue(START3.matches(START3));
    }

    @Test
    public void testMatches_32() {
        Assert.assertFalse(START3.matches(START4));
    }

    @Test
    public void testMatches_33() {
        Assert.assertTrue(START3.matches(START5));
    }

    @Test
    public void testMatches_34() {
        Assert.assertFalse(START3.matches(START6));
    }

    @Test
    public void testMatches_35() {
        Assert.assertFalse(START3.matches(START7));
    }

    @Test
    public void testMatches_36() {
        Assert.assertFalse(START3.matches(START8));
    }

    @Test
    public void testMatches_37() {
        Assert.assertFalse(START3.matches(START9));
    }

    @Test
    public void testMatches_38() {
        Assert.assertFalse(START4.matches(START0));
    }

    @Test
    public void testMatches_39() {
        Assert.assertFalse(START4.matches(START1));
    }

    @Test
    public void testMatches_40() {
        Assert.assertFalse(START4.matches(START2));
    }

    @Test
    public void testMatches_41() {
        Assert.assertFalse(START4.matches(START3));
    }

    @Test
    public void testMatches_42() {
        Assert.assertTrue(START4.matches(START4));
    }

    @Test
    public void testMatches_43() {
        Assert.assertFalse(START4.matches(START5));
    }

    @Test
    public void testMatches_44() {
        Assert.assertFalse(START4.matches(START6));
    }

    @Test
    public void testMatches_45() {
        Assert.assertFalse(START4.matches(START7));
    }

    @Test
    public void testMatches_46() {
        Assert.assertFalse(START4.matches(START8));
    }

    @Test
    public void testMatches_47() {
        Assert.assertFalse(START4.matches(START9));
    }

    @Test
    public void testMatches_48() {
        Assert.assertTrue(START5.matches(START0));
    }

    @Test
    public void testMatches_49() {
        Assert.assertTrue(START5.matches(START1));
    }

    @Test
    public void testMatches_50() {
        Assert.assertFalse(START5.matches(START2));
    }

    @Test
    public void testMatches_51() {
        Assert.assertTrue(START5.matches(START3));
    }

    @Test
    public void testMatches_52() {
        Assert.assertTrue(START5.matches(START4));
    }

    @Test
    public void testMatches_53() {
        Assert.assertTrue(START5.matches(START5));
    }

    @Test
    public void testMatches_54() {
        Assert.assertTrue(START5.matches(START6));
    }

    @Test
    public void testMatches_55() {
        Assert.assertTrue(START5.matches(START7));
    }

    @Test
    public void testMatches_56() {
        Assert.assertTrue(START5.matches(START8));
    }

    @Test
    public void testMatches_57() {
        Assert.assertTrue(START5.matches(START9));
    }

    @Test
    public void testMatches_58() {
        Assert.assertTrue(START6.matches(START0));
    }

    @Test
    public void testMatches_59() {
        Assert.assertTrue(START6.matches(START1));
    }

    @Test
    public void testMatches_60() {
        Assert.assertFalse(START6.matches(START2));
    }

    @Test
    public void testMatches_61() {
        Assert.assertTrue(START6.matches(START3));
    }

    @Test
    public void testMatches_62() {
        Assert.assertTrue(START6.matches(START4));
    }

    @Test
    public void testMatches_63() {
        Assert.assertTrue(START6.matches(START5));
    }

    @Test
    public void testMatches_64() {
        Assert.assertTrue(START6.matches(START6));
    }

    @Test
    public void testMatches_65() {
        Assert.assertTrue(START6.matches(START7));
    }

    @Test
    public void testMatches_66() {
        Assert.assertTrue(START6.matches(START8));
    }

    @Test
    public void testMatches_67() {
        Assert.assertTrue(START6.matches(START9));
    }

    @Test
    public void testMatches_68() {
        Assert.assertTrue(START7.matches(START0));
    }

    @Test
    public void testMatches_69() {
        Assert.assertTrue(START7.matches(START1));
    }

    @Test
    public void testMatches_70() {
        Assert.assertTrue(START7.matches(START2));
    }

    @Test
    public void testMatches_71() {
        Assert.assertTrue(START7.matches(START3));
    }

    @Test
    public void testMatches_72() {
        Assert.assertTrue(START7.matches(START4));
    }

    @Test
    public void testMatches_73() {
        Assert.assertTrue(START7.matches(START5));
    }

    @Test
    public void testMatches_74() {
        Assert.assertTrue(START7.matches(START6));
    }

    @Test
    public void testMatches_75() {
        Assert.assertTrue(START7.matches(START7));
    }

    @Test
    public void testMatches_76() {
        Assert.assertTrue(START7.matches(START8));
    }

    @Test
    public void testMatches_77() {
        Assert.assertTrue(START7.matches(START9));
    }

    @Test
    public void testMatches_78() {
        Assert.assertTrue(START8.matches(START0));
    }

    @Test
    public void testMatches_79() {
        Assert.assertFalse(START8.matches(START1));
    }

    @Test
    public void testMatches_80() {
        Assert.assertFalse(START8.matches(START2));
    }

    @Test
    public void testMatches_81() {
        Assert.assertFalse(START8.matches(START3));
    }

    @Test
    public void testMatches_82() {
        Assert.assertTrue(START8.matches(START4));
    }

    @Test
    public void testMatches_83() {
        Assert.assertFalse(START8.matches(START5));
    }

    @Test
    public void testMatches_84() {
        Assert.assertFalse(START8.matches(START6));
    }

    @Test
    public void testMatches_85() {
        Assert.assertFalse(START8.matches(START7));
    }

    @Test
    public void testMatches_86() {
        Assert.assertTrue(START8.matches(START8));
    }

    @Test
    public void testMatches_87() {
        Assert.assertTrue(START8.matches(START9));
    }

    @Test
    public void testMatches_88() {
        Assert.assertTrue(START9.matches(START0));
    }

    @Test
    public void testMatches_89() {
        Assert.assertFalse(START9.matches(START1));
    }

    @Test
    public void testMatches_90() {
        Assert.assertFalse(START9.matches(START2));
    }

    @Test
    public void testMatches_91() {
        Assert.assertFalse(START9.matches(START3));
    }

    @Test
    public void testMatches_92() {
        Assert.assertTrue(START9.matches(START4));
    }

    @Test
    public void testMatches_93() {
        Assert.assertFalse(START9.matches(START5));
    }

    @Test
    public void testMatches_94() {
        Assert.assertFalse(START9.matches(START6));
    }

    @Test
    public void testMatches_95() {
        Assert.assertFalse(START9.matches(START7));
    }

    @Test
    public void testMatches_96() {
        Assert.assertTrue(START9.matches(START8));
    }

    @Test
    public void testMatches_97() {
        Assert.assertTrue(START9.matches(START9));
    }

    @Test
    public void testMatches_98() {
        Assert.assertTrue(END0.matches(END0));
    }

    @Test
    public void testMatches_99() {
        Assert.assertTrue(END0.matches(END1));
    }

    @Test
    public void testMatches_100() {
        Assert.assertTrue(END0.matches(END2));
    }

    @Test
    public void testMatches_101() {
        Assert.assertTrue(END0.matches(END3));
    }

    @Test
    public void testMatches_102() {
        Assert.assertFalse(END0.matches(END4));
    }

    @Test
    public void testMatches_103() {
        Assert.assertTrue(END0.matches(END5));
    }

    @Test
    public void testMatches_104() {
        Assert.assertTrue(END0.matches(END6));
    }

    @Test
    public void testMatches_105() {
        Assert.assertFalse(END0.matches(END7));
    }

    @Test
    public void testMatches_106() {
        Assert.assertFalse(END0.matches(END8));
    }

    @Test
    public void testMatches_107() {
        Assert.assertFalse(END0.matches(END9));
    }

    @Test
    public void testMatches_108() {
        Assert.assertFalse(END0.matches(END10));
    }

    @Test
    public void testMatches_109() {
        Assert.assertTrue(END1.matches(END0));
    }

    @Test
    public void testMatches_110() {
        Assert.assertTrue(END1.matches(END1));
    }

    @Test
    public void testMatches_111() {
        Assert.assertTrue(END1.matches(END2));
    }

    @Test
    public void testMatches_112() {
        Assert.assertTrue(END1.matches(END3));
    }

    @Test
    public void testMatches_113() {
        Assert.assertFalse(END1.matches(END4));
    }

    @Test
    public void testMatches_114() {
        Assert.assertTrue(END1.matches(END5));
    }

    @Test
    public void testMatches_115() {
        Assert.assertTrue(END1.matches(END6));
    }

    @Test
    public void testMatches_116() {
        Assert.assertFalse(END1.matches(END7));
    }

    @Test
    public void testMatches_117() {
        Assert.assertFalse(END1.matches(END8));
    }

    @Test
    public void testMatches_118() {
        Assert.assertFalse(END1.matches(END9));
    }

    @Test
    public void testMatches_119() {
        Assert.assertFalse(END1.matches(END10));
    }

    @Test
    public void testMatches_120() {
        Assert.assertTrue(END2.matches(END0));
    }

    @Test
    public void testMatches_121() {
        Assert.assertTrue(END2.matches(END1));
    }

    @Test
    public void testMatches_122() {
        Assert.assertTrue(END2.matches(END2));
    }

    @Test
    public void testMatches_123() {
        Assert.assertFalse(END2.matches(END3));
    }

    @Test
    public void testMatches_124() {
        Assert.assertFalse(END2.matches(END4));
    }

    @Test
    public void testMatches_125() {
        Assert.assertTrue(END2.matches(END5));
    }

    @Test
    public void testMatches_126() {
        Assert.assertTrue(END2.matches(END6));
    }

    @Test
    public void testMatches_127() {
        Assert.assertFalse(END2.matches(END7));
    }

    @Test
    public void testMatches_128() {
        Assert.assertFalse(END2.matches(END8));
    }

    @Test
    public void testMatches_129() {
        Assert.assertFalse(END2.matches(END9));
    }

    @Test
    public void testMatches_130() {
        Assert.assertFalse(END2.matches(END10));
    }

    @Test
    public void testMatches_131() {
        Assert.assertTrue(END3.matches(END0));
    }

    @Test
    public void testMatches_132() {
        Assert.assertTrue(END3.matches(END1));
    }

    @Test
    public void testMatches_133() {
        Assert.assertFalse(END3.matches(END2));
    }

    @Test
    public void testMatches_134() {
        Assert.assertTrue(END3.matches(END3));
    }

    @Test
    public void testMatches_135() {
        Assert.assertFalse(END3.matches(END4));
    }

    @Test
    public void testMatches_136() {
        Assert.assertTrue(END3.matches(END5));
    }

    @Test
    public void testMatches_137() {
        Assert.assertFalse(END3.matches(END6));
    }

    @Test
    public void testMatches_138() {
        Assert.assertFalse(END3.matches(END7));
    }

    @Test
    public void testMatches_139() {
        Assert.assertFalse(END3.matches(END8));
    }

    @Test
    public void testMatches_140() {
        Assert.assertFalse(END3.matches(END9));
    }

    @Test
    public void testMatches_141() {
        Assert.assertFalse(END3.matches(END10));
    }

    @Test
    public void testMatches_142() {
        Assert.assertFalse(END4.matches(END0));
    }

    @Test
    public void testMatches_143() {
        Assert.assertFalse(END4.matches(END1));
    }

    @Test
    public void testMatches_144() {
        Assert.assertFalse(END4.matches(END2));
    }

    @Test
    public void testMatches_145() {
        Assert.assertFalse(END4.matches(END3));
    }

    @Test
    public void testMatches_146() {
        Assert.assertTrue(END4.matches(END4));
    }

    @Test
    public void testMatches_147() {
        Assert.assertFalse(END4.matches(END5));
    }

    @Test
    public void testMatches_148() {
        Assert.assertFalse(END4.matches(END6));
    }

    @Test
    public void testMatches_149() {
        Assert.assertFalse(END4.matches(END7));
    }

    @Test
    public void testMatches_150() {
        Assert.assertFalse(END4.matches(END8));
    }

    @Test
    public void testMatches_151() {
        Assert.assertFalse(END4.matches(END9));
    }

    @Test
    public void testMatches_152() {
        Assert.assertFalse(END4.matches(END10));
    }

    @Test
    public void testMatches_153() {
        Assert.assertTrue(END5.matches(END0));
    }

    @Test
    public void testMatches_154() {
        Assert.assertTrue(END5.matches(END1));
    }

    @Test
    public void testMatches_155() {
        Assert.assertTrue(END5.matches(END2));
    }

    @Test
    public void testMatches_156() {
        Assert.assertTrue(END5.matches(END3));
    }

    @Test
    public void testMatches_157() {
        Assert.assertTrue(END5.matches(END4));
    }

    @Test
    public void testMatches_158() {
        Assert.assertTrue(END5.matches(END5));
    }

    @Test
    public void testMatches_159() {
        Assert.assertTrue(END5.matches(END6));
    }

    @Test
    public void testMatches_160() {
        Assert.assertTrue(END5.matches(END7));
    }

    @Test
    public void testMatches_161() {
        Assert.assertTrue(END5.matches(END8));
    }

    @Test
    public void testMatches_162() {
        Assert.assertTrue(END5.matches(END9));
    }

    @Test
    public void testMatches_163() {
        Assert.assertTrue(END5.matches(END10));
    }

    @Test
    public void testMatches_164() {
        Assert.assertTrue(END6.matches(END0));
    }

    @Test
    public void testMatches_165() {
        Assert.assertTrue(END6.matches(END1));
    }

    @Test
    public void testMatches_166() {
        Assert.assertTrue(END6.matches(END2));
    }

    @Test
    public void testMatches_167() {
        Assert.assertFalse(END6.matches(END3));
    }

    @Test
    public void testMatches_168() {
        Assert.assertTrue(END6.matches(END4));
    }

    @Test
    public void testMatches_169() {
        Assert.assertTrue(END6.matches(END5));
    }

    @Test
    public void testMatches_170() {
        Assert.assertTrue(END6.matches(END6));
    }

    @Test
    public void testMatches_171() {
        Assert.assertTrue(END6.matches(END7));
    }

    @Test
    public void testMatches_172() {
        Assert.assertTrue(END6.matches(END8));
    }

    @Test
    public void testMatches_173() {
        Assert.assertTrue(END6.matches(END9));
    }

    @Test
    public void testMatches_174() {
        Assert.assertTrue(END6.matches(END10));
    }

    @Test
    public void testMatches_175() {
        Assert.assertTrue(END7.matches(END0));
    }

    @Test
    public void testMatches_176() {
        Assert.assertTrue(END7.matches(END1));
    }

    @Test
    public void testMatches_177() {
        Assert.assertTrue(END7.matches(END2));
    }

    @Test
    public void testMatches_178() {
        Assert.assertTrue(END7.matches(END3));
    }

    @Test
    public void testMatches_179() {
        Assert.assertTrue(END7.matches(END4));
    }

    @Test
    public void testMatches_180() {
        Assert.assertTrue(END7.matches(END5));
    }

    @Test
    public void testMatches_181() {
        Assert.assertTrue(END7.matches(END6));
    }

    @Test
    public void testMatches_182() {
        Assert.assertTrue(END7.matches(END7));
    }

    @Test
    public void testMatches_183() {
        Assert.assertTrue(END7.matches(END8));
    }

    @Test
    public void testMatches_184() {
        Assert.assertTrue(END7.matches(END9));
    }

    @Test
    public void testMatches_185() {
        Assert.assertTrue(END7.matches(END10));
    }

    @Test
    public void testMatches_186() {
        Assert.assertTrue(END8.matches(END0));
    }

    @Test
    public void testMatches_187() {
        Assert.assertTrue(END8.matches(END1));
    }

    @Test
    public void testMatches_188() {
        Assert.assertTrue(END8.matches(END2));
    }

    @Test
    public void testMatches_189() {
        Assert.assertFalse(END8.matches(END3));
    }

    @Test
    public void testMatches_190() {
        Assert.assertTrue(END8.matches(END4));
    }

    @Test
    public void testMatches_191() {
        Assert.assertTrue(END8.matches(END5));
    }

    @Test
    public void testMatches_192() {
        Assert.assertTrue(END8.matches(END6));
    }

    @Test
    public void testMatches_193() {
        Assert.assertTrue(END8.matches(END7));
    }

    @Test
    public void testMatches_194() {
        Assert.assertTrue(END8.matches(END8));
    }

    @Test
    public void testMatches_195() {
        Assert.assertTrue(END8.matches(END9));
    }

    @Test
    public void testMatches_196() {
        Assert.assertTrue(END8.matches(END10));
    }

    @Test
    public void testMatches_197() {
        Assert.assertTrue(END9.matches(END0));
    }

    @Test
    public void testMatches_198() {
        Assert.assertFalse(END9.matches(END1));
    }

    @Test
    public void testMatches_199() {
        Assert.assertFalse(END9.matches(END2));
    }

    @Test
    public void testMatches_200() {
        Assert.assertFalse(END9.matches(END3));
    }

    @Test
    public void testMatches_201() {
        Assert.assertTrue(END9.matches(END4));
    }

    @Test
    public void testMatches_202() {
        Assert.assertFalse(END9.matches(END5));
    }

    @Test
    public void testMatches_203() {
        Assert.assertFalse(END9.matches(END6));
    }

    @Test
    public void testMatches_204() {
        Assert.assertFalse(END9.matches(END7));
    }

    @Test
    public void testMatches_205() {
        Assert.assertFalse(END9.matches(END8));
    }

    @Test
    public void testMatches_206() {
        Assert.assertTrue(END9.matches(END9));
    }

    @Test
    public void testMatches_207() {
        Assert.assertTrue(END9.matches(END10));
    }

    @Test
    public void testMatches_208() {
        Assert.assertTrue(END10.matches(END0));
    }

    @Test
    public void testMatches_209() {
        Assert.assertFalse(END10.matches(END1));
    }

    @Test
    public void testMatches_210() {
        Assert.assertFalse(END10.matches(END2));
    }

    @Test
    public void testMatches_211() {
        Assert.assertFalse(END10.matches(END3));
    }

    @Test
    public void testMatches_212() {
        Assert.assertTrue(END10.matches(END4));
    }

    @Test
    public void testMatches_213() {
        Assert.assertFalse(END10.matches(END5));
    }

    @Test
    public void testMatches_214() {
        Assert.assertFalse(END10.matches(END6));
    }

    @Test
    public void testMatches_215() {
        Assert.assertFalse(END10.matches(END7));
    }

    @Test
    public void testMatches_216() {
        Assert.assertFalse(END10.matches(END8));
    }

    @Test
    public void testMatches_217() {
        Assert.assertTrue(END10.matches(END9));
    }

    @Test
    public void testMatches_218() {
        Assert.assertTrue(END10.matches(END10));
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
    public void testIsValidStart_7() {
        Assert.assertTrue(START6.isValidStart());
    }

    @Test
    public void testIsValidStart_8() {
        Assert.assertTrue(START7.isValidStart());
    }

    @Test
    public void testIsValidStart_9() {
        Assert.assertTrue(START8.isValidStart());
    }

    @Test
    public void testIsValidStart_10() {
        Assert.assertTrue(START9.isValidStart());
    }

    @Test
    public void testPlus_1() {
        Assert.assertEquals(startMetadata(ImmutableMap.of(0, 2L, 1, 3L, 2, 5L)), START1.plus(START3));
    }

    @Test
    public void testPlus_2() {
        Assert.assertEquals(startMetadata(ImmutableMap.of(0, 2L, 1, 4L, 2, 5L)), START0.plus(START2));
    }

    @Test
    public void testPlus_3() {
        Assert.assertEquals(startMetadata(ImmutableMap.of(0, 2L, 1, 4L, 2, 5L)), START1.plus(START2));
    }

    @Test
    public void testPlus_4() {
        Assert.assertEquals(startMetadata(ImmutableMap.of(0, 2L, 1, 3L, 2, 5L)), START2.plus(START1));
    }

    @Test
    public void testPlus_5() {
        Assert.assertEquals(startMetadata(ImmutableMap.of(0, 2L, 1, 4L, 2, 5L)), START2.plus(START2));
    }

    @Test
    public void testPlus_6() {
        Assert.assertEquals(START4, START1.plus(START4));
    }

    @Test
    public void testPlus_7() {
        Assert.assertEquals(startMetadata(ImmutableMap.of(0, 2L, 1, 3L)), START1.plus(START5));
    }

    @Test
    public void testPlus_8() {
        Assert.assertEquals(startMetadata(ImmutableMap.of(0, 2L, 1, 3L)), START1.plus(START6));
    }

    @Test
    public void testPlus_9() {
        Assert.assertEquals(startMetadata(ImmutableMap.of(0, 2L, 1, 3L, 2, 5L)), START1.plus(START7));
    }

    @Test
    public void testPlus_10() {
        Assert.assertEquals(startMetadata(ImmutableMap.of(0, 2L, 1, 3L, 2, 5L)), START2.plus(START6));
    }

    @Test
    public void testPlus_11() {
        Assert.assertEquals(START0, START4.plus(START0));
    }

    @Test
    public void testPlus_12() {
        Assert.assertEquals(START1, START4.plus(START1));
    }

    @Test
    public void testPlus_13() {
        Assert.assertEquals(START4, START4.plus(START5));
    }

    @Test
    public void testPlus_14() {
        Assert.assertEquals(START5, START5.plus(START4));
    }

    @Test
    public void testPlus_15() {
        Assert.assertEquals(startMetadataMultiTopic("foo.*", ImmutableList.of("foo", "foo2"), ImmutableMap.of(0, 2L, 1, 3L)), START5.plus(START6));
    }

    @Test
    public void testPlus_16() {
        Assert.assertEquals(startMetadataMultiTopic("foo.*", ImmutableList.of("foo", "foo2"), ImmutableMap.of(0, 2L, 2, 5L)), START7.plus(START8));
    }

    @Test
    public void testPlus_17() {
        Assert.assertEquals(startMetadataMultiTopic("foo.*", ImmutableList.of("foo", "foo2", "foo22"), ImmutableMap.of(0, 2L, 2, 5L)), START7.plus(START9));
    }

    @Test
    public void testPlus_18() {
        Assert.assertEquals(startMetadataMultiTopic("foo2.*", ImmutableList.of("foo2"), ImmutableMap.of(0, 2L, 2, 5L)), START8.plus(START7));
    }

    @Test
    public void testPlus_19() {
        Assert.assertEquals(startMetadataMultiTopic("foo2.*", ImmutableList.of("foo2", "foo22"), ImmutableMap.of(0, 2L, 2, 5L)), START8.plus(START9));
    }

    @Test
    public void testPlus_20() {
        Assert.assertEquals(startMetadataMultiTopic("foo2.*", ImmutableList.of("foo2", "foo22"), ImmutableMap.of(0, 2L, 2, 5L)), START9.plus(START8));
    }

    @Test
    public void testPlus_21() {
        Assert.assertEquals(endMetadata(ImmutableMap.of(0, 2L, 2, 5L)), END0.plus(END1));
    }

    @Test
    public void testPlus_22() {
        Assert.assertEquals(endMetadata(ImmutableMap.of(0, 2L, 1, 4L, 2, 5L)), END1.plus(END2));
    }

    @Test
    public void testPlus_23() {
        Assert.assertEquals(END4, END0.plus(END4));
    }

    @Test
    public void testPlus_24() {
        Assert.assertEquals(END4, END1.plus(END4));
    }

    @Test
    public void testPlus_25() {
        Assert.assertEquals(END0, END4.plus(END0));
    }

    @Test
    public void testPlus_26() {
        Assert.assertEquals(END1, END4.plus(END1));
    }

    @Test
    public void testPlus_27() {
        Assert.assertEquals(END3, END2.plus(END3));
    }

    @Test
    public void testPlus_28() {
        Assert.assertEquals(END2, END3.plus(END2));
    }

    @Test
    public void testPlus_29() {
        Assert.assertEquals(END4, END4.plus(END5));
    }

    @Test
    public void testPlus_30() {
        Assert.assertEquals(END5, END5.plus(END4));
    }

    @Test
    public void testPlus_31() {
        Assert.assertEquals(endMetadataMultiTopic("foo.*", ImmutableList.of("foo", "foo2"), ImmutableMap.of(0, 2L, 2, 5L)), END5.plus(END9));
    }

    @Test
    public void testPlus_32() {
        Assert.assertEquals(endMetadataMultiTopic("foo.*", ImmutableList.of("foo", "foo2", "foo22"), ImmutableMap.of(0, 2L, 2, 5L)), END5.plus(END10));
    }

    @Test
    public void testPlus_33() {
        Assert.assertEquals(endMetadataMultiTopic("foo.*", ImmutableList.of("foo"), ImmutableMap.of(0, 2L, 1, 4L, 2, 5L)), END5.plus(END6));
    }

    @Test
    public void testPlus_34() {
        Assert.assertEquals(endMetadataMultiTopic("foo.*", ImmutableList.of("foo", "foo2"), ImmutableMap.of(0, 2L, 2, 5L)), END5.plus(END7));
    }

    @Test
    public void testPlus_35() {
        Assert.assertEquals(endMetadataMultiTopic("foo.*", ImmutableList.of("foo", "foo2"), ImmutableMap.of(0, 2L, 2, 5L)), END7.plus(END5));
    }

    @Test
    public void testPlus_36() {
        Assert.assertEquals(endMetadataMultiTopic("foo2.*", ImmutableList.of("foo2"), ImmutableMap.of(0, 2L, 2, 5L)), END9.plus(END5));
    }

    @Test
    public void testPlus_37() {
        Assert.assertEquals(endMetadataMultiTopic("foo2.*", ImmutableList.of("foo2", "foo22"), ImmutableMap.of(0, 2L, 2, 5L)), END9.plus(END10));
    }

    @Test
    public void testPlus_38() {
        Assert.assertEquals(endMetadataMultiTopic("foo2.*", ImmutableList.of("foo2", "foo22"), ImmutableMap.of(0, 2L, 2, 5L)), END10.plus(END9));
    }

    @Test
    public void testMinus_1() {
        Assert.assertEquals(startMetadata(ImmutableMap.of()), START0.minus(START2));
    }

    @Test
    public void testMinus_2() {
        Assert.assertEquals(START0, START0.minus(START4));
    }

    @Test
    public void testMinus_3() {
        Assert.assertEquals(startMetadata(ImmutableMap.of()), START1.minus(START2));
    }

    @Test
    public void testMinus_4() {
        Assert.assertEquals(startMetadata(ImmutableMap.of(1, 3L)), START1.minus(START3));
    }

    @Test
    public void testMinus_5() {
        Assert.assertEquals(START1, START1.minus(START4));
    }

    @Test
    public void testMinus_6() {
        Assert.assertEquals(startMetadata("foo", ImmutableMap.of()), START1.minus(START5));
    }

    @Test
    public void testMinus_7() {
        Assert.assertEquals(startMetadata("foo", ImmutableMap.of()), START1.minus(START6));
    }

    @Test
    public void testMinus_8() {
        Assert.assertEquals(startMetadata("foo", ImmutableMap.of(1, 3L)), START1.minus(START7));
    }

    @Test
    public void testMinus_9() {
        Assert.assertEquals(startMetadata(ImmutableMap.of(2, 5L)), START2.minus(START1));
    }

    @Test
    public void testMinus_10() {
        Assert.assertEquals(startMetadata(ImmutableMap.of()), START2.minus(START2));
    }

    @Test
    public void testMinus_11() {
        Assert.assertEquals(START4, START4.minus(START0));
    }

    @Test
    public void testMinus_12() {
        Assert.assertEquals(START4, START4.minus(START1));
    }

    @Test
    public void testMinus_13() {
        Assert.assertEquals(startMetadataMultiTopic("foo.*", ImmutableList.of("foo"), ImmutableMap.of()), START5.minus(START1));
    }

    @Test
    public void testMinus_14() {
        Assert.assertEquals(endMetadata(ImmutableMap.of(1, 4L)), END2.minus(END1));
    }

    @Test
    public void testMinus_15() {
        Assert.assertEquals(endMetadata(ImmutableMap.of(2, 5L)), END1.minus(END2));
    }

    @Test
    public void testMinus_16() {
        Assert.assertEquals(END0, END0.minus(END4));
    }

    @Test
    public void testMinus_17() {
        Assert.assertEquals(END4, END4.minus(END0));
    }

    @Test
    public void testMinus_18() {
        Assert.assertEquals(END1, END1.minus(END4));
    }

    @Test
    public void testMinus_19() {
        Assert.assertEquals(END4, END4.minus(END1));
    }

    @Test
    public void testMinus_20() {
        Assert.assertEquals(endMetadataMultiTopic("foo.*", ImmutableList.of("foo"), ImmutableMap.of()), END5.minus(END1));
    }

    @Test
    public void testMinus_21() {
        Assert.assertEquals(endMetadataMultiTopic("foo.*", ImmutableList.of("foo"), ImmutableMap.of(0, 2L, 2, 5L)), END5.minus(END4));
    }

    @Test
    public void testMinus_22() {
        Assert.assertEquals(endMetadataMultiTopic("foo.*", ImmutableList.of("foo"), ImmutableMap.of(2, 5L)), END5.minus(END6));
    }

    @Test
    public void testMinus_23() {
        Assert.assertEquals(endMetadataMultiTopic("foo.*", ImmutableList.of("foo"), ImmutableMap.of(1, 4L)), END6.minus(END5));
    }

    @Test
    public void testMinus_24() {
        Assert.assertEquals(endMetadataMultiTopic("foo.*", ImmutableList.of("foo"), ImmutableMap.of(1, 4L)), END6.minus(END7));
    }

    @Test
    public void testMinus_25() {
        Assert.assertEquals(endMetadataMultiTopic("foo.*", ImmutableList.of("foo2"), ImmutableMap.of(0, 2L, 2, 5L)), END7.minus(END5));
    }

    @Test
    public void testMinus_26() {
        Assert.assertEquals(endMetadataMultiTopic("foo.*", ImmutableList.of("foo", "foo2"), ImmutableMap.of(2, 5L)), END7.minus(END8));
    }

    @Test
    public void testMinus_27() {
        Assert.assertEquals(endMetadataMultiTopic("foo.*", ImmutableList.of("foo"), ImmutableMap.of(0, 2L, 2, 5L)), END7.minus(END9));
    }

    @Test
    public void testMinus_28() {
        Assert.assertEquals(endMetadataMultiTopic("foo.*", ImmutableList.of("foo"), ImmutableMap.of(0, 2L, 2, 5L)), END7.minus(END10));
    }

    @Test
    public void testMinus_29() {
        Assert.assertEquals(END9, END9.minus(END6));
    }

    @Test
    public void testMinus_30() {
        Assert.assertEquals(endMetadataMultiTopic("foo2.*", ImmutableList.of("foo2"), ImmutableMap.of()), END9.minus(END7));
    }

    @Test
    public void testMinus_31() {
        Assert.assertEquals(endMetadataMultiTopic("foo2.*", ImmutableList.of("foo2"), ImmutableMap.of(2, 5L)), END9.minus(END8));
    }

    @Test
    public void testMinus_32() {
        Assert.assertEquals(endMetadataMultiTopic("foo2.*", ImmutableList.of("foo2"), ImmutableMap.of()), END9.minus(END9));
    }

    @Test
    public void testMinus_33() {
        Assert.assertEquals(endMetadataMultiTopic("foo2.*", ImmutableList.of("foo2"), ImmutableMap.of()), END9.minus(END10));
    }

    @Test
    public void testMinus_34() {
        Assert.assertEquals(endMetadataMultiTopic("foo2.*", ImmutableList.of("foo22"), ImmutableMap.of(0, 2L, 2, 5L)), END10.minus(END9));
    }
}
