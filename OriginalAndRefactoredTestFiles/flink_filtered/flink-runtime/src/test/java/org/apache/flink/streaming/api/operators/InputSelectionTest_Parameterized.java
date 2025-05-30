package org.apache.flink.streaming.api.operators;

import org.apache.flink.streaming.api.operators.InputSelection.Builder;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class InputSelectionTest_Parameterized {

    static class BuilderTest {

        @Test
        void testSelect() {
            assertThat(new Builder().select(1).build().getInputMask()).isOne();
            assertThat(new Builder().select(1).select(2).select(3).build().getInputMask()).isEqualTo(7L);
            assertThat(new Builder().select(64).build().getInputMask()).isEqualTo(0x8000_0000_0000_0000L);
            assertThat(new Builder().select(-1).build().getInputMask()).isEqualTo(0xffff_ffff_ffff_ffffL);
        }

        @Test
        void testIllegalInputId1() {
            assertThatThrownBy(() -> new Builder().select(-2)).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void testIllegalInputId2() {
            assertThatThrownBy(() -> new Builder().select(65)).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testIsInputSelected_1() {
        assertThat(new Builder().build().isInputSelected(1)).isFalse();
    }

    @Test
    void testIsInputSelected_2() {
        assertThat(new Builder().select(2).build().isInputSelected(1)).isFalse();
    }

    @Test
    void testIsInputSelected_4() {
        assertThat(new Builder().select(1).select(2).build().isInputSelected(1)).isTrue();
    }

    @Test
    void testIsInputSelected_5() {
        assertThat(new Builder().select(-1).build().isInputSelected(1)).isTrue();
    }

    @Test
    void testInputSelectionNormalization_1() {
        assertThat(InputSelection.ALL.areAllInputsSelected()).isTrue();
    }

    @Test
    void testInputSelectionNormalization_3() {
        assertThat(new Builder().select(1).select(2).build(2).areAllInputsSelected()).isTrue();
    }

    @Test
    void testInputSelectionNormalization_4() {
        assertThat(new Builder().select(1).select(2).select(3).build().areAllInputsSelected()).isFalse();
    }

    @Test
    void testInputSelectionNormalization_5() {
        assertThat(new Builder().select(1).select(2).select(3).build(3).areAllInputsSelected()).isTrue();
    }

    @Test
    void testInputSelectionNormalization_7() {
        assertThat(new Builder().select(1).select(3).build(3).areAllInputsSelected()).isFalse();
    }

    @Test
    void testInputSelectionNormalization_8() {
        assertThat(InputSelection.FIRST.areAllInputsSelected()).isFalse();
    }

    @Test
    void testInputSelectionNormalization_9() {
        assertThat(InputSelection.SECOND.areAllInputsSelected()).isFalse();
    }

    @Test
    void testFairSelectNextIndexOutOf2_2() {
        assertThat(new Builder().select(1).select(2).build().fairSelectNextIndexOutOf2(3, 1)).isZero();
    }

    @Test
    void testFairSelectNextIndexWithAllInputsSelected_2() {
        assertThat(InputSelection.ALL.fairSelectNextIndex(7, 1)).isEqualTo(2);
    }

    @Test
    void testFairSelectNextIndexWithAllInputsSelected_3() {
        assertThat(InputSelection.ALL.fairSelectNextIndex(7, 2)).isZero();
    }

    @Test
    void testFairSelectNextIndexWithAllInputsSelected_5() {
        assertThat(InputSelection.ALL.fairSelectNextIndex(0, 2)).isEqualTo(InputSelection.NONE_AVAILABLE);
    }

    @Test
    void testFairSelectNextIndexWithAllInputsSelected_6() {
        assertThat(InputSelection.ALL.fairSelectNextIndex(-1, 10)).isEqualTo(11);
    }

    @Test
    void testFairSelectNextIndexWithSomeInputsSelected_1_testMerged_1() {
        InputSelection selection = new Builder().select(2).select(3).select(4).select(5).select(8).build();
        int availableInputs = (int) new Builder().select(3).select(5).select(6).select(8).build().getInputMask();
        assertThat(selection.fairSelectNextIndex(availableInputs, 0)).isEqualTo(2);
        assertThat(selection.fairSelectNextIndex(availableInputs, 1)).isEqualTo(2);
        assertThat(selection.fairSelectNextIndex(availableInputs, 2)).isEqualTo(4);
        assertThat(selection.fairSelectNextIndex(availableInputs, 3)).isEqualTo(4);
        assertThat(selection.fairSelectNextIndex(availableInputs, 4)).isEqualTo(7);
        assertThat(selection.fairSelectNextIndex(availableInputs, 5)).isEqualTo(7);
        assertThat(selection.fairSelectNextIndex(availableInputs, 6)).isEqualTo(7);
        assertThat(selection.fairSelectNextIndex(availableInputs, 7)).isEqualTo(2);
        assertThat(selection.fairSelectNextIndex(availableInputs, 8)).isEqualTo(2);
        assertThat(selection.fairSelectNextIndex(availableInputs, 158)).isEqualTo(2);
        assertThat(selection.fairSelectNextIndex(0, 5)).isEqualTo(InputSelection.NONE_AVAILABLE);
    }

    @Test
    void testFairSelectNextIndexWithSomeInputsSelected_12() {
        assertThat(new Builder().build().fairSelectNextIndex(-1, 5)).isEqualTo(InputSelection.NONE_AVAILABLE);
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsInputSelected_3_6")
    void testIsInputSelected_3_6(int param1, int param2) {
        assertThat(new Builder().select(param2).build().isInputSelected(param1)).isTrue();
    }

    static public Stream<Arguments> Provider_testIsInputSelected_3_6() {
        return Stream.of(arguments(1, 1), arguments(64, 64));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInputSelectionNormalization_2_6")
    void testInputSelectionNormalization_2_6(int param1, int param2) {
        assertThat(new Builder().select(param2).select(param1).build().areAllInputsSelected()).isFalse();
    }

    static public Stream<Arguments> Provider_testInputSelectionNormalization_2_6() {
        return Stream.of(arguments(2, 1), arguments(3, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFairSelectNextIndexOutOf2_1_3to4")
    void testFairSelectNextIndexOutOf2_1_3to4(int param1, int param2) {
        assertThat(InputSelection.ALL.fairSelectNextIndexOutOf2(param1, param2)).isOne();
    }

    static public Stream<Arguments> Provider_testFairSelectNextIndexOutOf2_1_3to4() {
        return Stream.of(arguments(3, 0), arguments(2, 0), arguments(2, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFairSelectNextIndexOutOf2_5to6")
    void testFairSelectNextIndexOutOf2_5to6(int param1, int param2) {
        assertThat(InputSelection.ALL.fairSelectNextIndexOutOf2(param1, param2)).isZero();
    }

    static public Stream<Arguments> Provider_testFairSelectNextIndexOutOf2_5to6() {
        return Stream.of(arguments(1, 0), arguments(1, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFairSelectNextIndexOutOf2_7to8")
    void testFairSelectNextIndexOutOf2_7to8(int param1, int param2) {
        assertThat(InputSelection.ALL.fairSelectNextIndexOutOf2(param1, param2)).isEqualTo(InputSelection.NONE_AVAILABLE);
    }

    static public Stream<Arguments> Provider_testFairSelectNextIndexOutOf2_7to8() {
        return Stream.of(arguments(0, 0), arguments(0, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFairSelectNextIndexOutOf2_9to10")
    void testFairSelectNextIndexOutOf2_9to10(int param1, int param2) {
        assertThat(InputSelection.FIRST.fairSelectNextIndexOutOf2(param1, param2)).isZero();
    }

    static public Stream<Arguments> Provider_testFairSelectNextIndexOutOf2_9to10() {
        return Stream.of(arguments(1, 0), arguments(3, 0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFairSelectNextIndexOutOf2_11to12")
    void testFairSelectNextIndexOutOf2_11to12(int param1, int param2) {
        assertThat(InputSelection.FIRST.fairSelectNextIndexOutOf2(param1, param2)).isEqualTo(InputSelection.NONE_AVAILABLE);
    }

    static public Stream<Arguments> Provider_testFairSelectNextIndexOutOf2_11to12() {
        return Stream.of(arguments(2, 0), arguments(0, 0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFairSelectNextIndexOutOf2_13to14")
    void testFairSelectNextIndexOutOf2_13to14(int param1, int param2) {
        assertThat(InputSelection.SECOND.fairSelectNextIndexOutOf2(param1, param2)).isOne();
    }

    static public Stream<Arguments> Provider_testFairSelectNextIndexOutOf2_13to14() {
        return Stream.of(arguments(2, 1), arguments(3, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFairSelectNextIndexOutOf2_15to16")
    void testFairSelectNextIndexOutOf2_15to16(int param1, int param2) {
        assertThat(InputSelection.SECOND.fairSelectNextIndexOutOf2(param1, param2)).isEqualTo(InputSelection.NONE_AVAILABLE);
    }

    static public Stream<Arguments> Provider_testFairSelectNextIndexOutOf2_15to16() {
        return Stream.of(arguments(1, 1), arguments(0, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFairSelectNextIndexWithAllInputsSelected_1_4")
    void testFairSelectNextIndexWithAllInputsSelected_1_4(int param1, int param2) {
        assertThat(InputSelection.ALL.fairSelectNextIndex(param1, param2)).isOne();
    }

    static public Stream<Arguments> Provider_testFairSelectNextIndexWithAllInputsSelected_1_4() {
        return Stream.of(arguments(7, 0), arguments(7, 0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFairSelectNextIndexWithAllInputsSelected_7to8")
    void testFairSelectNextIndexWithAllInputsSelected_7to8(int param1, int param2) {
        assertThat(InputSelection.ALL.fairSelectNextIndex(-param2, param1)).isZero();
    }

    static public Stream<Arguments> Provider_testFairSelectNextIndexWithAllInputsSelected_7to8() {
        return Stream.of(arguments(63, 1), arguments(158, 1));
    }
}
