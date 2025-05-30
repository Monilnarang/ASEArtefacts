package org.apache.dubbo.common.beanutil;

import org.apache.dubbo.rpc.model.person.BigPerson;
import org.apache.dubbo.rpc.model.person.FullAddress;
import org.apache.dubbo.rpc.model.person.PersonInfo;
import org.apache.dubbo.rpc.model.person.PersonStatus;
import org.apache.dubbo.rpc.model.person.Phone;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class JavaBeanSerializeUtilTest_Parameterized {

    public static class Parent {

        public String gender;

        public String email;

        String name;

        int age;

        Child child;

        private String securityEmail;

        public static Parent getNewParent() {
            return new Parent();
        }

        public String getEmail() {
            return this.securityEmail;
        }

        public void setEmail(String email) {
            this.securityEmail = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Child getChild() {
            return child;
        }

        public void setChild(Child child) {
            this.child = child;
        }
    }

    public static class Child {

        public String gender;

        public int age;

        String toy;

        Parent parent;

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getToy() {
            return toy;
        }

        public void setToy(String toy) {
            this.toy = toy;
        }

        public Parent getParent() {
            return parent;
        }

        public void setParent(Parent parent) {
            this.parent = parent;
        }
    }

    static void assertEqualsEnum(Enum<?> expected, Object obj) {
        JavaBeanDescriptor descriptor = (JavaBeanDescriptor) obj;
        Assertions.assertTrue(descriptor.isEnumType());
        Assertions.assertEquals(expected.getClass().getName(), descriptor.getClassName());
        Assertions.assertEquals(expected.name(), descriptor.getEnumPropertyName());
    }

    static void assertEqualsPrimitive(Object expected, Object obj) {
        if (expected == null) {
            return;
        }
        JavaBeanDescriptor descriptor = (JavaBeanDescriptor) obj;
        Assertions.assertTrue(descriptor.isPrimitiveType());
        Assertions.assertEquals(expected, descriptor.getPrimitiveProperty());
    }

    static void assertEqualsBigPerson(BigPerson person, Object obj) {
        JavaBeanDescriptor descriptor = (JavaBeanDescriptor) obj;
        Assertions.assertTrue(descriptor.isBeanType());
        assertEqualsPrimitive(person.getPersonId(), descriptor.getProperty("personId"));
        assertEqualsPrimitive(person.getLoginName(), descriptor.getProperty("loginName"));
        assertEqualsEnum(person.getStatus(), descriptor.getProperty("status"));
        assertEqualsPrimitive(person.getEmail(), descriptor.getProperty("email"));
        assertEqualsPrimitive(person.getPenName(), descriptor.getProperty("penName"));
        JavaBeanDescriptor infoProfile = (JavaBeanDescriptor) descriptor.getProperty("infoProfile");
        Assertions.assertTrue(infoProfile.isBeanType());
        JavaBeanDescriptor phones = (JavaBeanDescriptor) infoProfile.getProperty("phones");
        Assertions.assertTrue(phones.isCollectionType());
        assertEqualsPhone(person.getInfoProfile().getPhones().get(0), phones.getProperty(0));
        assertEqualsPhone(person.getInfoProfile().getPhones().get(1), phones.getProperty(1));
        assertEqualsPhone(person.getInfoProfile().getFax(), infoProfile.getProperty("fax"));
        assertEqualsFullAddress(person.getInfoProfile().getFullAddress(), infoProfile.getProperty("fullAddress"));
        assertEqualsPrimitive(person.getInfoProfile().getMobileNo(), infoProfile.getProperty("mobileNo"));
        assertEqualsPrimitive(person.getInfoProfile().getName(), infoProfile.getProperty("name"));
        assertEqualsPrimitive(person.getInfoProfile().getDepartment(), infoProfile.getProperty("department"));
        assertEqualsPrimitive(person.getInfoProfile().getJobTitle(), infoProfile.getProperty("jobTitle"));
        assertEqualsPrimitive(person.getInfoProfile().getHomepageUrl(), infoProfile.getProperty("homepageUrl"));
        assertEqualsPrimitive(person.getInfoProfile().isFemale(), infoProfile.getProperty("female"));
        assertEqualsPrimitive(person.getInfoProfile().isMale(), infoProfile.getProperty("male"));
    }

    static void assertEqualsPhone(Phone expected, Object obj) {
        JavaBeanDescriptor descriptor = (JavaBeanDescriptor) obj;
        Assertions.assertTrue(descriptor.isBeanType());
        if (expected.getArea() != null) {
            assertEqualsPrimitive(expected.getArea(), descriptor.getProperty("area"));
        }
        if (expected.getCountry() != null) {
            assertEqualsPrimitive(expected.getCountry(), descriptor.getProperty("country"));
        }
        if (expected.getExtensionNumber() != null) {
            assertEqualsPrimitive(expected.getExtensionNumber(), descriptor.getProperty("extensionNumber"));
        }
        if (expected.getNumber() != null) {
            assertEqualsPrimitive(expected.getNumber(), descriptor.getProperty("number"));
        }
    }

    static void assertEqualsFullAddress(FullAddress expected, Object obj) {
        JavaBeanDescriptor descriptor = (JavaBeanDescriptor) obj;
        Assertions.assertTrue(descriptor.isBeanType());
        if (expected.getCityId() != null) {
            assertEqualsPrimitive(expected.getCityId(), descriptor.getProperty("cityId"));
        }
        if (expected.getCityName() != null) {
            assertEqualsPrimitive(expected.getCityName(), descriptor.getProperty("cityName"));
        }
        if (expected.getCountryId() != null) {
            assertEqualsPrimitive(expected.getCountryId(), descriptor.getProperty("countryId"));
        }
        if (expected.getCountryName() != null) {
            assertEqualsPrimitive(expected.getCountryName(), descriptor.getProperty("countryName"));
        }
        if (expected.getProvinceName() != null) {
            assertEqualsPrimitive(expected.getProvinceName(), descriptor.getProperty("provinceName"));
        }
        if (expected.getStreetAddress() != null) {
            assertEqualsPrimitive(expected.getStreetAddress(), descriptor.getProperty("streetAddress"));
        }
        if (expected.getZipCode() != null) {
            assertEqualsPrimitive(expected.getZipCode(), descriptor.getProperty("zipCode"));
        }
    }

    static BigPerson createBigPerson() {
        BigPerson bigPerson;
        bigPerson = new BigPerson();
        bigPerson.setPersonId("superman111");
        bigPerson.setLoginName("superman");
        bigPerson.setStatus(PersonStatus.ENABLED);
        bigPerson.setEmail("sm@1.com");
        bigPerson.setPenName("pname");
        ArrayList<Phone> phones = new ArrayList<Phone>();
        Phone phone1 = new Phone("86", "0571", "87654321", "001");
        Phone phone2 = new Phone("86", "0571", "87654322", "002");
        phones.add(phone1);
        phones.add(phone2);
        PersonInfo pi = new PersonInfo();
        pi.setPhones(phones);
        Phone fax = new Phone("86", "0571", "87654321", null);
        pi.setFax(fax);
        FullAddress addr = new FullAddress("CN", "zj", "3480", "wensanlu", "315000");
        pi.setFullAddress(addr);
        pi.setMobileNo("13584652131");
        pi.setMale(true);
        pi.setDepartment("b2b");
        pi.setHomepageUrl("www.capcom.com");
        pi.setJobTitle("qa");
        pi.setName("superman");
        bigPerson.setInfoProfile(pi);
        return bigPerson;
    }

    @Test
    void testConstructorArg_1() {
        Assertions.assertFalse((boolean) JavaBeanSerializeUtil.getConstructorArg(boolean.class));
    }

    @Test
    void testConstructorArg_2() {
        Assertions.assertFalse((boolean) JavaBeanSerializeUtil.getConstructorArg(Boolean.class));
    }

    @Test
    void testConstructorArg_7() {
        Assertions.assertEquals(0, JavaBeanSerializeUtil.getConstructorArg(int.class));
    }

    @Test
    void testConstructorArg_8() {
        Assertions.assertEquals(0, JavaBeanSerializeUtil.getConstructorArg(Integer.class));
    }

    @Test
    void testConstructorArg_16() {
        Assertions.assertEquals(new Character((char) 0), JavaBeanSerializeUtil.getConstructorArg(Character.class));
    }

    @Test
    void testConstructorArg_17() {
        Assertions.assertNull(JavaBeanSerializeUtil.getConstructorArg(JavaBeanSerializeUtil.class));
    }

    @ParameterizedTest
    @MethodSource("Provider_testConstructorArg_3_5_9_11_13_15")
    void testConstructorArg_3_5_9_11_13_15(int param1) {
        Assertions.assertEquals((byte) param1, JavaBeanSerializeUtil.getConstructorArg(byte.class));
    }

    static public Stream<Arguments> Provider_testConstructorArg_3_5_9_11_13_15() {
        return Stream.of(arguments(0), arguments(0), arguments(0), arguments(0), arguments(0), arguments(0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testConstructorArg_4_6_10_12_14")
    void testConstructorArg_4_6_10_12_14(int param1) {
        Assertions.assertEquals((byte) param1, JavaBeanSerializeUtil.getConstructorArg(Byte.class));
    }

    static public Stream<Arguments> Provider_testConstructorArg_4_6_10_12_14() {
        return Stream.of(arguments(0), arguments(0), arguments(0), arguments(0), arguments(0));
    }
}
