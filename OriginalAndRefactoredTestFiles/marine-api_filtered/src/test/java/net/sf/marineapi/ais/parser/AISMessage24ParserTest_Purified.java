package net.sf.marineapi.ais.parser;

import net.sf.marineapi.ais.message.AISMessage24;
import net.sf.marineapi.ais.util.Sixbit;
import org.junit.Test;
import static org.junit.Assert.*;

public class AISMessage24ParserTest_Purified {

    private final String payloadA = "H1c2;qA@PU>0U>060<h5=>0:1Dp";

    private final String payloadB = "H1c2;qDTijklmno31<<C970`43<1";

    private final Sixbit sixbitA = new Sixbit(payloadA, 2);

    private final Sixbit sixbitB = new Sixbit(payloadB, 0);

    private final AISMessage24 partA = new AISMessage24Parser(sixbitA);

    private final AISMessage24 partB = new AISMessage24Parser(sixbitB);

    @Test
    public void getPartNumber_1() throws Exception {
        assertEquals(0, partA.getPartNumber());
    }

    @Test
    public void getPartNumber_2() throws Exception {
        assertEquals(1, partB.getPartNumber());
    }
}
