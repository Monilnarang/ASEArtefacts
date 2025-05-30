package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.junit.jupiter.api.Test;

public class RegExUtilsTest_Purified extends AbstractLangTest {

    @Test
    public void testRemoveAll_1() {
        assertNull(RegExUtils.removeAll((CharSequence) null, Pattern.compile("")));
    }

    @Test
    public void testRemoveAll_2() {
        assertEquals("any", RegExUtils.removeAll((CharSequence) "any", (Pattern) null));
    }

    @Test
    public void testRemoveAll_3() {
        assertEquals("any", RegExUtils.removeAll((CharSequence) "any", Pattern.compile("")));
    }

    @Test
    public void testRemoveAll_4() {
        assertEquals("", RegExUtils.removeAll((CharSequence) "any", Pattern.compile(".*")));
    }

    @Test
    public void testRemoveAll_5() {
        assertEquals("", RegExUtils.removeAll((CharSequence) "any", Pattern.compile(".+")));
    }

    @Test
    public void testRemoveAll_6() {
        assertEquals("", RegExUtils.removeAll((CharSequence) "any", Pattern.compile(".?")));
    }

    @Test
    public void testRemoveAll_7() {
        assertEquals("A\nB", RegExUtils.removeAll((CharSequence) "A<__>\n<__>B", Pattern.compile("<.*>")));
    }

    @Test
    public void testRemoveAll_8() {
        assertEquals("AB", RegExUtils.removeAll((CharSequence) "A<__>\n<__>B", Pattern.compile("(?s)<.*>")));
    }

    @Test
    public void testRemoveAll_9() {
        assertEquals("ABC123", RegExUtils.removeAll((CharSequence) "ABCabc123abc", Pattern.compile("[a-z]")));
    }

    @Test
    public void testRemoveAll_10() {
        assertEquals("AB", RegExUtils.removeAll((CharSequence) "A<__>\n<__>B", Pattern.compile("<.*>", Pattern.DOTALL)));
    }

    @Test
    public void testRemoveAll_11() {
        assertEquals("AB", RegExUtils.removeAll((CharSequence) "A<__>\\n<__>B", Pattern.compile("<.*>")));
    }

    @Test
    public void testRemoveAll_12() {
        assertEquals("", RegExUtils.removeAll((CharSequence) "<A>x\\ny</A>", Pattern.compile("<A>.*</A>")));
    }

    @Test
    public void testRemoveAll_13() {
        assertEquals("", RegExUtils.removeAll((CharSequence) "<A>\nxy\n</A>", Pattern.compile("<A>.*</A>", Pattern.DOTALL)));
    }

    @Test
    public void testRemoveAllDeprecated_1() {
        assertNull(RegExUtils.removeAll(null, Pattern.compile("")));
    }

    @Test
    public void testRemoveAllDeprecated_2() {
        assertEquals("any", RegExUtils.removeAll("any", (Pattern) null));
    }

    @Test
    public void testRemoveAllDeprecated_3() {
        assertEquals("any", RegExUtils.removeAll("any", Pattern.compile("")));
    }

    @Test
    public void testRemoveAllDeprecated_4() {
        assertEquals("", RegExUtils.removeAll("any", Pattern.compile(".*")));
    }

    @Test
    public void testRemoveAllDeprecated_5() {
        assertEquals("", RegExUtils.removeAll("any", Pattern.compile(".+")));
    }

    @Test
    public void testRemoveAllDeprecated_6() {
        assertEquals("", RegExUtils.removeAll("any", Pattern.compile(".?")));
    }

    @Test
    public void testRemoveAllDeprecated_7() {
        assertEquals("A\nB", RegExUtils.removeAll("A<__>\n<__>B", Pattern.compile("<.*>")));
    }

    @Test
    public void testRemoveAllDeprecated_8() {
        assertEquals("AB", RegExUtils.removeAll("A<__>\n<__>B", Pattern.compile("(?s)<.*>")));
    }

