package org.jboss.as.ejb3.util;

import static org.jboss.as.ejb3.util.EjbValidationsUtil.assertEjbClassValidity;
import static org.jboss.as.ejb3.util.EjbValidationsUtil.verifyEjbPublicMethodAreNotFinalNorStatic;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.io.InputStream;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.Index;
import org.jboss.jandex.Indexer;
import org.junit.Test;

public class EjbValidationsUtilTest_Purified {

    private ClassInfo buildClassInfoForClass(String mdbClassName) {
        String mdbClassNameAsResource = mdbClassName.replaceAll("\\.", "/").concat(".class");
        Index index = indexStream(getClass().getClassLoader().getResourceAsStream(mdbClassNameAsResource)).complete();
        return index.getClassByName(DotName.createSimple(mdbClassName));
    }

    private Indexer indexStream(InputStream stream) {
        try {
            Indexer indexer = new Indexer();
            indexer.index(stream);
            stream.close();
            return indexer;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void ejbWithFinalOrStaticMethods_1() {
    }

    @Test
    public void ejbWithFinalOrStaticMethods_2() {
        assertTrue(verifyEjbPublicMethodAreNotFinalNorStatic(EjbWithPrivateFinalMethod.class.getMethods(), EjbWithPrivateFinalMethod.class.getName()));
    }

    @Test
    public void ejbWithFinalOrStaticMethods_3() {
        assertFalse(verifyEjbPublicMethodAreNotFinalNorStatic(EjbWithStaticMethod.class.getMethods(), EjbWithStaticMethod.class.getName()));
    }

    @Test
    public void ejbWithFinalOrStaticMethods_4() {
        assertFalse(verifyEjbPublicMethodAreNotFinalNorStatic(EjbWithFinalMethod.class.getMethods(), EjbWithFinalMethod.class.getName()));
    }

    @Test
    public void ejbWithFinalOrStaticMethods_5() {
        assertTrue(verifyEjbPublicMethodAreNotFinalNorStatic(CleanBean.class.getMethods(), CleanBean.class.getName()));
    }

    @Test
    public void ejbWithFinalOrStaticMethods_6() {
        assertFalse(verifyEjbPublicMethodAreNotFinalNorStatic(TaintedBean.class.getMethods(), TaintedBean.class.getName()));
    }
}
