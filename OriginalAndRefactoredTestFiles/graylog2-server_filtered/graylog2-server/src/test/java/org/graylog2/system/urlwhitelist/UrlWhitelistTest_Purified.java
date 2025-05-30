package org.graylog2.system.urlwhitelist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.google.common.collect.ImmutableList;
import org.junit.Test;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class UrlWhitelistTest_Purified {

    @Test
    public void isWhitelisted_1() {
        assertThat(UrlWhitelist.createEnabled(ImmutableList.of(LiteralWhitelistEntry.create("a", "title", "foo"))).isWhitelisted(".foo")).isFalse();
    }

    @Test
    public void isWhitelisted_2() {
        assertThat(UrlWhitelist.createEnabled(ImmutableList.of(RegexWhitelistEntry.create("b", "title", "foo"))).isWhitelisted(".foo")).isTrue();
    }

    @Test
    public void isWhitelisted_3() {
        assertThat(UrlWhitelist.createEnabled(ImmutableList.of(RegexWhitelistEntry.create("c", "title", "^foo$"))).isWhitelisted(".foo")).isFalse();
    }

    @Test
    public void isWhitelisted_4() {
        assertThat(UrlWhitelist.createEnabled(ImmutableList.of(LiteralWhitelistEntry.create("d", "title", ".foo"))).isWhitelisted(".foo")).isTrue();
    }

    @Test
    public void isDisabled_1() {
        assertThat(UrlWhitelist.create(Collections.emptyList(), false).isWhitelisted("test")).isFalse();
    }

    @Test
    public void isDisabled_2() {
        assertThat(UrlWhitelist.create(Collections.emptyList(), true).isWhitelisted("test")).isTrue();
    }
}