    @Test
    public void testRemoveAllDeprecated_9() {
        assertEquals("ABC123", RegExUtils.removeAll("ABCabc123abc", Pattern.compile("[a-z]")));
    }

    @Test
    public void testRemoveAllDeprecated_10() {
        assertEquals("AB", RegExUtils.removeAll("A<__>\n<__>B", Pattern.compile("<.*>", Pattern.DOTALL)));
    }

    @Test
    public void testRemoveAllDeprecated_11() {
        assertEquals("AB", RegExUtils.removeAll("A<__>\\n<__>B", Pattern.compile("<.*>")));
    }

    @Test
    public void testRemoveAllDeprecated_12() {
        assertEquals("", RegExUtils.removeAll("<A>x\\ny</A>", Pattern.compile("<A>.*</A>")));
    }

    @Test
    public void testRemoveAllDeprecated_13() {
        assertEquals("", RegExUtils.removeAll("<A>\nxy\n</A>", Pattern.compile("<A>.*</A>", Pattern.DOTALL)));
    }

    @Test
    public void testRemoveFirst_1() {
        assertNull(RegExUtils.removeFirst((CharSequence) null, Pattern.compile("")));
    }

    @Test
    public void testRemoveFirst_2() {
        assertEquals("any", RegExUtils.removeFirst((CharSequence) "any", (Pattern) null));
    }

    @Test
    public void testRemoveFirst_3() {
        assertEquals("any", RegExUtils.removeFirst((CharSequence) "any", Pattern.compile("")));
    }

    @Test
    public void testRemoveFirst_4() {
        assertEquals("", RegExUtils.removeFirst((CharSequence) "any", Pattern.compile(".*")));
    }

    @Test
    public void testRemoveFirst_5() {
        assertEquals("", RegExUtils.removeFirst((CharSequence) "any", Pattern.compile(".+")));
    }

    @Test
    public void testRemoveFirst_6() {
        assertEquals("bc", RegExUtils.removeFirst((CharSequence) "abc", Pattern.compile(".?")));
    }

    @Test
    public void testRemoveFirst_7() {
        assertEquals("A\n<__>B", RegExUtils.removeFirst((CharSequence) "A<__>\n<__>B", Pattern.compile("<.*>")));
    }

    @Test
    public void testRemoveFirst_8() {
        assertEquals("AB", RegExUtils.removeFirst((CharSequence) "A<__>\n<__>B", Pattern.compile("(?s)<.*>")));
    }

    @Test
    public void testRemoveFirst_9() {
        assertEquals("ABCbc123", RegExUtils.removeFirst((CharSequence) "ABCabc123", Pattern.compile("[a-z]")));
    }

    @Test
    public void testRemoveFirst_10() {
        assertEquals("ABC123abc", RegExUtils.removeFirst((CharSequence) "ABCabc123abc", Pattern.compile("[a-z]+")));
    }

    @Test
    public void testRemoveFirstDeprecated_1() {
        assertNull(RegExUtils.removeFirst(null, Pattern.compile("")));
    }

    @Test
    public void testRemoveFirstDeprecated_2() {
        assertEquals("any", RegExUtils.removeFirst("any", (Pattern) null));
    }

    @Test
    public void testRemoveFirstDeprecated_3() {
        assertEquals("any", RegExUtils.removeFirst("any", Pattern.compile("")));
    }

    @Test
    public void testRemoveFirstDeprecated_4() {
        assertEquals("", RegExUtils.removeFirst("any", Pattern.compile(".*")));
    }

    @Test
    public void testRemoveFirstDeprecated_5() {
        assertEquals("", RegExUtils.removeFirst("any", Pattern.compile(".+")));
    }

    @Test
    public void testRemoveFirstDeprecated_6() {
        assertEquals("bc", RegExUtils.removeFirst("abc", Pattern.compile(".?")));
    }

    @Test
    public void testRemoveFirstDeprecated_7() {
        assertEquals("A\n<__>B", RegExUtils.removeFirst("A<__>\n<__>B", Pattern.compile("<.*>")));
    }

