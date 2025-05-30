import org.junit.jupiter.api.Test;
import static org.apache.flink.architecture.common.Predicates.getClassSimpleNameFromFqName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PredicatesTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testGetClassSimpleNameFromFqName_1to3")
    void testGetClassSimpleNameFromFqName_1to3(String param1, String param2) {
        assertThat(getClassSimpleNameFromFqName(param2)).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testGetClassSimpleNameFromFqName_1to3() {
        return Stream.of(arguments("OuterClass", "com.example.OuterClass"), arguments("InnerClass", "com.example.OuterClass.InnerClass"), arguments("InnerClass", "com.example.OuterClass$InnerClass"));
    }
}
