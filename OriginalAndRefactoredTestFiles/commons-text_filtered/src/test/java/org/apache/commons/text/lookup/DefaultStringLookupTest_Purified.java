package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertSame;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class DefaultStringLookupTest_Purified {

    @Test
    public void testIndividualEnums_1() {
        assertSame(DefaultStringLookup.BASE64_DECODER.getStringLookup(), StringLookupFactory.INSTANCE.base64DecoderStringLookup());
    }

    @Test
    public void testIndividualEnums_2() {
        assertSame(DefaultStringLookup.BASE64_ENCODER.getStringLookup(), StringLookupFactory.INSTANCE.base64EncoderStringLookup());
    }

    @Test
    public void testIndividualEnums_3() {
        assertSame(DefaultStringLookup.CONST.getStringLookup(), StringLookupFactory.INSTANCE.constantStringLookup());
    }

    @Test
    public void testIndividualEnums_4() {
        assertSame(DefaultStringLookup.DATE.getStringLookup(), StringLookupFactory.INSTANCE.dateStringLookup());
    }

    @Test
    public void testIndividualEnums_5() {
        assertSame(DefaultStringLookup.DNS.getStringLookup(), StringLookupFactory.INSTANCE.dnsStringLookup());
    }

    @Test
    public void testIndividualEnums_6() {
        assertSame(DefaultStringLookup.ENVIRONMENT.getStringLookup(), StringLookupFactory.INSTANCE.environmentVariableStringLookup());
    }

    @Test
    public void testIndividualEnums_7() {
        assertSame(DefaultStringLookup.FILE.getStringLookup(), StringLookupFactory.INSTANCE.fileStringLookup());
    }

    @Test
    public void testIndividualEnums_8() {
        assertSame(DefaultStringLookup.JAVA.getStringLookup(), StringLookupFactory.INSTANCE.javaPlatformStringLookup());
    }

    @Test
    public void testIndividualEnums_9() {
        assertSame(DefaultStringLookup.LOCAL_HOST.getStringLookup(), StringLookupFactory.INSTANCE.localHostStringLookup());
    }

    @Test
    public void testIndividualEnums_10() {
        assertSame(DefaultStringLookup.PROPERTIES.getStringLookup(), StringLookupFactory.INSTANCE.propertiesStringLookup());
    }

    @Test
    public void testIndividualEnums_11() {
        assertSame(DefaultStringLookup.RESOURCE_BUNDLE.getStringLookup(), StringLookupFactory.INSTANCE.resourceBundleStringLookup());
    }

    @Test
    public void testIndividualEnums_12() {
        assertSame(DefaultStringLookup.SCRIPT.getStringLookup(), StringLookupFactory.INSTANCE.scriptStringLookup());
    }

    @Test
    public void testIndividualEnums_13() {
        assertSame(DefaultStringLookup.SYSTEM_PROPERTIES.getStringLookup(), StringLookupFactory.INSTANCE.systemPropertyStringLookup());
    }

    @Test
    public void testIndividualEnums_14() {
        assertSame(DefaultStringLookup.URL.getStringLookup(), StringLookupFactory.INSTANCE.urlStringLookup());
    }

    @Test
    public void testIndividualEnums_15() {
        assertSame(DefaultStringLookup.URL_DECODER.getStringLookup(), StringLookupFactory.INSTANCE.urlDecoderStringLookup());
    }

    @Test
    public void testIndividualEnums_16() {
        assertSame(DefaultStringLookup.URL_ENCODER.getStringLookup(), StringLookupFactory.INSTANCE.urlEncoderStringLookup());
    }

    @Test
    public void testIndividualEnums_17() {
        assertSame(DefaultStringLookup.XML.getStringLookup(), StringLookupFactory.INSTANCE.xmlStringLookup());
    }
}