    @Test
    public void testRemoveFirstDeprecated_8() {
        assertEquals("AB", RegExUtils.removeFirst("A<__>\n<__>B", Pattern.compile("(?s)<.*>")));
    }

    @Test
    public void testRemoveFirstDeprecated_9() {
        assertEquals("ABCbc123", RegExUtils.removeFirst("ABCabc123", Pattern.compile("[a-z]")));
    }

    @Test
    public void testRemoveFirstDeprecated_10() {
        assertEquals("ABC123abc", RegExUtils.removeFirst("ABCabc123abc", Pattern.compile("[a-z]+")));
    }

    @Test
    public void testRemovePattern_1() {
        assertNull(RegExUtils.removePattern((CharSequence) null, ""));
    }

    @Test
    public void testRemovePattern_2() {
        assertEquals("any", RegExUtils.removePattern((CharSequence) "any", (String) null));
    }

    @Test
    public void testRemovePattern_3() {
        assertEquals("", RegExUtils.removePattern((CharSequence) "", ""));
    }

    @Test
    public void testRemovePattern_4() {
        assertEquals("", RegExUtils.removePattern((CharSequence) "", ".*"));
    }

    @Test
    public void testRemovePattern_5() {
        assertEquals("", RegExUtils.removePattern((CharSequence) "", ".+"));
    }

    @Test
    public void testRemovePattern_6() {
        assertEquals("AB", RegExUtils.removePattern((CharSequence) "A<__>\n<__>B", "<.*>"));
    }

    @Test
    public void testRemovePattern_7() {
        assertEquals("AB", RegExUtils.removePattern((CharSequence) "A<__>\\n<__>B", "<.*>"));
    }

    @Test
    public void testRemovePattern_8() {
        assertEquals("", RegExUtils.removePattern((CharSequence) "<A>x\\ny</A>", "<A>.*</A>"));
    }

    @Test
    public void testRemovePattern_9() {
        assertEquals("", RegExUtils.removePattern((CharSequence) "<A>\nxy\n</A>", "<A>.*</A>"));
    }

    @Test
    public void testRemovePattern_10() {
        assertEquals("ABC123", RegExUtils.removePattern((CharSequence) "ABCabc123", "[a-z]"));
    }

    @Test
    public void testRemovePatternDeprecated_1() {
        assertNull(RegExUtils.removePattern(null, ""));
    }

    @Test
    public void testRemovePatternDeprecated_2() {
        assertEquals("any", RegExUtils.removePattern("any", (String) null));
    }

    @Test
    public void testRemovePatternDeprecated_3() {
        assertEquals("", RegExUtils.removePattern("", ""));
    }

    @Test
    public void testRemovePatternDeprecated_4() {
        assertEquals("", RegExUtils.removePattern("", ".*"));
    }

    @Test
    public void testRemovePatternDeprecated_5() {
        assertEquals("", RegExUtils.removePattern("", ".+"));
    }

    @Test
    public void testRemovePatternDeprecated_6() {
        assertEquals("AB", RegExUtils.removePattern("A<__>\n<__>B", "<.*>"));
    }

    @Test
    public void testRemovePatternDeprecated_7() {
        assertEquals("AB", RegExUtils.removePattern("A<__>\\n<__>B", "<.*>"));
    }

    @Test
    public void testRemovePatternDeprecated_8() {
        assertEquals("", RegExUtils.removePattern("<A>x\\ny</A>", "<A>.*</A>"));
    }

    @Test
    public void testRemovePatternDeprecated_9() {
        assertEquals("", RegExUtils.removePattern("<A>\nxy\n</A>", "<A>.*</A>"));
    }

    @Test
    public void testRemovePatternDeprecated_10() {
        assertEquals("ABC123", RegExUtils.removePattern("ABCabc123", "[a-z]"));
    }

    @Test
    public void testReplaceAll_1() {
        assertNull(RegExUtils.replaceAll((CharSequence) null, Pattern.compile(""), ""));
    }

