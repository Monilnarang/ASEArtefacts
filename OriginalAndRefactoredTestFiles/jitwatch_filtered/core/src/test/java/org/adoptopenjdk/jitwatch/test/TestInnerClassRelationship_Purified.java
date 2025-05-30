package org.adoptopenjdk.jitwatch.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.adoptopenjdk.jitwatch.model.bytecode.InnerClassRelationship;
import org.junit.Test;

public class TestInnerClassRelationship_Purified {

    @Test
    public void testInnerClassNameFinder_1() {
        String line1 = "public #13= #6 of #10; //Cow=class PolymorphismTest$Cow of class PolymorphismTest";
        assertEquals("PolymorphismTest$Cow", InnerClassRelationship.parse(line1).getChildClass());
    }

    @Test
    public void testInnerClassNameFinder_2() {
        String line2 = "public #15= #4 of #10; //Cat=class PolymorphismTest$Cat of class PolymorphismTest";
        assertEquals("PolymorphismTest$Cat", InnerClassRelationship.parse(line2).getChildClass());
    }

    @Test
    public void testInnerClassNameFinder_3() {
        String line3 = "public #16= #2 of #10; //Dog=class PolymorphismTest$Dog of class PolymorphismTest";
        assertEquals("PolymorphismTest$Dog", InnerClassRelationship.parse(line3).getChildClass());
    }

    @Test
    public void testInnerClassNameFinder_4() {
        String line4 = "public static #18= #17 of #10; //Animal=class PolymorphismTest$Animal of class PolymorphismTest";
        assertEquals("PolymorphismTest$Animal", InnerClassRelationship.parse(line4).getChildClass());
    }

    @Test
    public void testInnerClassNameFinder_5() {
        String line5 = "foo";
        assertNull(InnerClassRelationship.parse(line5));
    }
}
