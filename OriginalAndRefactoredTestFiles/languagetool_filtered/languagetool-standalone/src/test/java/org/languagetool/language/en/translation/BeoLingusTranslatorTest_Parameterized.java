package org.languagetool.language.en.translation;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.languagetool.GlobalConfig;
import org.languagetool.rules.en.translation.BeoLingusTranslator;
import org.languagetool.rules.translation.TranslationEntry;
import java.io.File;
import java.io.IOException;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class BeoLingusTranslatorTest_Parameterized {

    private BeoLingusTranslator translator;

    @Before
    public void init() throws IOException {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setBeolingusFile(new File("src/test/resources/beolingus_test.txt"));
        translator = BeoLingusTranslator.getInstance(globalConfig);
    }

    @ParameterizedTest
    @MethodSource("Provider_testSplit_1to7")
    public void testSplit_1to7(String param1, String param2) {
        assertThat(translator.split(param2).toString(), is(param1));
    }

    static public Stream<Arguments> Provider_testSplit_1to7() {
        return Stream.of(arguments("[foo]", "foo"), arguments("[foo { bar } foo]", "foo { bar } foo"), arguments("[foo { bar }, foo]", "foo { bar }; foo"), arguments("[foo, bar, foo]", "foo; bar; foo"), arguments("[foo, bar { blah }, foo]", "foo; bar { blah }; foo"), arguments("[foo, bar { blah; blubb }, foo]", "foo; bar { blah; blubb }; foo"), arguments("[foo, bar { blah; blubb; three four }, foo]", "foo; bar { blah; blubb; three four }; foo"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCleanTranslationForReplace_1to2_7to10")
    public void testCleanTranslationForReplace_1to2_7to10(String param1, String param2) {
        assertThat(translator.cleanTranslationForReplace(param1, param2), is(""));
    }

    static public Stream<Arguments> Provider_testCleanTranslationForReplace_1to2_7to10() {
        return Stream.of(arguments("", ""), arguments("to go", "go"), arguments("foo (bar) {mus}", "foo"), arguments("some thing [Br.], something", "some thing , something"), arguments("Friday /Fri/", "Friday"), arguments("demise [poet.] <death>", "demise"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCleanTranslationForReplace_3to6")
    public void testCleanTranslationForReplace_3to6(String param1, String param2, String param3) {
        assertThat(translator.cleanTranslationForReplace(param1, param2), is(param3));
    }

    static public Stream<Arguments> Provider_testCleanTranslationForReplace_3to6() {
        return Stream.of(arguments("to go", "need", "to go"), arguments("to go", "will", "go"), arguments("to go", "foo", "go"), arguments("to go", "to", "go"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetTranslationSuffix_1to7")
    public void testGetTranslationSuffix_1to7(String param1, String param2) {
        assertThat(translator.getTranslationSuffix(param1), is(param2));
    }

    static public Stream<Arguments> Provider_testGetTranslationSuffix_1to7() {
        return Stream.of(arguments("", ""), arguments(" ", ""), arguments("foo bar", ""), arguments("foo bar [Br.]", "[Br.]"), arguments("foo bar {ugs} [Br.]", "{ugs} [Br.]"), arguments("foo bar {ugs} [Br.] (Blah)", "{ugs} [Br.] (Blah)"), arguments("foo bar <blah>", "<blah>"));
    }
}
