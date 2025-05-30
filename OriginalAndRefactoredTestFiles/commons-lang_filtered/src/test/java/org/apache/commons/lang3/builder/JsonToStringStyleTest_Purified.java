package org.apache.commons.lang3.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.builder.ToStringStyleTest.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonToStringStyleTest_Purified extends AbstractLangTest {

    static class AcademyClass {

        Teacher teacher;

        List<Student> students;

        public List<Student> getStudents() {
            return students;
        }

        public Teacher getTeacher() {
            return teacher;
        }

        public void setStudents(final List<Student> students) {
            this.students = students;
        }

        public void setTeacher(final Teacher teacher) {
            this.teacher = teacher;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

    enum EmptyEnum {
    }

    enum Hobby {

        SPORT, BOOK, MUSIC
    }

    static class InnerMapObject {

        String pid;

        Map<String, Object> map;
    }

    static class NestingPerson {

        String pid;

        Person person;
    }

    static class Student {

        List<Hobby> hobbies;

        public List<Hobby> getHobbies() {
            return hobbies;
        }

        public void setHobbies(final List<Hobby> hobbies) {
            this.hobbies = hobbies;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

    static class Teacher {

        Hobby[] hobbies;

        public Hobby[] getHobbies() {
            return hobbies;
        }

        public void setHobbies(final Hobby[] hobbies) {
            this.hobbies = hobbies;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

    private final Integer base = Integer.valueOf(5);

    @BeforeEach
    public void setUp() {
        ToStringBuilder.setDefaultStyle(ToStringStyle.JSON_STYLE);
    }

    @AfterEach
    public void tearDown() {
        ToStringBuilder.setDefaultStyle(ToStringStyle.DEFAULT_STYLE);
    }

    @Test
    public void testAppendSuper_1() {
        assertEquals("{}", new ToStringBuilder(base).appendSuper("Integer@8888[" + System.lineSeparator() + "]").toString());
    }

    @Test
    public void testAppendSuper_2() {
        assertEquals("{}", new ToStringBuilder(base).appendSuper("Integer@8888[" + System.lineSeparator() + "  null" + System.lineSeparator() + "]").toString());
    }

    @Test
    public void testAppendSuper_3() {
        assertEquals("{\"a\":\"hello\"}", new ToStringBuilder(base).appendSuper("Integer@8888[" + System.lineSeparator() + "]").append("a", "hello").toString());
    }

    @Test
    public void testAppendSuper_4() {
        assertEquals("{\"a\":\"hello\"}", new ToStringBuilder(base).appendSuper("Integer@8888[" + System.lineSeparator() + "  null" + System.lineSeparator() + "]").append("a", "hello").toString());
    }

    @Test
    public void testAppendSuper_5() {
        assertEquals("{\"a\":\"hello\"}", new ToStringBuilder(base).appendSuper(null).append("a", "hello").toString());
    }

    @Test
    public void testAppendSuper_6() {
        assertEquals("{\"a\":\"hello\",\"b\":\"world\"}", new ToStringBuilder(base).appendSuper("{\"a\":\"hello\"}").append("b", "world").toString());
    }

    @Test
    public void testLANG1395_1() {
        assertEquals("{\"name\":\"value\"}", new ToStringBuilder(base).append("name", "value").toString());
    }

    @Test
    public void testLANG1395_2() {
        assertEquals("{\"name\":\"\"}", new ToStringBuilder(base).append("name", "").toString());
    }

    @Test
    public void testLANG1395_3() {
        assertEquals("{\"name\":\"\\\"\"}", new ToStringBuilder(base).append("name", '"').toString());
    }

    @Test
    public void testLANG1395_4() {
        assertEquals("{\"name\":\"\\\\\"}", new ToStringBuilder(base).append("name", '\\').toString());
    }

    @Test
    public void testLANG1395_5() {
        assertEquals("{\"name\":\"Let's \\\"quote\\\" this\"}", new ToStringBuilder(base).append("name", "Let's \"quote\" this").toString());
    }
}
