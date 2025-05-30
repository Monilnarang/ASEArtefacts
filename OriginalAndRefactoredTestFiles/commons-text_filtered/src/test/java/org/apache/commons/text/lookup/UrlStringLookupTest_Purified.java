package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UrlStringLookupTest_Purified {

    @Test
    public void testHttpScheme_1() {
        Assertions.assertNotNull(UrlStringLookup.INSTANCE.lookup("UTF-8:https://www.apache.org"));
    }

    @Test
    public void testHttpScheme_2() {
        Assertions.assertNotNull(UrlStringLookup.INSTANCE.lookup("UTF-8:https://www.google.com"));
    }
}