    @Test
    public void testReplaceAll_2() {
        assertEquals("any", RegExUtils.replaceAll((CharSequence) "any", (Pattern) null, ""));
    }

    @Test
    public void testReplaceAll_3() {
        assertEquals("any", RegExUtils.replaceAll((CharSequence) "any", Pattern.compile(""), null));
    }

    @Test
    public void testReplaceAll_4() {
        assertEquals("zzz", RegExUtils.replaceAll((CharSequence) "", Pattern.compile(""), "zzz"));
    }

    @Test
    public void testReplaceAll_5() {
        assertEquals("zzz", RegExUtils.replaceAll((CharSequence) "", Pattern.compile(".*"), "zzz"));
    }

    @Test
    public void testReplaceAll_6() {
        assertEquals("", RegExUtils.replaceAll((CharSequence) "", Pattern.compile(".+"), "zzz"));
    }

    @Test
    public void testReplaceAll_7() {
        assertEquals("ZZaZZbZZcZZ", RegExUtils.replaceAll((CharSequence) "abc", Pattern.compile(""), "ZZ"));
    }

    @Test
    public void testReplaceAll_8() {
        assertEquals("z\nz", RegExUtils.replaceAll((CharSequence) "<__>\n<__>", Pattern.compile("<.*>"), "z"));
    }

    @Test
    public void testReplaceAll_9() {
        assertEquals("z", RegExUtils.replaceAll((CharSequence) "<__>\n<__>", Pattern.compile("(?s)<.*>"), "z"));
    }

    @Test
    public void testReplaceAll_10() {
        assertEquals("z", RegExUtils.replaceAll((CharSequence) "<__>\n<__>", Pattern.compile("<.*>", Pattern.DOTALL), "z"));
    }

    @Test
    public void testReplaceAll_11() {
        assertEquals("z", RegExUtils.replaceAll((CharSequence) "<__>\\n<__>", Pattern.compile("<.*>"), "z"));
    }

    @Test
    public void testReplaceAll_12() {
        assertEquals("X", RegExUtils.replaceAll((CharSequence) "<A>\nxy\n</A>", Pattern.compile("<A>.*</A>", Pattern.DOTALL), "X"));
    }

    @Test
    public void testReplaceAll_13() {
        assertEquals("ABC___123", RegExUtils.replaceAll((CharSequence) "ABCabc123", Pattern.compile("[a-z]"), "_"));
    }

    @Test
    public void testReplaceAll_14() {
        assertEquals("ABC_123", RegExUtils.replaceAll((CharSequence) "ABCabc123", Pattern.compile("[^A-Z0-9]+"), "_"));
    }

    @Test
    public void testReplaceAll_15() {
        assertEquals("ABC123", RegExUtils.replaceAll((CharSequence) "ABCabc123", Pattern.compile("[^A-Z0-9]+"), ""));
    }

    @Test
    public void testReplaceAll_16() {
        assertEquals("Lorem_ipsum_dolor_sit", RegExUtils.replaceAll((CharSequence) "Lorem ipsum  dolor   sit", Pattern.compile("( +)([a-z]+)"), "_$2"));
    }

    @Test
    public void testReplaceAllDeprecated_1() {
        assertNull(RegExUtils.replaceAll(null, Pattern.compile(""), ""));
    }

    @Test
    public void testReplaceAllDeprecated_2() {
        assertEquals("any", RegExUtils.replaceAll("any", (Pattern) null, ""));
    }

    @Test
    public void testReplaceAllDeprecated_3() {
        assertEquals("any", RegExUtils.replaceAll("any", Pattern.compile(""), null));
    }

    @Test
    public void testReplaceAllDeprecated_4() {
        assertEquals("zzz", RegExUtils.replaceAll("", Pattern.compile(""), "zzz"));
    }

    @Test
    public void testReplaceAllDeprecated_5() {
        assertEquals("zzz", RegExUtils.replaceAll("", Pattern.compile(".*"), "zzz"));
    }

    @Test
    public void testReplaceAllDeprecated_6() {
        assertEquals("", RegExUtils.replaceAll("", Pattern.compile(".+"), "zzz"));
    }

