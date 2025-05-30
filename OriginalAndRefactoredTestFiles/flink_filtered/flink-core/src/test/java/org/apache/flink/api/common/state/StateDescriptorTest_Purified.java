package org.apache.flink.api.common.state;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.serialization.SerializerConfigImpl;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.common.typeutils.base.IntSerializer;
import org.apache.flink.api.common.typeutils.base.StringSerializer;
import org.apache.flink.api.java.typeutils.PojoTypeInfo;
import org.apache.flink.api.java.typeutils.runtime.kryo.KryoSerializer;
import org.apache.flink.core.fs.Path;
import org.apache.flink.core.testutils.CheckedThread;
import org.apache.flink.core.testutils.CommonTestUtils;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Fail.fail;

class StateDescriptorTest_Purified {

    private static class TestStateDescriptor<T> extends StateDescriptor<State, T> {

        private static final long serialVersionUID = 1L;

        TestStateDescriptor(String name, TypeSerializer<T> serializer) {
            super(name, serializer, null);
        }

        TestStateDescriptor(String name, TypeInformation<T> typeInfo) {
            super(name, typeInfo, null);
        }

        TestStateDescriptor(String name, Class<T> type) {
            super(name, type, null);
        }

        @Override
        public Type getType() {
            return Type.VALUE;
        }
    }

    private static class OtherTestStateDescriptor<T> extends StateDescriptor<State, T> {

        private static final long serialVersionUID = 1L;

        OtherTestStateDescriptor(String name, TypeSerializer<T> serializer) {
            super(name, serializer, null);
        }

        OtherTestStateDescriptor(String name, TypeInformation<T> typeInfo) {
            super(name, typeInfo, null);
        }

        OtherTestStateDescriptor(String name, Class<T> type) {
            super(name, type, null);
        }

        @Override
        public Type getType() {
            return Type.VALUE;
        }
    }

    @Test
    void testInitializeSerializerAfterSerializationWithCustomConfig_1() throws Exception {
    }

    @Test
    void testInitializeSerializerAfterSerializationWithCustomConfig_2() throws Exception {
        final ExecutionConfig config = new ExecutionConfig();
        ((SerializerConfigImpl) config.getSerializerConfig()).registerKryoType(File.class);
        final TestStateDescriptor<Path> original = new TestStateDescriptor<>("test", Path.class);
        TestStateDescriptor<Path> clone = CommonTestUtils.createCopySerializable(original);
        clone.initializeSerializerUnlessSet(config);
        assertThat(((KryoSerializer<?>) clone.getSerializer()).getKryo().getRegistration(File.class).getId() > 0).isTrue();
    }
}
