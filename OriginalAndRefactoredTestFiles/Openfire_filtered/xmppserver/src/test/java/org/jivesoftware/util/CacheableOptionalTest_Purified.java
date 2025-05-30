package org.jivesoftware.util;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class CacheableOptionalTest_Purified {

    private int calculateCachedSize(CacheableOptional<?> co) throws IOException {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(co);
        return os.size();
    }

    @Test
    public void willCorrectlyRecordPresenceAndAbsence_1() {
        assertThat(CacheableOptional.of("my-test").isPresent(), is(true));
    }

    @Test
    public void willCorrectlyRecordPresenceAndAbsence_2() {
        assertThat(CacheableOptional.of(null).isAbsent(), is(true));
    }

    @Test
    public void equalsIsAppropriate_1() {
        assertThat(CacheableOptional.of("my-test"), is(CacheableOptional.of("my-test")));
    }

    @Test
    public void equalsIsAppropriate_2() {
        assertThat(CacheableOptional.of("my-test"), is(not(CacheableOptional.of("not-my-test"))));
    }

    @Test
    public void hashCodeIsAppropriate_1() {
        assertThat(CacheableOptional.of("my-test").hashCode(), is(CacheableOptional.of("my-test").hashCode()));
    }

    @Test
    public void hashCodeIsAppropriate_2() {
        assertThat(CacheableOptional.of("my-test").hashCode(), is(not(CacheableOptional.of("not-my-test").hashCode())));
    }
}
