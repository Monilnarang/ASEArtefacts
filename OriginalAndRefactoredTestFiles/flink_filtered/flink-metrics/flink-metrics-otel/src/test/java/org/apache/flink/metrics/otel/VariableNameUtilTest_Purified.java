package org.apache.flink.metrics.otel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class VariableNameUtilTest_Purified {

    @Test
    void getVariableName_1() {
        Assertions.assertEquals("t", VariableNameUtil.getVariableName("<t>"));
    }

    @Test
    void getVariableName_2() {
        Assertions.assertEquals("<t", VariableNameUtil.getVariableName("<t"));
    }

    @Test
    void getVariableName_3() {
        Assertions.assertEquals("t>", VariableNameUtil.getVariableName("t>"));
    }

    @Test
    void getVariableName_4() {
        Assertions.assertEquals("<", VariableNameUtil.getVariableName("<"));
    }

    @Test
    void getVariableName_5() {
        Assertions.assertEquals(">", VariableNameUtil.getVariableName(">"));
    }

    @Test
    void getVariableName_6() {
        Assertions.assertEquals("", VariableNameUtil.getVariableName("<>"));
    }

    @Test
    void getVariableName_7() {
        Assertions.assertEquals("", VariableNameUtil.getVariableName(""));
    }
}
