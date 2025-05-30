package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public final class TextHelpAppendableTest_Purified {

    private StringBuilder sb;

    private TextHelpAppendable underTest;

    @BeforeEach
    public void setUp() {
        sb = new StringBuilder();
        underTest = new TextHelpAppendable(sb);
    }

    @Test
    public void testSetIndent_1() {
        assertEquals(TextHelpAppendable.DEFAULT_INDENT, underTest.getIndent(), "Default indent value was changed, some tests may fail");
    }

    @Test
    public void testSetIndent_2() {
        underTest.setIndent(TextHelpAppendable.DEFAULT_INDENT + 2);
        assertEquals(underTest.getIndent(), TextHelpAppendable.DEFAULT_INDENT + 2);
    }
}
