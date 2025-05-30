package org.eclipse.collections.impl.list.fixed;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Procedures2;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FixedSizeListFactoryTest_Purified {

    @Test
    public void createList_singleton_1() {
        Verify.assertEmpty(Lists.fixedSize.of());
    }

    @Test
    public void createList_singleton_2() {
        assertSame(Lists.fixedSize.of(), Lists.fixedSize.of());
    }
}
