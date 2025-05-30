package org.apache.commons.configuration2;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Deque;
import java.util.Iterator;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.convert.LegacyListDelimiterHandler;
import org.apache.commons.configuration2.event.ConfigurationEvent;
import org.apache.commons.configuration2.event.EventListener;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPropertiesConfigurationLayout_Purified {

    static class LayoutTestConfiguration extends PropertiesConfiguration {

        private PropertiesBuilder builder;

        @Override
        boolean propertyLoaded(final String key, final String value, final Deque<URL> seenStack) throws ConfigurationException {
            if (builder == null) {
                return super.propertyLoaded(key, value, seenStack);
            }
            if (PropertiesConfiguration.getInclude().equals(key)) {
                getLayout().load(this, builder.getReader());
                return false;
            }
            return true;
        }
    }

    static class PropertiesBuilder {

        private final StringBuilder buf = new StringBuilder();

        private int commentCounter;

        public void addComment(final String s) {
            if (s != null) {
                if (commentCounter % 2 == 0) {
                    buf.append("# ");
                } else {
                    buf.append("! ");
                }
                buf.append(s);
                commentCounter++;
            }
            buf.append(CR);
        }

        public void addLine(final String s) {
            buf.append(s).append(CR);
        }

        public void addProperty(final String key, final String value) {
            buf.append(key).append(" = ").append(value).append(CR);
        }

        public Reader getReader() {
            return new StringReader(buf.toString());
        }

        @Override
        public String toString() {
            return buf.toString();
        }
    }

    private static final String CR = System.lineSeparator();

    private static final String CRNORM = "\n";

    private static final String TEST_KEY = "myProperty";

    private static final String TEST_COMMENT = "A comment for my test property";

    private static final String TEST_VALUE = "myPropertyValue";

    private PropertiesConfigurationLayout layout;

    private LayoutTestConfiguration config;

    private PropertiesBuilder builder;

    private void checkLayoutString(final String expected) throws ConfigurationException {
        assertEquals(expected, getLayoutString());
    }

    private void fillLayout() {
        builder.addComment("A header comment");
        builder.addComment(null);
        builder.addProperty("prop", "value");
        builder.addComment(TEST_COMMENT);
        builder.addProperty(TEST_KEY, TEST_VALUE);
        builder.addProperty("anotherProp", "anotherValue");
        builder.addComment("A footer comment");
        assertDoesNotThrow(() -> layout.load(config, builder.getReader()));
    }

    private String getLayoutString() throws ConfigurationException {
        final StringWriter out = new StringWriter();
        layout.save(config, out);
        return out.toString();
    }

    @BeforeEach
    public void setUp() throws Exception {
        config = new LayoutTestConfiguration();
        config.setListDelimiterHandler(new LegacyListDelimiterHandler(','));
        layout = new PropertiesConfigurationLayout();
        config.setLayout(layout);
        builder = new PropertiesBuilder();
    }

    @Test
    public void testGetNonExistingLayouData_1() {
        assertNull(layout.getComment("unknown"));
    }

    @Test
    public void testGetNonExistingLayouData_2() {
        assertTrue(layout.isSingleLine("unknown"));
    }

    @Test
    public void testGetNonExistingLayouData_3() {
        assertEquals(0, layout.getBlankLinesBefore("unknown"));
    }

    @Test
    public void testHeaderCommentNull_1() {
        assertNull(layout.getCanonicalHeaderComment(true));
    }

    @Test
    public void testHeaderCommentNull_2() {
        assertNull(layout.getCanonicalHeaderComment(false));
    }
}
