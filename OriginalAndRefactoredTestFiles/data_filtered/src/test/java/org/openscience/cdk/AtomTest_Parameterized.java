package org.openscience.cdk;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.test.interfaces.AbstractAtomTest;
import org.openscience.cdk.interfaces.IElement;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class AtomTest_Parameterized extends AbstractAtomTest {

    @BeforeAll
    static void setUp() {
        setTestObjectBuilder(Atom::new);
    }

    @ParameterizedTest
    @MethodSource("Provider_testNewAtomImplicitHydrogenCount_1to5")
    void testNewAtomImplicitHydrogenCount_1to5(String param1) {
        Assertions.assertNull(new Atom(param1).getImplicitHydrogenCount());
    }

    static public Stream<Arguments> Provider_testNewAtomImplicitHydrogenCount_1to5() {
        return Stream.of(arguments("C"), arguments("*"), arguments("H"), arguments("D"), arguments("T"));
    }
}
