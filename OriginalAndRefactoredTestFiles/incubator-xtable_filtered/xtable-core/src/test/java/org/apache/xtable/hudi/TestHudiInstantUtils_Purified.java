package org.apache.xtable.hudi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Instant;
import org.junit.jupiter.api.Test;

public class TestHudiInstantUtils_Purified {

    @Test
    public void testParseCommitTimeToInstant_1() {
        assertEquals(Instant.parse("2023-01-20T04:43:31.843Z"), HudiInstantUtils.parseFromInstantTime("20230120044331843"));
    }

    @Test
    public void testParseCommitTimeToInstant_2() {
        assertEquals(Instant.parse("2023-01-20T04:43:31.999Z"), HudiInstantUtils.parseFromInstantTime("20230120044331"));
    }

    @Test
    public void testInstantToCommit_1() {
        assertEquals("20230120044331843", HudiInstantUtils.convertInstantToCommit(Instant.parse("2023-01-20T04:43:31.843Z")));
    }

    @Test
    public void testInstantToCommit_2() {
        assertEquals("20230120044331000", HudiInstantUtils.convertInstantToCommit(Instant.parse("2023-01-20T04:43:31Z")));
    }
}
