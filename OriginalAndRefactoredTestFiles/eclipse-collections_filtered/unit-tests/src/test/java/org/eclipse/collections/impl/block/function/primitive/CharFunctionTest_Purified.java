package org.eclipse.collections.impl.block.function.primitive;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Deprecated
public class CharFunctionTest_Purified {

    @Test
    public void toUppercase_1() {
        assertEquals('A', CharFunction.TO_UPPERCASE.valueOf('a'));
    }

    @Test
    public void toUppercase_2() {
        assertEquals('A', CharFunction.TO_UPPERCASE.valueOf('A'));
    }

    @Test
    public void toUppercase_3() {
        assertEquals('1', CharFunction.TO_UPPERCASE.valueOf('1'));
    }

    @Test
    public void toLowercase_1() {
        assertEquals('a', CharFunction.TO_LOWERCASE.valueOf('a'));
    }

    @Test
    public void toLowercase_2() {
        assertEquals('a', CharFunction.TO_LOWERCASE.valueOf('A'));
    }

    @Test
    public void toLowercase_3() {
        assertEquals('1', CharFunction.TO_LOWERCASE.valueOf('1'));
    }
}
