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

public class EmailValidatorTest_Purified {

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

    @Test
    public void testEmailExtension_1() {
        assertTrue(validator.isValid("jsmith@apache.org"));
    }

    @Test
    public void testEmailExtension_2() {
        assertTrue(validator.isValid("jsmith@apache.com"));
    }

    @Test
    public void testEmailExtension_3() {
        assertTrue(validator.isValid("jsmith@apache.net"));
    }

    @Test
    public void testEmailExtension_4() {
        assertTrue(validator.isValid("jsmith@apache.info"));
    }

    @Test
    public void testEmailExtension_5() {
        assertFalse(validator.isValid("jsmith@apache."));
    }

    @Test
    public void testEmailExtension_6() {
        assertFalse(validator.isValid("jsmith@apache.c"));
    }

    @Test
    public void testEmailExtension_7() {
        assertTrue(validator.isValid("someone@yahoo.museum"));
    }

    @Test
    public void testEmailExtension_8() {
        assertFalse(validator.isValid("someone@yahoo.mu-seum"));
    }

    @Test
    public void testEmailUserName_1() {
        assertTrue(validator.isValid("joe1blow@apache.org"));
    }

    @Test
    public void testEmailUserName_2() {
        assertTrue(validator.isValid("joe$blow@apache.org"));
    }

    @Test
    public void testEmailUserName_3() {
        assertTrue(validator.isValid("joe-@apache.org"));
    }

    @Test
    public void testEmailUserName_4() {
        assertTrue(validator.isValid("joe_@apache.org"));
    }

    @Test
    public void testEmailUserName_5() {
        assertTrue(validator.isValid("joe+@apache.org"));
    }

    @Test
    public void testEmailUserName_6() {
        assertTrue(validator.isValid("joe!@apache.org"));
    }

    @Test
    public void testEmailUserName_7() {
        assertTrue(validator.isValid("joe*@apache.org"));
    }

    @Test
    public void testEmailUserName_8() {
        assertTrue(validator.isValid("joe'@apache.org"));
    }

    @Test
    public void testEmailUserName_9() {
        assertTrue(validator.isValid("joe%45@apache.org"));
    }

    @Test
    public void testEmailUserName_10() {
        assertTrue(validator.isValid("joe?@apache.org"));
    }

    @Test
    public void testEmailUserName_11() {
        assertTrue(validator.isValid("joe&@apache.org"));
    }

    @Test
    public void testEmailUserName_12() {
        assertTrue(validator.isValid("joe=@apache.org"));
    }

    @Test
    public void testEmailUserName_13() {
        assertTrue(validator.isValid("+joe@apache.org"));
    }

    @Test
    public void testEmailUserName_14() {
        assertTrue(validator.isValid("!joe@apache.org"));
    }

    @Test
    public void testEmailUserName_15() {
        assertTrue(validator.isValid("*joe@apache.org"));
    }

    @Test
    public void testEmailUserName_16() {
        assertTrue(validator.isValid("'joe@apache.org"));
    }

    @Test
    public void testEmailUserName_17() {
        assertTrue(validator.isValid("%joe45@apache.org"));
    }

    @Test
    public void testEmailUserName_18() {
        assertTrue(validator.isValid("?joe@apache.org"));
    }

    @Test
    public void testEmailUserName_19() {
        assertTrue(validator.isValid("&joe@apache.org"));
    }

    @Test
    public void testEmailUserName_20() {
        assertTrue(validator.isValid("=joe@apache.org"));
    }

    @Test
    public void testEmailUserName_21() {
        assertTrue(validator.isValid("+@apache.org"));
    }

    @Test
    public void testEmailUserName_22() {
        assertTrue(validator.isValid("!@apache.org"));
    }

    @Test
    public void testEmailUserName_23() {
        assertTrue(validator.isValid("*@apache.org"));
    }

    @Test
    public void testEmailUserName_24() {
        assertTrue(validator.isValid("'@apache.org"));
    }

    @Test
    public void testEmailUserName_25() {
        assertTrue(validator.isValid("%@apache.org"));
    }

    @Test
    public void testEmailUserName_26() {
        assertTrue(validator.isValid("?@apache.org"));
    }

    @Test
    public void testEmailUserName_27() {
        assertTrue(validator.isValid("&@apache.org"));
    }

    @Test
    public void testEmailUserName_28() {
        assertTrue(validator.isValid("=@apache.org"));
    }

    @Test
    public void testEmailUserName_29() {
        assertFalse(validator.isValid("joe.@apache.org"));
    }

    @Test
    public void testEmailUserName_30() {
        assertFalse(validator.isValid(".joe@apache.org"));
    }

    @Test
    public void testEmailUserName_31() {
        assertFalse(validator.isValid(".@apache.org"));
    }

    @Test
    public void testEmailUserName_32() {
        assertTrue(validator.isValid("joe.ok@apache.org"));
    }

