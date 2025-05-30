import org.junit.jupiter.api.Test;
import static org.apache.flink.architecture.common.Predicates.getClassSimpleNameFromFqName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PredicatesTest_Purified {

    @Test
    void testGetClassSimpleNameFromFqName_1() {
        assertThat(getClassSimpleNameFromFqName("com.example.OuterClass")).isEqualTo("OuterClass");
    }

    @Test
    void testGetClassSimpleNameFromFqName_2() {
        assertThat(getClassSimpleNameFromFqName("com.example.OuterClass.InnerClass")).isEqualTo("InnerClass");
    }

    @Test
    void testGetClassSimpleNameFromFqName_3() {
        assertThat(getClassSimpleNameFromFqName("com.example.OuterClass$InnerClass")).isEqualTo("InnerClass");
    }
}
