package org.apache.storm.tuple;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FieldsTest_Purified {

    @Test
    public void fieldsConstructorDoesNotThrowWithValidArgsTest_1() {
        assertEquals(new Fields("foo", "bar").size(), 2);
    }

    @Test
    public void fieldsConstructorDoesNotThrowWithValidArgsTest_2() {
        assertEquals(new Fields("foo", "bar").size(), 2);
    }
}
