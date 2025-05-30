package org.apache.flink.api.common.typeinfo;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.typeutils.GenericTypeInfo;
import org.apache.flink.api.java.typeutils.TupleTypeInfo;
import org.apache.flink.util.FlinkRuntimeException;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TypeInformationTest_Purified {

    @Test
    void testOfTypeHint_1() {
        assertThat(TypeInformation.of(String.class)).isEqualTo(BasicTypeInfo.STRING_TYPE_INFO);
    }

    @Test
    void testOfTypeHint_2() {
        assertThat(TypeInformation.of(new TypeHint<String>() {
        })).isEqualTo(BasicTypeInfo.STRING_TYPE_INFO);
    }

    @Test
    void testOfTypeHint_3() {
        TypeInformation<Tuple3<String, Double, Boolean>> tupleInfo = new TupleTypeInfo<>(BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.DOUBLE_TYPE_INFO, BasicTypeInfo.BOOLEAN_TYPE_INFO);
        assertThat(TypeInformation.of(new TypeHint<Tuple3<String, Double, Boolean>>() {
        })).isEqualTo(tupleInfo);
    }
}
