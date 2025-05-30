package org.apache.hadoop.util;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.hadoop.conf.Configuration;

public class TestGenericsUtil_Purified {

    private class GenericClass<T> {

        T dummy;

        List<T> list = new ArrayList<T>();

        void add(T item) {
            list.add(item);
        }

        T[] funcThatUsesToArray() {
            T[] arr = GenericsUtil.toArray(list);
            return arr;
        }
    }

    @Test
    public void testIsLog4jLogger_1() throws Exception {
        assertFalse("False if clazz is null", GenericsUtil.isLog4jLogger((Class<?>) null));
    }

    @Test
    public void testIsLog4jLogger_2() throws Exception {
        assertTrue("The implementation is Log4j", GenericsUtil.isLog4jLogger(TestGenericsUtil.class));
    }
}
