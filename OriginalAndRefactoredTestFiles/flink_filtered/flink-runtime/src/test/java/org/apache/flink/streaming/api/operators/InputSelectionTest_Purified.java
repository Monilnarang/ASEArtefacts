package org.apache.flink.streaming.api.operators;

import org.apache.flink.streaming.api.operators.InputSelection.Builder;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputSelectionTest_Purified {

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
    void testIsInputSelected_3() {
        assertThat(new Builder().select(1).build().isInputSelected(1)).isTrue();
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
    void testIsInputSelected_6() {
        assertThat(new Builder().select(64).build().isInputSelected(64)).isTrue();
    }

    @Test
    void testInputSelectionNormalization_1() {
        assertThat(InputSelection.ALL.areAllInputsSelected()).isTrue();
    }

    @Test
    void testInputSelectionNormalization_2() {
        assertThat(new Builder().select(1).select(2).build().areAllInputsSelected()).isFalse();
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
    void testInputSelectionNormalization_6() {
        assertThat(new Builder().select(1).select(3).build().areAllInputsSelected()).isFalse();
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
    void testFairSelectNextIndexOutOf2_1() {
        assertThat(InputSelection.ALL.fairSelectNextIndexOutOf2(3, 0)).isOne();
    }

    @Test
    void testFairSelectNextIndexOutOf2_2() {
        assertThat(new Builder().select(1).select(2).build().fairSelectNextIndexOutOf2(3, 1)).isZero();
    }

    @Test
    void testFairSelectNextIndexOutOf2_3() {
        assertThat(InputSelection.ALL.fairSelectNextIndexOutOf2(2, 0)).isOne();
    }

    @Test
    void testFairSelectNextIndexOutOf2_4() {
        assertThat(InputSelection.ALL.fairSelectNextIndexOutOf2(2, 1)).isOne();
    }

    @Test
    void testFairSelectNextIndexOutOf2_5() {
        assertThat(InputSelection.ALL.fairSelectNextIndexOutOf2(1, 0)).isZero();
    }

    @Test
    void testFairSelectNextIndexOutOf2_6() {
        assertThat(InputSelection.ALL.fairSelectNextIndexOutOf2(1, 1)).isZero();
    }

    @Test
    void testFairSelectNextIndexOutOf2_7() {
        assertThat(InputSelection.ALL.fairSelectNextIndexOutOf2(0, 0)).isEqualTo(InputSelection.NONE_AVAILABLE);
    }

    @Test
    void testFairSelectNextIndexOutOf2_8() {
        assertThat(InputSelection.ALL.fairSelectNextIndexOutOf2(0, 1)).isEqualTo(InputSelection.NONE_AVAILABLE);
    }

    @Test
    void testFairSelectNextIndexOutOf2_9() {
        assertThat(InputSelection.FIRST.fairSelectNextIndexOutOf2(1, 0)).isZero();
    }

    @Test
    void testFairSelectNextIndexOutOf2_10() {
        assertThat(InputSelection.FIRST.fairSelectNextIndexOutOf2(3, 0)).isZero();
    }

    @Test
    void testFairSelectNextIndexOutOf2_11() {
        assertThat(InputSelection.FIRST.fairSelectNextIndexOutOf2(2, 0)).isEqualTo(InputSelection.NONE_AVAILABLE);
    }

    @Test
    void testFairSelectNextIndexOutOf2_12() {
        assertThat(InputSelection.FIRST.fairSelectNextIndexOutOf2(0, 0)).isEqualTo(InputSelection.NONE_AVAILABLE);
    }

    @Test
    void testFairSelectNextIndexOutOf2_13() {
        assertThat(InputSelection.SECOND.fairSelectNextIndexOutOf2(2, 1)).isOne();
    }

    @Test
    void testFairSelectNextIndexOutOf2_14() {
        assertThat(InputSelection.SECOND.fairSelectNextIndexOutOf2(3, 1)).isOne();
    }

    @Test
    void testFairSelectNextIndexOutOf2_15() {
        assertThat(InputSelection.SECOND.fairSelectNextIndexOutOf2(1, 1)).isEqualTo(InputSelection.NONE_AVAILABLE);
    }

    @Test
    void testFairSelectNextIndexOutOf2_16() {
        assertThat(InputSelection.SECOND.fairSelectNextIndexOutOf2(0, 1)).isEqualTo(InputSelection.NONE_AVAILABLE);
    }

    @Test
    void testFairSelectNextIndexWithAllInputsSelected_1() {
        assertThat(InputSelection.ALL.fairSelectNextIndex(7, 0)).isOne();
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
    void testFairSelectNextIndexWithAllInputsSelected_4() {
        assertThat(InputSelection.ALL.fairSelectNextIndex(7, 0)).isOne();
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
    void testFairSelectNextIndexWithAllInputsSelected_7() {
        assertThat(InputSelection.ALL.fairSelectNextIndex(-1, 63)).isZero();
    }

    @Test
    void testFairSelectNextIndexWithAllInputsSelected_8() {
        assertThat(InputSelection.ALL.fairSelectNextIndex(-1, 158)).isZero();
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
}
