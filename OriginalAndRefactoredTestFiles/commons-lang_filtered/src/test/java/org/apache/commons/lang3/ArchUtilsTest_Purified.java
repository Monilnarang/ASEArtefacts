package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.lang3.arch.Processor;
import org.apache.commons.lang3.arch.Processor.Arch;
import org.apache.commons.lang3.arch.Processor.Type;
import org.junit.jupiter.api.Test;

public class ArchUtilsTest_Purified extends AbstractLangTest {

    private static final String IA64 = "ia64";

    private static final String IA64_32 = "ia64_32";

    private static final String PPC = "ppc";

    private static final String PPC64 = "ppc64";

    private static final String X86 = "x86";

    private static final String X86_64 = "x86_64";

    private static final String AARCH_64 = "aarch64";

    private static final String RISCV_64 = "riscv64";

    private static final String RISCV_32 = "riscv32";

    private void assertEqualsArchNotNull(final Processor.Arch arch, final Processor processor) {
        assertNotNull(arch);
        assertNotNull(processor);
        assertEquals(arch, processor.getArch());
    }

    private void assertEqualsTypeNotNull(final Processor.Type type, final Processor processor) {
        assertNotNull(type);
        assertNotNull(processor);
        assertEquals(type, processor.getType());
    }

    private void assertNotEqualsArchNotNull(final Processor.Arch arch, final Processor processor) {
        assertNotNull(arch);
        assertNotNull(processor);
        assertNotEquals(arch, processor.getArch());
    }

    private void assertNotEqualsTypeNotNull(final Processor.Type type, final Processor processor) {
        assertNotNull(type);
        assertNotNull(processor);
        assertNotEquals(type, processor.getType());
    }

    @Test
    public void testGetProcessor_1() {
        assertNotNull(ArchUtils.getProcessor(X86));
    }

    @Test
    public void testGetProcessor_2() {
        assertNull(ArchUtils.getProcessor("NA"));
    }

    @Test
    public void testGetProcessor_3() {
        assertNull(ArchUtils.getProcessor(null));
    }

    @Test
    public void testGetProcessor_4() {
        final Processor processor = ArchUtils.getProcessor();
        assertNotEquals(ObjectUtils.identityToString(processor), processor.toString());
    }
}
