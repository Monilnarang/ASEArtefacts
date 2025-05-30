package io.undertow.util;

import io.undertow.testutils.category.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@Category(UnitTest.class)
public class HttpStringTestCase_Purified {

    @Test
    public void testCompareShorterFirst_1() {
        HttpString accept = new HttpString(Headers.ACCEPT_STRING);
        Assert.assertEquals(accept.compareTo(Headers.ACCEPT_CHARSET), Headers.ACCEPT.compareTo(Headers.ACCEPT_CHARSET));
    }

    @Test
    public void testCompareShorterFirst_2() {
        HttpString acceptCharset = new HttpString(Headers.ACCEPT_CHARSET_STRING);
        Assert.assertEquals(acceptCharset.compareTo(Headers.ACCEPT), Headers.ACCEPT_CHARSET.compareTo(Headers.ACCEPT));
    }

    @Test
    public void testCompare_1() {
        HttpString contentType = new HttpString(Headers.CONTENT_TYPE_STRING);
        Assert.assertEquals(contentType.compareTo(Headers.COOKIE), Headers.CONTENT_TYPE.compareTo(Headers.COOKIE));
    }

    @Test
    public void testCompare_2() {
        HttpString cookie = new HttpString(Headers.COOKIE_STRING);
        Assert.assertEquals(cookie.compareTo(Headers.CONTENT_TYPE), Headers.COOKIE.compareTo(Headers.CONTENT_TYPE));
    }
}
