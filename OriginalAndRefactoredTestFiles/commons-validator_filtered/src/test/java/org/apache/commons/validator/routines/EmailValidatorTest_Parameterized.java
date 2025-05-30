package org.apache.commons.validator.routines;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.validator.ResultPair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class EmailValidatorTest_Parameterized {

    protected static final String FORM_KEY = "emailForm";

    protected static final String ACTION = "email";

    private static final ResultPair[] TEST_EMAIL_FROM_PERL = { new ResultPair("abigail@example.com", true), new ResultPair("abigail@example.com ", true), new ResultPair(" abigail@example.com", true), new ResultPair("abigail @example.com ", true), new ResultPair("*@example.net", true), new ResultPair("\"\\\"\"@foo.bar", true), new ResultPair("fred&barny@example.com", true), new ResultPair("---@example.com", true), new ResultPair("foo-bar@example.net", true), new ResultPair("\"127.0.0.1\"@[127.0.0.1]", true), new ResultPair("Abigail <abigail@example.com>", true), new ResultPair("Abigail<abigail@example.com>", true), new ResultPair("Abigail<@a,@b,@c:abigail@example.com>", true), new ResultPair("\"This is a phrase\"<abigail@example.com>", true), new ResultPair("\"Abigail \"<abigail@example.com>", true), new ResultPair("\"Joe & J. Harvey\" <example @Org>", true), new ResultPair("Abigail <abigail @ example.com>", true), new ResultPair("Abigail made this <  abigail   @   example  .    com    >", true), new ResultPair("Abigail(the bitch)@example.com", true), new ResultPair("Abigail <abigail @ example . (bar) com >", true), new ResultPair("Abigail < (one)  abigail (two) @(three) example . (bar) com (quz) >", true), new ResultPair("Abigail (foo) (((baz)(nested) (comment)) ! ) < (one)  abigail (two) @(three) example . (bar) com (quz) >", true), new ResultPair("Abigail <abigail(fo\\(o)@example.com>", true), new ResultPair("Abigail <abigail(fo\\)o)@example.com> ", true), new ResultPair("(foo) abigail@example.com", true), new ResultPair("abigail@example.com (foo)", true), new ResultPair("\"Abi\\\"gail\" <abigail@example.com>", true), new ResultPair("abigail@[example.com]", true), new ResultPair("abigail@[exa\\[ple.com]", true), new ResultPair("abigail@[exa\\]ple.com]", true), new ResultPair("\":sysmail\"@  Some-Group. Some-Org", true), new ResultPair("Muhammed.(I am  the greatest) Ali @(the) Vegas.WBA", true), new ResultPair("mailbox.sub1.sub2@this-domain", true), new ResultPair("sub-net.mailbox@sub-domain.domain", true), new ResultPair("name:;", true), new ResultPair("':;", true), new ResultPair("name:   ;", true), new ResultPair("Alfred Neuman <Neuman@BBN-TENEXA>", true), new ResultPair("Neuman@BBN-TENEXA", true), new ResultPair("\"George, Ted\" <Shared@Group.Arpanet>", true), new ResultPair("Wilt . (the  Stilt) Chamberlain@NBA.US", true), new ResultPair("Cruisers:  Port@Portugal, Jones@SEA;", true), new ResultPair("$@[]", true), new ResultPair("*()@[]", true), new ResultPair("\"quoted ( brackets\" ( a comment )@example.com", true), new ResultPair("\"Joe & J. Harvey\"\\x0D\\x0A     <ddd\\@ Org>", true), new ResultPair("\"Joe &\\x0D\\x0A J. Harvey\" <ddd \\@ Org>", true), new ResultPair("Gourmets:  Pompous Person <WhoZiWhatZit\\@Cordon-Bleu>,\\x0D\\x0A" + "        Childs\\@WGBH.Boston, \"Galloping Gourmet\"\\@\\x0D\\x0A" + "        ANT.Down-Under (Australian National Television),\\x0D\\x0A" + "        Cheapie\\@Discount-Liquors;", true), new ResultPair("   Just a string", false), new ResultPair("string", false), new ResultPair("(comment)", false), new ResultPair("()@example.com", false), new ResultPair("fred(&)barny@example.com", false), new ResultPair("fred\\ barny@example.com", false), new ResultPair("Abigail <abi gail @ example.com>", false), new ResultPair("Abigail <abigail(fo(o)@example.com>", false), new ResultPair("Abigail <abigail(fo) o)@example.com>", false), new ResultPair("\"Abi\"gail\" <abigail@example.com>", false), new ResultPair("abigail@[exa]ple.com]", false), new ResultPair("abigail@[exa[ple.com]", false), new ResultPair("abigail@[exaple].com]", false), new ResultPair("abigail@", false), new ResultPair("@example.com", false), new ResultPair("phrase: abigail@example.com abigail@example.com ;", false), new ResultPair("invalid�char@example.com", false) };

    public static void main(final String[] args) {
        final EmailValidator validator = EmailValidator.getInstance();
        for (final String arg : args) {
            System.out.printf("%s: %s%n", arg, validator.isValid(arg));
        }
    }

    private EmailValidator validator;

    @BeforeEach
    public void setUp() {
        validator = EmailValidator.getInstance();
    }

    @ParameterizedTest
    @MethodSource("Provider_testEmailExtension_1_1_1_1_1to2_2_2_2_2_2to3_3_3to4_4_4_4to7_7_7to8_8to9_9to28_32_39to56_58to59_61")
    public void testEmailExtension_1_1_1_1_1to2_2_2_2_2_2to3_3_3to4_4_4_4to7_7_7to8_8to9_9to28_32_39to56_58to59_61(String param1) {
        assertTrue(validator.isValid(param1));
    }

    static public Stream<Arguments> Provider_testEmailExtension_1_1_1_1_1to2_2_2_2_2_2to3_3_3to4_4_4_4to7_7_7to8_8to9_9to28_32_39to56_58to59_61() {
        return Stream.of(arguments("jsmith@apache.org"), arguments("jsmith@apache.com"), arguments("jsmith@apache.net"), arguments("jsmith@apache.info"), arguments("someone@yahoo.museum"), arguments("joe1blow@apache.org"), arguments("joe$blow@apache.org"), arguments("joe-@apache.org"), arguments("joe_@apache.org"), arguments("joe+@apache.org"), arguments("joe!@apache.org"), arguments("joe*@apache.org"), arguments("joe'@apache.org"), arguments("joe%45@apache.org"), arguments("joe?@apache.org"), arguments("joe&@apache.org"), arguments("joe=@apache.org"), arguments("+joe@apache.org"), arguments("!joe@apache.org"), arguments("*joe@apache.org"), arguments("'joe@apache.org"), arguments("%joe45@apache.org"), arguments("?joe@apache.org"), arguments("&joe@apache.org"), arguments("=joe@apache.org"), arguments("+@apache.org"), arguments("!@apache.org"), arguments("*@apache.org"), arguments("'@apache.org"), arguments("%@apache.org"), arguments("?@apache.org"), arguments("&@apache.org"), arguments("=@apache.org"), arguments("joe.ok@apache.org"), arguments("\"joe.\"@apache.org"), arguments("\".joe\"@apache.org"), arguments("\"joe+\"@apache.org"), arguments("\"joe@\"@apache.org"), arguments("\"joe!\"@apache.org"), arguments("\"joe*\"@apache.org"), arguments("\"joe'\"@apache.org"), arguments("\"joe(\"@apache.org"), arguments("\"joe)\"@apache.org"), arguments("\"joe,\"@apache.org"), arguments("\"joe%45\"@apache.org"), arguments("\"joe;\"@apache.org"), arguments("\"joe?\"@apache.org"), arguments("\"joe&\"@apache.org"), arguments("\"joe=\"@apache.org"), arguments("\"..\"@apache.org"), arguments("\"john\\\"doe\"@apache.org"), arguments("john56789.john56789.john56789.john56789.john56789.john56789.john@example.com"), arguments("\\>escape\\\\special\\^characters\\<@example.com"), arguments("Abc\\@def@example.com"), arguments("space\\ monkey@example.com"), arguments("andy.o'reilly@data-workshop.com"), arguments("foo+bar@i.am.not.in.us.example.com"), arguments("andy.noble@data-workshop.com"), arguments("someone@[216.109.118.76]"), arguments("someone@yahoo.com"), arguments("\"joeblow \"@apache.org"), arguments("\" joeblow\"@apache.org"), arguments("\" joe blow \"@apache.org"), arguments("abc-@abc.com"), arguments("abc_@abc.com"), arguments("abc-def@abc.com"), arguments("abc_def@abc.com"), arguments("me@att.net"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEmailExtension_1_1_1_1_1to2_2_2_2to3_3_3_3to4_4to5_5_5_5to6_6_6to8_29to31_33to38_57_60")
    public void testEmailExtension_1_1_1_1_1to2_2_2_2to3_3_3_3to4_4to5_5_5_5to6_6_6to8_29to31_33to38_57_60(String param1) {
        assertFalse(validator.isValid(param1));
    }

    static public Stream<Arguments> Provider_testEmailExtension_1_1_1_1_1to2_2_2_2to3_3_3_3to4_4to5_5_5_5to6_6_6to8_29to31_33to38_57_60() {
        return Stream.of(arguments("jsmith@apache."), arguments("jsmith@apache.c"), arguments("someone@yahoo.mu-seum"), arguments("joe.@apache.org"), arguments(".joe@apache.org"), arguments(".@apache.org"), arguments("joe..ok@apache.org"), arguments("..@apache.org"), arguments("joe(@apache.org"), arguments("joe)@apache.org"), arguments("joe,@apache.org"), arguments("joe;@apache.org"), arguments("john56789.john56789.john56789.john56789.john56789.john56789.john5@example.com"), arguments("Abc@def@example.com"), arguments("andy.noble@\u008fdata-workshop.com"), arguments("andy@o'reilly.data-workshop.com"), arguments("foo+bar@example+3.com"), arguments("test@%*.com"), arguments("test@^&#.com"), arguments("joeblow@apa,che.org"), arguments("joeblow@apache.o,rg"), arguments("joeblow@apache,org"), arguments("andy-noble@data-workshop.-com"), arguments("andy-noble@data-workshop.c-om"), arguments("andy-noble@data-workshop.co-m"), arguments("joeblow @apache.org"), arguments("joeblow@ apache.org"), arguments(" joeblow@apache.org"), arguments("joeblow@apache.org "), arguments("joe blow@apache.org "), arguments("joeblow@apa che.org "), arguments("someone@-test.com"), arguments("someone@test-.com"), arguments("abc@abc_def.com"), arguments("me@at&t.net"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEmailWithSlashes_1_1to3_5")
    public void testEmailWithSlashes_1_1to3_5(String param1, String param2) {
        assertTrue(validator.isValid(param2), param1);
    }

    static public Stream<Arguments> Provider_testEmailWithSlashes_1_1to3_5() {
        return Stream.of(arguments("/ and ! valid in username", "joe!/blow@apache.org"), arguments("xn--d1abbgf6aiiy.xn--p1ai should validate", "someone@xn--d1abbgf6aiiy.xn--p1ai"), arguments("президент.рф should validate", "someone@президент.рф"), arguments("www.b\u00fccher.ch should validate", "someone@www.b\u00fccher.ch"), arguments("www.b\u00fccher.ch should validate", "someone@www.b\u00fccher.ch"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEmailWithSlashes_2to4_6")
    public void testEmailWithSlashes_2to4_6(String param1, String param2) {
        assertFalse(validator.isValid(param2), param1);
    }

    static public Stream<Arguments> Provider_testEmailWithSlashes_2to4_6() {
        return Stream.of(arguments("/ not valid in domain", "joe@ap/ache.org"), arguments("! not valid in domain", "joe@apac!he.org"), arguments("www.\uFFFD.ch FFFD should fail", "someone@www.\uFFFD.ch"), arguments("www.\uFFFD.ch FFFD should fail", "someone@www.\uFFFD.ch"));
    }
}