    @Test
    public void testEmailUserName_33() {
        assertFalse(validator.isValid("joe..ok@apache.org"));
    }

    @Test
    public void testEmailUserName_34() {
        assertFalse(validator.isValid("..@apache.org"));
    }

    @Test
    public void testEmailUserName_35() {
        assertFalse(validator.isValid("joe(@apache.org"));
    }

    @Test
    public void testEmailUserName_36() {
        assertFalse(validator.isValid("joe)@apache.org"));
    }

    @Test
    public void testEmailUserName_37() {
        assertFalse(validator.isValid("joe,@apache.org"));
    }

    @Test
    public void testEmailUserName_38() {
        assertFalse(validator.isValid("joe;@apache.org"));
    }

    @Test
    public void testEmailUserName_39() {
        assertTrue(validator.isValid("\"joe.\"@apache.org"));
    }

    @Test
    public void testEmailUserName_40() {
        assertTrue(validator.isValid("\".joe\"@apache.org"));
    }

    @Test
    public void testEmailUserName_41() {
        assertTrue(validator.isValid("\"joe+\"@apache.org"));
    }

    @Test
    public void testEmailUserName_42() {
        assertTrue(validator.isValid("\"joe@\"@apache.org"));
    }

    @Test
    public void testEmailUserName_43() {
        assertTrue(validator.isValid("\"joe!\"@apache.org"));
    }

    @Test
    public void testEmailUserName_44() {
        assertTrue(validator.isValid("\"joe*\"@apache.org"));
    }

    @Test
    public void testEmailUserName_45() {
        assertTrue(validator.isValid("\"joe'\"@apache.org"));
    }

    @Test
    public void testEmailUserName_46() {
        assertTrue(validator.isValid("\"joe(\"@apache.org"));
    }

    @Test
    public void testEmailUserName_47() {
        assertTrue(validator.isValid("\"joe)\"@apache.org"));
    }

    @Test
    public void testEmailUserName_48() {
        assertTrue(validator.isValid("\"joe,\"@apache.org"));
    }

    @Test
    public void testEmailUserName_49() {
        assertTrue(validator.isValid("\"joe%45\"@apache.org"));
    }

    @Test
    public void testEmailUserName_50() {
        assertTrue(validator.isValid("\"joe;\"@apache.org"));
    }

    @Test
    public void testEmailUserName_51() {
        assertTrue(validator.isValid("\"joe?\"@apache.org"));
    }

    @Test
    public void testEmailUserName_52() {
        assertTrue(validator.isValid("\"joe&\"@apache.org"));
    }

    @Test
    public void testEmailUserName_53() {
        assertTrue(validator.isValid("\"joe=\"@apache.org"));
    }

    @Test
    public void testEmailUserName_54() {
        assertTrue(validator.isValid("\"..\"@apache.org"));
    }

    @Test
    public void testEmailUserName_55() {
        assertTrue(validator.isValid("\"john\\\"doe\"@apache.org"));
    }

    @Test
    public void testEmailUserName_56() {
        assertTrue(validator.isValid("john56789.john56789.john56789.john56789.john56789.john56789.john@example.com"));
    }

    @Test
    public void testEmailUserName_57() {
        assertFalse(validator.isValid("john56789.john56789.john56789.john56789.john56789.john56789.john5@example.com"));
    }

    @Test
    public void testEmailUserName_58() {
        assertTrue(validator.isValid("\\>escape\\\\special\\^characters\\<@example.com"));
    }

    @Test
    public void testEmailUserName_59() {
        assertTrue(validator.isValid("Abc\\@def@example.com"));
    }

    @Test
    public void testEmailUserName_60() {
        assertFalse(validator.isValid("Abc@def@example.com"));
    }

    @Test
    public void testEmailUserName_61() {
        assertTrue(validator.isValid("space\\ monkey@example.com"));
    }

    @Test
    public void testEmailWithBogusCharacter_1() {
        assertFalse(validator.isValid("andy.noble@\u008fdata-workshop.com"));
    }

    @Test
    public void testEmailWithBogusCharacter_2() {
        assertTrue(validator.isValid("andy.o'reilly@data-workshop.com"));
    }

    @Test
    public void testEmailWithBogusCharacter_3() {
        assertFalse(validator.isValid("andy@o'reilly.data-workshop.com"));
    }

    @Test
    public void testEmailWithBogusCharacter_4() {
        assertTrue(validator.isValid("foo+bar@i.am.not.in.us.example.com"));
    }

    @Test
    public void testEmailWithBogusCharacter_5() {
        assertFalse(validator.isValid("foo+bar@example+3.com"));
    }

    @Test
    public void testEmailWithBogusCharacter_6() {
        assertFalse(validator.isValid("test@%*.com"));
    }

    @Test
    public void testEmailWithBogusCharacter_7() {
        assertFalse(validator.isValid("test@^&#.com"));
    }

    @Test
    public void testEmailWithCommas_1() {
        assertFalse(validator.isValid("joeblow@apa,che.org"));
    }

