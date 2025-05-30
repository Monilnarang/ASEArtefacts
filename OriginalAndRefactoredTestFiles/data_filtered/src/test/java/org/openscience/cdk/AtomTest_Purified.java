package org.openscience.cdk;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.test.interfaces.AbstractAtomTest;
import org.openscience.cdk.interfaces.IElement;

class AtomTest_Purified extends AbstractAtomTest {

    @BeforeAll
    static void setUp() {
        setTestObjectBuilder(Atom::new);
    }

    @Test
    void testNewAtomImplicitHydrogenCount_1() {
        Assertions.assertNull(new Atom("C").getImplicitHydrogenCount());
    }

    @Test
    void testNewAtomImplicitHydrogenCount_2() {
        Assertions.assertNull(new Atom("*").getImplicitHydrogenCount());
    }

    @Test
    void testNewAtomImplicitHydrogenCount_3() {
        Assertions.assertNull(new Atom("H").getImplicitHydrogenCount());
    }

    @Test
    void testNewAtomImplicitHydrogenCount_4() {
        Assertions.assertNull(new Atom("D").getImplicitHydrogenCount());
    }

    @Test
    void testNewAtomImplicitHydrogenCount_5() {
        Assertions.assertNull(new Atom("T").getImplicitHydrogenCount());
    }
}
