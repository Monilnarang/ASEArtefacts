package org.asteriskjava.manager.internal;

import org.asteriskjava.AsteriskVersion;
import org.asteriskjava.manager.action.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ActionBuilderImplTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testDetermineSetterName_1to2")
    void testDetermineSetterName_1to2(String param1, String param2) {
        assertEquals(param1, ActionBuilderImpl.determineSetterName(param2));
    }

    static public Stream<Arguments> Provider_testDetermineSetterName_1to2() {
        return Stream.of(arguments("setProperty1", "getProperty1"), arguments("setProperty1", "isProperty1"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDetermineFieldName_1to3")
    void testDetermineFieldName_1to3(String param1, String param2) {
        assertEquals(param1, ActionBuilderImpl.determineFieldName(param2));
    }

    static public Stream<Arguments> Provider_testDetermineFieldName_1to3() {
        return Stream.of(arguments("property1", "getProperty1"), arguments("property1", "isProperty1"), arguments("property1", "setProperty1"));
    }
}