    @Test
    public void testReplaceAllDeprecated_7() {
        assertEquals("ZZaZZbZZcZZ", RegExUtils.replaceAll("abc", Pattern.compile(""), "ZZ"));
    }

    @Test
    public void testReplaceAllDeprecated_8() {
        assertEquals("z\nz", RegExUtils.replaceAll("<__>\n<__>", Pattern.compile("<.*>"), "z"));
    }

    @Test
    public void testReplaceAllDeprecated_9() {
        assertEquals("z", RegExUtils.replaceAll("<__>\n<__>", Pattern.compile("(?s)<.*>"), "z"));
    }

    @Test
    public void testReplaceAllDeprecated_10() {
        assertEquals("z", RegExUtils.replaceAll("<__>\n<__>", Pattern.compile("<.*>", Pattern.DOTALL), "z"));
    }

    @Test
    public void testReplaceAllDeprecated_11() {
        assertEquals("z", RegExUtils.replaceAll("<__>\\n<__>", Pattern.compile("<.*>"), "z"));
    }

    @Test
    public void testReplaceAllDeprecated_12() {
        assertEquals("X", RegExUtils.replaceAll("<A>\nxy\n</A>", Pattern.compile("<A>.*</A>", Pattern.DOTALL), "X"));
    }

    @Test
    public void testReplaceAllDeprecated_13() {
        assertEquals("ABC___123", RegExUtils.replaceAll("ABCabc123", Pattern.compile("[a-z]"), "_"));
    }

    @Test
    public void testReplaceAllDeprecated_14() {
        assertEquals("ABC_123", RegExUtils.replaceAll("ABCabc123", Pattern.compile("[^A-Z0-9]+"), "_"));
    }

    @Test
    public void testReplaceAllDeprecated_15() {
        assertEquals("ABC123", RegExUtils.replaceAll("ABCabc123", Pattern.compile("[^A-Z0-9]+"), ""));
    }

    @Test
    public void testReplaceAllDeprecated_16() {
        assertEquals("Lorem_ipsum_dolor_sit", RegExUtils.replaceAll("Lorem ipsum  dolor   sit", Pattern.compile("( +)([a-z]+)"), "_$2"));
    }

    @Test
    public void testReplaceFirst_1() {
        assertNull(RegExUtils.replaceFirst((CharSequence) null, Pattern.compile(""), ""));
    }

    @Test
    public void testReplaceFirst_2() {
        assertEquals("any", RegExUtils.replaceFirst((CharSequence) "any", (Pattern) null, ""));
    }

    @Test
    public void testReplaceFirst_3() {
        assertEquals("any", RegExUtils.replaceFirst((CharSequence) "any", Pattern.compile(""), null));
    }

    @Test
    public void testReplaceFirst_4() {
        assertEquals("zzz", RegExUtils.replaceFirst((CharSequence) "", Pattern.compile(""), "zzz"));
    }

    @Test
    public void testReplaceFirst_5() {
        assertEquals("zzz", RegExUtils.replaceFirst((CharSequence) "", Pattern.compile(".*"), "zzz"));
    }

    @Test
    public void testReplaceFirst_6() {
        assertEquals("", RegExUtils.replaceFirst((CharSequence) "", Pattern.compile(".+"), "zzz"));
    }

    @Test
    public void testReplaceFirst_7() {
        assertEquals("ZZabc", RegExUtils.replaceFirst((CharSequence) "abc", Pattern.compile(""), "ZZ"));
    }

    @Test
    public void testReplaceFirst_8() {
        assertEquals("z\n<__>", RegExUtils.replaceFirst((CharSequence) "<__>\n<__>", Pattern.compile("<.*>"), "z"));
    }

    @Test
    public void testReplaceFirst_9() {
        assertEquals("z", RegExUtils.replaceFirst((CharSequence) "<__>\n<__>", Pattern.compile("(?s)<.*>"), "z"));
    }

