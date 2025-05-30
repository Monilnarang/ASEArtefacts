package org.apache.dubbo.common.utils;

import org.apache.dubbo.common.model.Person;
import org.apache.dubbo.common.model.SerializablePerson;
import org.apache.dubbo.common.model.User;
import org.apache.dubbo.common.model.person.Ageneric;
import org.apache.dubbo.common.model.person.Bgeneric;
import org.apache.dubbo.common.model.person.BigPerson;
import org.apache.dubbo.common.model.person.Cgeneric;
import org.apache.dubbo.common.model.person.Dgeneric;
import org.apache.dubbo.common.model.person.FullAddress;
import org.apache.dubbo.common.model.person.PersonInfo;
import org.apache.dubbo.common.model.person.PersonMap;
import org.apache.dubbo.common.model.person.PersonStatus;
import org.apache.dubbo.common.model.person.Phone;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PojoUtilsTest_Purified {

    BigPerson bigPerson;

    {
        bigPerson = new BigPerson();
        bigPerson.setPersonId("id1");
        bigPerson.setLoginName("name1");
        bigPerson.setStatus(PersonStatus.ENABLED);
        bigPerson.setEmail("abc@123.com");
        bigPerson.setPenName("pname");
        ArrayList<Phone> phones = new ArrayList<Phone>();
        Phone phone1 = new Phone("86", "0571", "11223344", "001");
        Phone phone2 = new Phone("86", "0571", "11223344", "002");
        phones.add(phone1);
        phones.add(phone2);
        PersonInfo pi = new PersonInfo();
        pi.setPhones(phones);
        Phone fax = new Phone("86", "0571", "11223344", null);
        pi.setFax(fax);
        FullAddress addr = new FullAddress("CN", "zj", "1234", "Road1", "333444");
        pi.setFullAddress(addr);
        pi.setMobileNo("1122334455");
        pi.setMale(true);
        pi.setDepartment("b2b");
        pi.setHomepageUrl("www.abc.com");
        pi.setJobTitle("dev");
        pi.setName("name2");
        bigPerson.setInfoProfile(pi);
    }

    private static Child newChild(String name, int age) {
        Child result = new Child();
        result.setName(name);
        result.setAge(age);
        return result;
    }

    public void assertObject(Object data) {
        assertObject(data, null);
    }

    public void assertObject(Object data, Type type) {
        Object generalize = PojoUtils.generalize(data);
        Object realize = PojoUtils.realize(generalize, data.getClass(), type);
        assertEquals(data, realize);
    }

    public <T> void assertArrayObject(T[] data) {
        Object generalize = PojoUtils.generalize(data);
        @SuppressWarnings("unchecked")
        T[] realize = (T[]) PojoUtils.realize(generalize, data.getClass());
        assertArrayEquals(data, realize);
    }

    public void setMap(Map<Integer, Object> map) {
    }

    public void setListMap(List<Map<Integer, Object>> list) {
    }

    public List<Person> returnListPersonMethod() {
        return null;
    }

    public BigPerson returnBigPersonMethod() {
        return null;
    }

    public Type getType(String methodName) {
        Method method;
        try {
            method = getClass().getDeclaredMethod(methodName, new Class<?>[] {});
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        Type gtype = method.getGenericReturnType();
        return gtype;
    }

    public void setLong(long l) {
    }

    public void setInt(int l) {
    }

    public List<Parent> getListGenericType() {
        return null;
    }

    public Map<String, Parent> getMapGenericType() {
        return null;
    }

    protected PersonInfo createPersonInfoByName(String name) {
        PersonInfo dataPerson = new PersonInfo();
        dataPerson.setName(name);
        return dataPerson;
    }

    protected Ageneric<PersonInfo> createAGenericPersonInfo(String name) {
        Ageneric<PersonInfo> ret = new Ageneric();
        ret.setData(createPersonInfoByName(name));
        return ret;
    }

    protected Bgeneric<PersonInfo> createBGenericPersonInfo(String name) {
        Bgeneric<PersonInfo> ret = new Bgeneric();
        ret.setData(createPersonInfoByName(name));
        return ret;
    }

    protected Ageneric<Ageneric<PersonInfo>> createAGenericLoop(String name) {
        Ageneric<Ageneric<PersonInfo>> ret = new Ageneric();
        ret.setData(createAGenericPersonInfo(name));
        return ret;
    }

    protected Bgeneric<Ageneric<PersonInfo>> createBGenericWithAgeneric(String name) {
        Bgeneric<Ageneric<PersonInfo>> ret = new Bgeneric();
        ret.setData(createAGenericPersonInfo(name));
        return ret;
    }

    protected Cgeneric<PersonInfo> createCGenericPersonInfo(String name) {
        Cgeneric<PersonInfo> ret = new Cgeneric();
        ret.setData(createPersonInfoByName(name));
        ret.setA(createAGenericPersonInfo(name));
        ret.setB(createBGenericPersonInfo(name));
        return ret;
    }

    protected Dgeneric<Ageneric<PersonInfo>, Bgeneric<PersonInfo>, Cgeneric<PersonInfo>> createDGenericPersonInfo(String name) {
        Dgeneric<Ageneric<PersonInfo>, Bgeneric<PersonInfo>, Cgeneric<PersonInfo>> ret = new Dgeneric();
        ret.setT(createAGenericPersonInfo(name));
        ret.setY(createBGenericPersonInfo(name));
        ret.setZ(createCGenericPersonInfo(name));
        return ret;
    }

    class NameNotMatch implements Serializable {

        private String NameA;

        private String NameAbsent;

        public void setNameA(String nameA) {
            this.NameA = nameA;
        }

        public String getNameA() {
            return NameA;
        }

        public void setNameB(String nameB) {
            this.NameAbsent = nameB;
        }

        public String getNameB() {
            return NameAbsent;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            NameNotMatch that = (NameNotMatch) o;
            return Objects.equals(NameA, that.NameA) && Objects.equals(NameAbsent, that.NameAbsent);
        }

        @Override
        public int hashCode() {
            return Objects.hash(NameA, NameAbsent);
        }
    }

    public enum Day {

        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY
    }

    public static class BasicTestData implements Serializable {

        public boolean a;

        public char b;

        public byte c;

        public short d;

        public int e;

        public long f;

        public float g;

        public double h;

        public BasicTestData(boolean a, char b, byte c, short d, int e, long f, float g, double h) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.e = e;
            this.f = f;
            this.g = g;
            this.h = h;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (a ? 1 : 2);
            result = prime * result + b;
            result = prime * result + c;
            result = prime * result + c;
            result = prime * result + e;
            result = (int) (prime * result + f);
            result = (int) (prime * result + g);
            result = (int) (prime * result + h);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            BasicTestData other = (BasicTestData) obj;
            if (a != other.a) {
                return false;
            }
            if (b != other.b) {
                return false;
            }
            if (c != other.c) {
                return false;
            }
            if (e != other.e) {
                return false;
            }
            if (f != other.f) {
                return false;
            }
            if (g != other.g) {
                return false;
            }
            if (h != other.h) {
                return false;
            }
            return true;
        }
    }

    public static class Parent implements Serializable {

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

    public static class Child implements Serializable {

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

    public static class TestData implements Serializable {

        private Map<String, Child> children = new HashMap<String, Child>();

        private List<Child> list = new ArrayList<Child>();

        public List<Child> getList() {
            return list;
        }

        public void setList(List<Child> list) {
            if (CollectionUtils.isNotEmpty(list)) {
                this.list.addAll(list);
            }
        }

        public Map<String, Child> getChildren() {
            return children;
        }

        public void setChildren(Map<String, Child> children) {
            if (CollectionUtils.isNotEmptyMap(children)) {
                this.children.putAll(children);
            }
        }

        public void addChild(Child child) {
            this.children.put(child.getName(), child);
        }
    }

    public static class InnerPojo<T> implements Serializable {

        private List<T> list;

        public List<T> getList() {
            return list;
        }

        public void setList(List<T> list) {
            this.list = list;
        }
    }

    public static class ListResult<T> implements Serializable {

        List<T> result;

        public List<T> getResult() {
            return result;
        }

        public void setResult(List<T> result) {
            this.result = result;
        }
    }

    interface Message {

        String getContent();

        String getFrom();

        boolean isUrgent();
    }

    @Test
    void test_primitive_1() throws Exception {
        assertObject(Boolean.TRUE);
    }

    @Test
    void test_primitive_2() throws Exception {
        assertObject(Boolean.FALSE);
    }

    @Test
    void test_primitive_3() throws Exception {
        assertObject(Byte.valueOf((byte) 78));
    }

    @Test
    void test_primitive_4() throws Exception {
        assertObject('a');
    }

    @Test
    void test_primitive_5() throws Exception {
        assertObject('中');
    }

    @Test
    void test_primitive_6() throws Exception {
        assertObject(Short.valueOf((short) 37));
    }

    @Test
    void test_primitive_7() throws Exception {
        assertObject(78);
    }

    @Test
    void test_primitive_8() throws Exception {
        assertObject(123456789L);
    }

    @Test
    void test_primitive_9() throws Exception {
        assertObject(3.14F);
    }

    @Test
    void test_primitive_10() throws Exception {
        assertObject(3.14D);
    }

    @Test
    void test_pojo_1() throws Exception {
        assertObject(new Person());
    }

    @Test
    void test_pojo_2() throws Exception {
        assertObject(new BasicTestData(false, '\0', (byte) 0, (short) 0, 0, 0L, 0F, 0D));
    }

    @Test
    void test_pojo_3() throws Exception {
        assertObject(new SerializablePerson(Character.MIN_VALUE, false));
    }

    @Test
    void testIsPojo_1() throws Exception {
        assertFalse(PojoUtils.isPojo(boolean.class));
    }

    @Test
    void testIsPojo_2() throws Exception {
        assertFalse(PojoUtils.isPojo(Map.class));
    }

    @Test
    void testIsPojo_3() throws Exception {
        assertFalse(PojoUtils.isPojo(List.class));
    }

    @Test
    void testIsPojo_4() throws Exception {
        assertTrue(PojoUtils.isPojo(Person.class));
    }
}