    @Test
    public void testEmailWithCommas_2() {
        assertFalse(validator.isValid("joeblow@apache.o,rg"));
    }

    @Test
    public void testEmailWithCommas_3() {
        assertFalse(validator.isValid("joeblow@apache,org"));
    }

    @Test
    public void testEmailWithDash_1() {
        assertTrue(validator.isValid("andy.noble@data-workshop.com"));
    }

    @Test
    public void testEmailWithDash_2() {
        assertFalse(validator.isValid("andy-noble@data-workshop.-com"));
    }

    @Test
    public void testEmailWithDash_3() {
        assertFalse(validator.isValid("andy-noble@data-workshop.c-om"));
    }

    @Test
    public void testEmailWithDash_4() {
        assertFalse(validator.isValid("andy-noble@data-workshop.co-m"));
    }

    @Test
    public void testEmailWithNumericAddress_1() {
        assertTrue(validator.isValid("someone@[216.109.118.76]"));
    }

    @Test
    public void testEmailWithNumericAddress_2() {
        assertTrue(validator.isValid("someone@yahoo.com"));
    }

    @Test
    public void testEmailWithSlashes_1() {
        assertTrue(validator.isValid("joe!/blow@apache.org"), "/ and ! valid in username");
    }

    @Test
    public void testEmailWithSlashes_2() {
        assertFalse(validator.isValid("joe@ap/ache.org"), "/ not valid in domain");
    }

    @Test
    public void testEmailWithSlashes_3() {
        assertFalse(validator.isValid("joe@apac!he.org"), "! not valid in domain");
    }

    @Test
    public void testEmailWithSpaces_1() {
        assertFalse(validator.isValid("joeblow @apache.org"));
    }

    @Test
    public void testEmailWithSpaces_2() {
        assertFalse(validator.isValid("joeblow@ apache.org"));
    }

    @Test
    public void testEmailWithSpaces_3() {
        assertFalse(validator.isValid(" joeblow@apache.org"));
    }

    @Test
    public void testEmailWithSpaces_4() {
        assertFalse(validator.isValid("joeblow@apache.org "));
    }

    @Test
    public void testEmailWithSpaces_5() {
        assertFalse(validator.isValid("joe blow@apache.org "));
    }

    @Test
    public void testEmailWithSpaces_6() {
        assertFalse(validator.isValid("joeblow@apa che.org "));
    }

    @Test
    public void testEmailWithSpaces_7() {
        assertTrue(validator.isValid("\"joeblow \"@apache.org"));
    }

    @Test
    public void testEmailWithSpaces_8() {
        assertTrue(validator.isValid("\" joeblow\"@apache.org"));
    }

    @Test
    public void testEmailWithSpaces_9() {
        assertTrue(validator.isValid("\" joe blow \"@apache.org"));
    }

    @Test
    public void testValidator235_1() {
        assertTrue(validator.isValid("someone@xn--d1abbgf6aiiy.xn--p1ai"), "xn--d1abbgf6aiiy.xn--p1ai should validate");
    }

    @Test
    public void testValidator235_2() {
        assertTrue(validator.isValid("someone@президент.рф"), "президент.рф should validate");
    }

    @Test
    public void testValidator235_3() {
        assertTrue(validator.isValid("someone@www.b\u00fccher.ch"), "www.b\u00fccher.ch should validate");
    }

    @Test
    public void testValidator235_4() {
        assertFalse(validator.isValid("someone@www.\uFFFD.ch"), "www.\uFFFD.ch FFFD should fail");
    }

    @Test
    public void testValidator235_5() {
        assertTrue(validator.isValid("someone@www.b\u00fccher.ch"), "www.b\u00fccher.ch should validate");
    }

    @Test
    public void testValidator235_6() {
        assertFalse(validator.isValid("someone@www.\uFFFD.ch"), "www.\uFFFD.ch FFFD should fail");
    }

    @Test
    public void testValidator278_1() {
        assertFalse(validator.isValid("someone@-test.com"));
    }

    @Test
    public void testValidator278_2() {
        assertFalse(validator.isValid("someone@test-.com"));
    }

    @Test
    public void testValidator293_1() {
        assertTrue(validator.isValid("abc-@abc.com"));
    }

    @Test
    public void testValidator293_2() {
        assertTrue(validator.isValid("abc_@abc.com"));
    }

    @Test
    public void testValidator293_3() {
        assertTrue(validator.isValid("abc-def@abc.com"));
    }

    @Test
    public void testValidator293_4() {
        assertTrue(validator.isValid("abc_def@abc.com"));
    }

    @Test
    public void testValidator293_5() {
        assertFalse(validator.isValid("abc@abc_def.com"));
    }

    @Test
    public void testValidator315_1() {
        assertFalse(validator.isValid("me@at&t.net"));
    }

    @Test
    public void testValidator315_2() {
        assertTrue(validator.isValid("me@att.net"));
    }
}
