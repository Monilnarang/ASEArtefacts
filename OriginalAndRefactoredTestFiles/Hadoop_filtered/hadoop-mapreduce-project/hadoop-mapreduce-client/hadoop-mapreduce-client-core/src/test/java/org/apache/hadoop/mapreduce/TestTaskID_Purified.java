package org.apache.hadoop.mapreduce;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.nio.ByteBuffer;
import org.apache.hadoop.io.DataInputByteBuffer;
import org.apache.hadoop.io.WritableUtils;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTaskID_Purified {

    @Test
    public void testGetRepresentingCharacter_1() {
        assertEquals("The getRepresentingCharacter() method did not return the " + "expected character", 'm', TaskID.getRepresentingCharacter(TaskType.MAP));
    }

    @Test
    public void testGetRepresentingCharacter_2() {
        assertEquals("The getRepresentingCharacter() method did not return the " + "expected character", 'r', TaskID.getRepresentingCharacter(TaskType.REDUCE));
    }

    @Test
    public void testGetRepresentingCharacter_3() {
        assertEquals("The getRepresentingCharacter() method did not return the " + "expected character", 's', TaskID.getRepresentingCharacter(TaskType.JOB_SETUP));
    }

    @Test
    public void testGetRepresentingCharacter_4() {
        assertEquals("The getRepresentingCharacter() method did not return the " + "expected character", 'c', TaskID.getRepresentingCharacter(TaskType.JOB_CLEANUP));
    }

    @Test
    public void testGetRepresentingCharacter_5() {
        assertEquals("The getRepresentingCharacter() method did not return the " + "expected character", 't', TaskID.getRepresentingCharacter(TaskType.TASK_CLEANUP));
    }

    @Test
    public void testGetTaskTypeChar_1() {
        assertEquals("The getTaskType() method did not return the expected type", TaskType.MAP, TaskID.getTaskType('m'));
    }

    @Test
    public void testGetTaskTypeChar_2() {
        assertEquals("The getTaskType() method did not return the expected type", TaskType.REDUCE, TaskID.getTaskType('r'));
    }

    @Test
    public void testGetTaskTypeChar_3() {
        assertEquals("The getTaskType() method did not return the expected type", TaskType.JOB_SETUP, TaskID.getTaskType('s'));
    }

    @Test
    public void testGetTaskTypeChar_4() {
        assertEquals("The getTaskType() method did not return the expected type", TaskType.JOB_CLEANUP, TaskID.getTaskType('c'));
    }

    @Test
    public void testGetTaskTypeChar_5() {
        assertEquals("The getTaskType() method did not return the expected type", TaskType.TASK_CLEANUP, TaskID.getTaskType('t'));
    }

    @Test
    public void testGetTaskTypeChar_6() {
        assertNull("The getTaskType() method did not return null for an unknown " + "type", TaskID.getTaskType('x'));
    }
}
