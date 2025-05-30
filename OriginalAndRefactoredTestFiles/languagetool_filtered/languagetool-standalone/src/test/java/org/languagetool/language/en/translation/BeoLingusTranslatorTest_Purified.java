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

public class BeoLingusTranslatorTest_Purified {

    private BeoLingusTranslator translator;

    @Before
    public void init() throws IOException {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setBeolingusFile(new File("src/test/resources/beolingus_test.txt"));
        translator = BeoLingusTranslator.getInstance(globalConfig);
    }

    @Test
    public void testSplit_1() {
        assertThat(translator.split("foo").toString(), is("[foo]"));
    }

    @Test
    public void testSplit_2() {
        assertThat(translator.split("foo { bar } foo").toString(), is("[foo { bar } foo]"));
    }

    @Test
    public void testSplit_3() {
        assertThat(translator.split("foo { bar }; foo").toString(), is("[foo { bar }, foo]"));
    }

    @Test
    public void testSplit_4() {
        assertThat(translator.split("foo; bar; foo").toString(), is("[foo, bar, foo]"));
    }

    @Test
    public void testSplit_5() {
        assertThat(translator.split("foo; bar { blah }; foo").toString(), is("[foo, bar { blah }, foo]"));
    }

    @Test
    public void testSplit_6() {
        assertThat(translator.split("foo; bar { blah; blubb }; foo").toString(), is("[foo, bar { blah; blubb }, foo]"));
    }

    @Test
    public void testSplit_7() {
        assertThat(translator.split("foo; bar { blah; blubb; three four }; foo").toString(), is("[foo, bar { blah; blubb; three four }, foo]"));
    }

    @Test
    public void testCleanTranslationForReplace_1() {
        assertThat(translator.cleanTranslationForReplace("", null), is(""));
    }

    @Test
    public void testCleanTranslationForReplace_2() {
        assertThat(translator.cleanTranslationForReplace("to go", null), is("go"));
    }

    @Test
    public void testCleanTranslationForReplace_3() {
        assertThat(translator.cleanTranslationForReplace("to go", "need"), is("to go"));
    }

    @Test
    public void testCleanTranslationForReplace_4() {
        assertThat(translator.cleanTranslationForReplace("to go", "will"), is("go"));
    }

    @Test
    public void testCleanTranslationForReplace_5() {
        assertThat(translator.cleanTranslationForReplace("to go", "foo"), is("go"));
    }

    @Test
    public void testCleanTranslationForReplace_6() {
        assertThat(translator.cleanTranslationForReplace("to go", "to"), is("go"));
    }

    @Test
    public void testCleanTranslationForReplace_7() {
        assertThat(translator.cleanTranslationForReplace("foo (bar) {mus}", null), is("foo"));
    }

    @Test
    public void testCleanTranslationForReplace_8() {
        assertThat(translator.cleanTranslationForReplace("some thing [Br.], something", null), is("some thing , something"));
    }

    @Test
    public void testCleanTranslationForReplace_9() {
        assertThat(translator.cleanTranslationForReplace("Friday /Fri/", null), is("Friday"));
    }

    @Test
    public void testCleanTranslationForReplace_10() {
        assertThat(translator.cleanTranslationForReplace("demise [poet.] <death>", null), is("demise"));
    }

    @Test
    public void testGetTranslationSuffix_1() {
        assertThat(translator.getTranslationSuffix(""), is(""));
    }

    @Test
    public void testGetTranslationSuffix_2() {
        assertThat(translator.getTranslationSuffix(" "), is(""));
    }

    @Test
    public void testGetTranslationSuffix_3() {
        assertThat(translator.getTranslationSuffix("foo bar"), is(""));
    }

    @Test
    public void testGetTranslationSuffix_4() {
        assertThat(translator.getTranslationSuffix("foo bar [Br.]"), is("[Br.]"));
    }

    @Test
    public void testGetTranslationSuffix_5() {
        assertThat(translator.getTranslationSuffix("foo bar {ugs} [Br.]"), is("{ugs} [Br.]"));
    }

    @Test
    public void testGetTranslationSuffix_6() {
        assertThat(translator.getTranslationSuffix("foo bar {ugs} [Br.] (Blah)"), is("{ugs} [Br.] (Blah)"));
    }

    @Test
    public void testGetTranslationSuffix_7() {
        assertThat(translator.getTranslationSuffix("foo bar <blah>"), is("<blah>"));
    }
}
