package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class NotImplementedExceptionTest_Purified extends AbstractLangTest {

    private void assertCorrect(final String assertMessage, final NotImplementedException nie, final String message, final Throwable nested, final String code) {
        assertNotNull(nie, assertMessage + ": target is null");
        assertEquals(message, nie.getMessage(), assertMessage + ": Message not equal");
        assertEquals(nested, nie.getCause(), assertMessage + ": Nested throwable not equal");
        assertEquals(code, nie.getCode(), assertMessage + ": Code not equal");
    }

    @Test
    public void testConstructors_1_testMerged_1() {
        final Throwable nested = new RuntimeException();
        final String message = "Not Implemented";
        final String code = "CODE";
        NotImplementedException nie = new NotImplementedException(message);
        assertCorrect("Issue in (String)", nie, message, null, null);
        nie = new NotImplementedException(nested);
        assertCorrect("Issue in (Throwable)", nie, nested.toString(), nested, null);
        nie = new NotImplementedException(message, nested);
        assertCorrect("Issue in (String, Throwable)", nie, message, nested, null);
        nie = new NotImplementedException(message, code);
        assertCorrect("Issue in (String, String)", nie, message, null, code);
        nie = new NotImplementedException(nested, code);
        assertCorrect("Issue in (Throwable, String)", nie, nested.toString(), nested, code);
        nie = new NotImplementedException(message, nested, code);
        assertCorrect("Issue in (String, Throwable, String)", nie, message, nested, code);
    }

    @Test
    public void testConstructors_7() {
        assertNull(new NotImplementedException().getCode());
    }
}
