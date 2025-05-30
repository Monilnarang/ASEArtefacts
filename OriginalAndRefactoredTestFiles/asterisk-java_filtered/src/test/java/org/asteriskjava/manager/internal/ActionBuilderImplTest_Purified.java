package org.asteriskjava.manager.internal;

import org.asteriskjava.AsteriskVersion;
import org.asteriskjava.manager.action.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class ActionBuilderImplTest_Purified {

    private ActionBuilderImpl actionBuilder;

    @BeforeEach
    void setUp() {
        this.actionBuilder = new ActionBuilderImpl();
    }

    class MyAction extends AbstractManagerAction {

        private static final long serialVersionUID = 3257568425345102641L;

        private String firstProperty;

        private Integer secondProperty;

        private String nonPublicProperty;

        @Override
        public String getAction() {
            return "My";
        }

        public String getFirstProperty() {
            return firstProperty;
        }

        public void setFirstProperty(String firstProperty) {
            this.firstProperty = firstProperty;
        }

        public Integer getSecondProperty() {
            return secondProperty;
        }

        public void setSecondProperty(Integer secondProperty) {
            this.secondProperty = secondProperty;
        }

        protected String getNonPublicProperty() {
            return nonPublicProperty;
        }

        protected void setNonPublicProperty(String privateProperty) {
            this.nonPublicProperty = privateProperty;
        }

        public String get() {
            return "This method must not be considered a getter";
        }

        public String getIndexedProperty(int i) {
            return "This method must not be considered a getter relevant for building the action";
        }
    }

    @Test
    void testDetermineSetterName_1() {
        assertEquals("setProperty1", ActionBuilderImpl.determineSetterName("getProperty1"));
    }

    @Test
    void testDetermineSetterName_2() {
        assertEquals("setProperty1", ActionBuilderImpl.determineSetterName("isProperty1"));
    }

    @Test
    void testDetermineFieldName_1() {
        assertEquals("property1", ActionBuilderImpl.determineFieldName("getProperty1"));
    }

    @Test
    void testDetermineFieldName_2() {
        assertEquals("property1", ActionBuilderImpl.determineFieldName("isProperty1"));
    }

    @Test
    void testDetermineFieldName_3() {
        assertEquals("property1", ActionBuilderImpl.determineFieldName("setProperty1"));
    }
}
