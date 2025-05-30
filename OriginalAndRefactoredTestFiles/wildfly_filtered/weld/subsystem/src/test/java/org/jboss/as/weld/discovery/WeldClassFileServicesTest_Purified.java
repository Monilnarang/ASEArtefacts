package org.jboss.as.weld.discovery;

import java.io.IOException;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;
import jakarta.enterprise.inject.Vetoed;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jboss.as.weld.discovery.InnerClasses.InnerInterface;
import org.jboss.as.weld.discovery.vetoed.Bravo;
import org.jboss.weld.resources.spi.ClassFileInfo;
import org.jboss.weld.resources.spi.ClassFileServices;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WeldClassFileServicesTest_Purified {

    private static ClassFileInfo alpha;

    private static ClassFileInfo abstractAlpha;

    private static ClassFileInfo alphaImpl;

    private static ClassFileInfo innerInterface;

    private static ClassFileInfo bravo;

    private static ClassFileInfo charlie;

    @BeforeClass
    public static void init() throws IOException {
        ClassFileServices service = new WeldClassFileServices(IndexUtils.createIndex(Alpha.class, AlphaImpl.class, AbstractAlpha.class, InnerClasses.class, Bravo.class, "org/jboss/as/weld/discovery/vetoed/package-info.class", Inject.class, Named.class, Charlie.class), Thread.currentThread().getContextClassLoader());
        alpha = service.getClassFileInfo(Alpha.class.getName());
        abstractAlpha = service.getClassFileInfo(AbstractAlpha.class.getName());
        alphaImpl = service.getClassFileInfo(AlphaImpl.class.getName());
        innerInterface = service.getClassFileInfo(InnerClasses.InnerInterface.class.getName());
        bravo = service.getClassFileInfo(Bravo.class.getName());
        charlie = service.getClassFileInfo(Charlie.class.getName());
    }

    @Test
    public void testModifiers_1() throws IOException {
        Assert.assertTrue(Modifier.isAbstract(alpha.getModifiers()));
    }

    @Test
    public void testModifiers_2() throws IOException {
        Assert.assertTrue(Modifier.isAbstract(abstractAlpha.getModifiers()));
    }

    @Test
    public void testModifiers_3() throws IOException {
        Assert.assertFalse(Modifier.isAbstract(alphaImpl.getModifiers()));
    }

    @Test
    public void testModifiers_4() throws IOException {
        Assert.assertFalse(Modifier.isStatic(alpha.getModifiers()));
    }

    @Test
    public void testModifiers_5() throws IOException {
        Assert.assertFalse(Modifier.isStatic(abstractAlpha.getModifiers()));
    }

    @Test
    public void testModifiers_6() throws IOException {
        Assert.assertFalse(Modifier.isStatic(alphaImpl.getModifiers()));
    }

    @Test
    public void testVeto_1() throws IOException {
        Assert.assertTrue(alpha.isVetoed());
    }

    @Test
    public void testVeto_2() throws IOException {
        Assert.assertFalse(abstractAlpha.isVetoed());
    }

    @Test
    public void testVeto_3() throws IOException {
        Assert.assertFalse(alphaImpl.isVetoed());
    }

    @Test
    public void testVeto_4() throws IOException {
        Assert.assertTrue(bravo.isVetoed());
    }

    @Test
    public void testSuperclassName_1() {
        Assert.assertEquals(Object.class.getName(), alpha.getSuperclassName());
    }

    @Test
    public void testSuperclassName_2() {
        Assert.assertEquals(Object.class.getName(), abstractAlpha.getSuperclassName());
    }

    @Test
    public void testSuperclassName_3() {
        Assert.assertEquals(AbstractAlpha.class.getName(), alphaImpl.getSuperclassName());
    }

    @Test
    public void testIsAssignableFrom_1() {
        Assert.assertTrue(alpha.isAssignableFrom(AlphaImpl.class));
    }

    @Test
    public void testIsAssignableFrom_2() {
        Assert.assertTrue(abstractAlpha.isAssignableFrom(AlphaImpl.class));
    }

    @Test
    public void testIsAssignableFrom_3() {
        Assert.assertFalse(abstractAlpha.isAssignableFrom(Alpha.class));
    }

    @Test
    public void testIsAssignableFrom_4() {
        Assert.assertTrue(innerInterface.isAssignableFrom(Bravo.class));
    }

    @Test
    public void testIsAssignableFrom_5() {
        Assert.assertTrue(alphaImpl.isAssignableFrom(Bravo.class));
    }

    @Test
    public void testIsAssignableTo_1() {
        Assert.assertTrue(alphaImpl.isAssignableTo(Alpha.class));
    }

    @Test
    public void testIsAssignableTo_2() {
        Assert.assertTrue(abstractAlpha.isAssignableTo(Alpha.class));
    }

    @Test
    public void testIsAssignableTo_3() {
        Assert.assertFalse(abstractAlpha.isAssignableTo(AlphaImpl.class));
    }

    @Test
    public void testIsAssignableTo_4() {
        Assert.assertTrue(bravo.isAssignableTo(InnerInterface.class));
    }

    @Test
    public void testIsAssignableTo_5() {
        Assert.assertTrue(bravo.isAssignableTo(AbstractAlpha.class));
    }

    @Test
    public void testIsAssignableTo_6() {
        Assert.assertFalse(bravo.isAssignableTo(InnerClasses.class));
    }

    @Test
    public void testIsAssignableToObject_1() {
        Assert.assertTrue(alpha.isAssignableTo(Object.class));
    }

    @Test
    public void testIsAssignableToObject_2() {
        Assert.assertTrue(abstractAlpha.isAssignableTo(Object.class));
    }

    @Test
    public void testIsAssignableToObject_3() {
        Assert.assertTrue(alphaImpl.isAssignableTo(Object.class));
    }

    @Test
    public void testIsAssignableToObject_4() {
        Assert.assertTrue(bravo.isAssignableTo(Object.class));
    }

    @Test
    public void testIsAssignableFromObject_1() {
        Assert.assertFalse(alpha.isAssignableFrom(Object.class));
    }

    @Test
    public void testIsAssignableFromObject_2() {
        Assert.assertFalse(abstractAlpha.isAssignableFrom(Object.class));
    }

    @Test
    public void testIsAssignableFromObject_3() {
        Assert.assertFalse(alphaImpl.isAssignableFrom(Object.class));
    }

    @Test
    public void testIsAssignableFromObject_4() {
        Assert.assertFalse(bravo.isAssignableFrom(Object.class));
    }

    @Test
    public void testIsAnnotationDeclared_1() {
        Assert.assertTrue(alpha.isAnnotationDeclared(Vetoed.class));
    }

    @Test
    public void testIsAnnotationDeclared_2() {
        Assert.assertTrue(innerInterface.isAnnotationDeclared(Named.class));
    }

    @Test
    public void testIsAnnotationDeclared_3() {
        Assert.assertFalse(bravo.isAnnotationDeclared(Vetoed.class));
    }

    @Test
    public void testIsAnnotationDeclared_4() {
        Assert.assertFalse(bravo.isAnnotationDeclared(Named.class));
    }

    @Test
    public void testIsAnnotationDeclared_5() {
        Assert.assertFalse(bravo.isAnnotationDeclared(Inject.class));
    }

    @Test
    public void testContainsAnnotation_1() {
        Assert.assertTrue(alpha.containsAnnotation(Vetoed.class));
    }

    @Test
    public void testContainsAnnotation_2() {
        Assert.assertTrue(innerInterface.containsAnnotation(Named.class));
    }

    @Test
    public void testContainsAnnotation_3() {
        Assert.assertFalse(bravo.containsAnnotation(Vetoed.class));
    }

    @Test
    public void testContainsAnnotation_4() {
        Assert.assertFalse(bravo.containsAnnotation(Named.class));
    }

    @Test
    public void testContainsAnnotation_5() {
        Assert.assertTrue(bravo.containsAnnotation(Inject.class));
    }

    @Test
    public void testContainsAnnotationReflectionFallback_1() {
        Assert.assertTrue(charlie.containsAnnotation(Target.class));
    }

    @Test
    public void testContainsAnnotationReflectionFallback_2() {
        Assert.assertTrue(bravo.containsAnnotation(Target.class));
    }
}