    @Test
    public void testReplaceFirst_10() {
        assertEquals("ABC_bc123", RegExUtils.replaceFirst((CharSequence) "ABCabc123", Pattern.compile("[a-z]"), "_"));
    }

    @Test
    public void testReplaceFirst_11() {
        assertEquals("ABC_123abc", RegExUtils.replaceFirst((CharSequence) "ABCabc123abc", Pattern.compile("[^A-Z0-9]+"), "_"));
    }

    @Test
    public void testReplaceFirst_12() {
        assertEquals("ABC123abc", RegExUtils.replaceFirst((CharSequence) "ABCabc123abc", Pattern.compile("[^A-Z0-9]+"), ""));
    }

    @Test
    public void testReplaceFirst_13() {
        assertEquals("Lorem_ipsum  dolor   sit", RegExUtils.replaceFirst((CharSequence) "Lorem ipsum  dolor   sit", Pattern.compile("( +)([a-z]+)"), "_$2"));
    }

    @Test
    public void testReplaceFirstDeprecated_1() {
        assertNull(RegExUtils.replaceFirst(null, Pattern.compile(""), ""));
    }

    @Test
    public void testReplaceFirstDeprecated_2() {
        assertEquals("any", RegExUtils.replaceFirst("any", (Pattern) null, ""));
    }

    @Test
    public void testReplaceFirstDeprecated_3() {
        assertEquals("any", RegExUtils.replaceFirst("any", Pattern.compile(""), null));
    }

    @Test
    public void testReplaceFirstDeprecated_4() {
        assertEquals("zzz", RegExUtils.replaceFirst("", Pattern.compile(""), "zzz"));
    }

    @Test
    public void testReplaceFirstDeprecated_5() {
        assertEquals("zzz", RegExUtils.replaceFirst("", Pattern.compile(".*"), "zzz"));
    }

    @Test
    public void testReplaceFirstDeprecated_6() {
        assertEquals("", RegExUtils.replaceFirst("", Pattern.compile(".+"), "zzz"));
    }

    @Test
    public void testReplaceFirstDeprecated_7() {
        assertEquals("ZZabc", RegExUtils.replaceFirst("abc", Pattern.compile(""), "ZZ"));
    }

    @Test
    public void testReplaceFirstDeprecated_8() {
        assertEquals("z\n<__>", RegExUtils.replaceFirst("<__>\n<__>", Pattern.compile("<.*>"), "z"));
    }

    @Test
    public void testReplaceFirstDeprecated_9() {
        assertEquals("z", RegExUtils.replaceFirst("<__>\n<__>", Pattern.compile("(?s)<.*>"), "z"));
    }

    @Test
    public void testReplaceFirstDeprecated_10() {
        assertEquals("ABC_bc123", RegExUtils.replaceFirst("ABCabc123", Pattern.compile("[a-z]"), "_"));
    }

    @Test
    public void testReplaceFirstDeprecated_11() {
        assertEquals("ABC_123abc", RegExUtils.replaceFirst("ABCabc123abc", Pattern.compile("[^A-Z0-9]+"), "_"));
    }

    @Test
    public void testReplaceFirstDeprecated_12() {
        assertEquals("ABC123abc", RegExUtils.replaceFirst("ABCabc123abc", Pattern.compile("[^A-Z0-9]+"), ""));
    }

    @Test
    public void testReplaceFirstDeprecated_13() {
        assertEquals("Lorem_ipsum  dolor   sit", RegExUtils.replaceFirst("Lorem ipsum  dolor   sit", Pattern.compile("( +)([a-z]+)"), "_$2"));
    }

    @Test
    public void testReplacePattern_1() {
        assertNull(RegExUtils.replacePattern((CharSequence) null, "", ""));
    }

    @Test
    public void testReplacePattern_2() {
        assertEquals("any", RegExUtils.replacePattern((CharSequence) "any", (String) null, ""));
    }

    @Test
    public void testReplacePattern_3() {
        assertEquals("any", RegExUtils.replacePattern((CharSequence) "any", "", null));
    }

