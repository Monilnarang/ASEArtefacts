package org.eclipse.collections.impl.block.procedure;

import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.utility.Iterate;
import org.eclipse.collections.impl.utility.StringIterate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChainedProcedureTest_Purified {

    @Test
    public void toStringTest_1_testMerged_1() {
        Procedure<String> procedure = new CollectionAddProcedure<>(Lists.mutable.of());
        String s = procedure.toString();
        assertNotNull(s);
        assertTrue(StringIterate.notEmptyOrWhitespace(s));
    }

    @Test
    public void toStringTest_3() {
        Verify.assertContains("ChainedProcedure.with", new ChainedProcedure<>().toString());
    }
}
