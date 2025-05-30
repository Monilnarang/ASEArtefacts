package org.apache.xtable.hudi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestHudiInstantUtils_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testParseCommitTimeToInstant_1to2")
    public void testParseCommitTimeToInstant_1to2(String param1, double param2) {
        assertEquals(Instant.parse(param1), HudiInstantUtils.parseFromInstantTime(param2));
    }

    static public Stream<Arguments> Provider_testParseCommitTimeToInstant_1to2() {
        return Stream.of(arguments("2023-01-20T04:43:31.843Z", 20230120044331843), arguments("2023-01-20T04:43:31.999Z", 20230120044331));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInstantToCommit_1to2")
    public void testInstantToCommit_1to2(double param1, String param2) {
        assertEquals(param1, HudiInstantUtils.convertInstantToCommit(Instant.parse(param2)));
    }

    static public Stream<Arguments> Provider_testInstantToCommit_1to2() {
        return Stream.of(arguments(20230120044331843, "2023-01-20T04:43:31.843Z"), arguments(20230120044331000, "2023-01-20T04:43:31Z"));
    }
}