    @Test
    public void testReplacePattern_4() {
        assertEquals("zzz", RegExUtils.replacePattern((CharSequence) "", "", "zzz"));
    }

    @Test
    public void testReplacePattern_5() {
        assertEquals("zzz", RegExUtils.replacePattern((CharSequence) "", ".*", "zzz"));
    }

    @Test
    public void testReplacePattern_6() {
        assertEquals("", RegExUtils.replacePattern((CharSequence) "", ".+", "zzz"));
    }

    @Test
    public void testReplacePattern_7() {
        assertEquals("z", RegExUtils.replacePattern((CharSequence) "<__>\n<__>", "<.*>", "z"));
    }

    @Test
    public void testReplacePattern_8() {
        assertEquals("z", RegExUtils.replacePattern((CharSequence) "<__>\\n<__>", "<.*>", "z"));
    }

    @Test
    public void testReplacePattern_9() {
        assertEquals("X", RegExUtils.replacePattern((CharSequence) "<A>\nxy\n</A>", "<A>.*</A>", "X"));
    }

    @Test
    public void testReplacePattern_10() {
        assertEquals("ABC___123", RegExUtils.replacePattern((CharSequence) "ABCabc123", "[a-z]", "_"));
    }

    @Test
    public void testReplacePattern_11() {
        assertEquals("ABC_123", RegExUtils.replacePattern((CharSequence) "ABCabc123", "[^A-Z0-9]+", "_"));
    }

    @Test
    public void testReplacePattern_12() {
        assertEquals("ABC123", RegExUtils.replacePattern((CharSequence) "ABCabc123", "[^A-Z0-9]+", ""));
    }

    @Test
    public void testReplacePattern_13() {
        assertEquals("Lorem_ipsum_dolor_sit", RegExUtils.replacePattern((CharSequence) "Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2"));
    }

    @Test
    public void testReplacePatternDeprecated_1() {
        assertNull(RegExUtils.replacePattern(null, "", ""));
    }

    @Test
    public void testReplacePatternDeprecated_2() {
        assertEquals("any", RegExUtils.replacePattern("any", (String) null, ""));
    }

    @Test
    public void testReplacePatternDeprecated_3() {
        assertEquals("any", RegExUtils.replacePattern("any", "", null));
    }

    @Test
    public void testReplacePatternDeprecated_4() {
        assertEquals("zzz", RegExUtils.replacePattern("", "", "zzz"));
    }

    @Test
    public void testReplacePatternDeprecated_5() {
        assertEquals("zzz", RegExUtils.replacePattern("", ".*", "zzz"));
    }

    @Test
    public void testReplacePatternDeprecated_6() {
        assertEquals("", RegExUtils.replacePattern("", ".+", "zzz"));
    }

    @Test
    public void testReplacePatternDeprecated_7() {
        assertEquals("z", RegExUtils.replacePattern("<__>\n<__>", "<.*>", "z"));
    }

    @Test
    public void testReplacePatternDeprecated_8() {
        assertEquals("z", RegExUtils.replacePattern("<__>\\n<__>", "<.*>", "z"));
    }

    @Test
    public void testReplacePatternDeprecated_9() {
        assertEquals("X", RegExUtils.replacePattern("<A>\nxy\n</A>", "<A>.*</A>", "X"));
    }

    @Test
    public void testReplacePatternDeprecated_10() {
        assertEquals("ABC___123", RegExUtils.replacePattern("ABCabc123", "[a-z]", "_"));
    }

    @Test
    public void testReplacePatternDeprecated_11() {
        assertEquals("ABC_123", RegExUtils.replacePattern("ABCabc123", "[^A-Z0-9]+", "_"));
    }

    @Test
    public void testReplacePatternDeprecated_12() {
        assertEquals("ABC123", RegExUtils.replacePattern("ABCabc123", "[^A-Z0-9]+", ""));
    }

    @Test
    public void testReplacePatternDeprecated_13() {
        assertEquals("Lorem_ipsum_dolor_sit", RegExUtils.replacePattern("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2"));
    }
}
