package org.apache.commons.net.imap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.util.stream.Stream;
import org.apache.commons.net.MalformedServerReplyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class IMAPReplyTest_Parameterized {

    private static Stream<String> invalidLiteralCommands() {
        return Stream.of("", "{", "}", "{}", "{foobar}", "STORE +FLAGS.SILENT \\DELETED {", "STORE +FLAGS.SILENT \\DELETED }", "STORE +FLAGS.SILENT \\DELETED {-1}", "STORE +FLAGS.SILENT \\DELETED {-10}", "STORE +FLAGS.SILENT \\DELETED {-2147483648}");
    }

    private static Stream<Arguments> literalCommands() {
        return Stream.of(Arguments.of(310, "A003 APPEND saved-messages (\\Seen) {310}"), Arguments.of(6, "A284 SEARCH CHARSET UTF-8 TEXT {6}"), Arguments.of(7, "FRED FOOBAR {7}"), Arguments.of(102856, "A044 BLURDYBLOOP {102856}"), Arguments.of(342, "* 12 FETCH (BODY[HEADER] {342}"), Arguments.of(0, "X999 LOGIN {0}"), Arguments.of(Integer.MAX_VALUE, "X999 LOGIN {2147483647}"));
    }

    @Test
    public void testGetReplyCodeOkLine_1() throws IOException {
        assertEquals(IMAPReply.OK, IMAPReply.getReplyCode("A001 OK LOGIN completed"));
    }

    @Test
    public void testGetReplyCodeOkLine_2() throws IOException {
        assertEquals(IMAPReply.OK, IMAPReply.getReplyCode("AAAA OK [CAPABILITY IMAP4rev1 SASL-IR LOGIN-REFERRALS ID ENABLE IDLE SORT" + " SORT=DISPLAY THREAD=REFERENCES THREAD=REFS THREAD=ORDEREDSUBJECT" + " MULTIAPPEND URL-PARTIAL CATENATE UNSELECT CHILDREN NAMESPACE UIDPLUS" + " LIST-EXTENDED I18NLEVEL=1 CONDSTORE QRESYNC ESEARCH ESORT SEARCHRES WITHIN" + " CONTEXT=SEARCH LIST-STATUS BINARY MOVE SNIPPET=FUZZY PREVIEW=FUZZY PREVIEW" + " STATUS=SIZE SAVEDATE XLIST LITERAL+ NOTIFY SPECIAL-USE] Logged in"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetUntaggedReplyCodeOkLine_1to3")
    public void testGetUntaggedReplyCodeOkLine_1to3(String param1) throws IOException {
        assertEquals(IMAPReply.OK, IMAPReply.getUntaggedReplyCode(param1));
    }

    static public Stream<Arguments> Provider_testGetUntaggedReplyCodeOkLine_1to3() {
        return Stream.of(arguments("* OK Salvage successful, no data lost"), arguments("* OK The Microsoft Exchange IMAP4 service is ready. [xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx]"), arguments("* OK The Microsoft Exchange IMAP4 service is ready. [TQBXADIAUABSADIAMQAwADEAQwBBADAAMAAzADYALgBuAGEAbQBwAHIAZAAyADEALgBwAHIAbwBkAC4AbwB1AHQAbABvAG8AawAuAGMAbwBtAA==]"));
    }
}
