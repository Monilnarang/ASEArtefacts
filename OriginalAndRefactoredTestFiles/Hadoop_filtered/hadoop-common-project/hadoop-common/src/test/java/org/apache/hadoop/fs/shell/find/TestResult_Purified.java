package org.apache.hadoop.fs.shell.find;

import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.junit.Test;
import java.util.concurrent.TimeUnit;

public class TestResult_Purified {

    @Rule
    public Timeout globalTimeout = new Timeout(10000, TimeUnit.MILLISECONDS);

    @Test
    public void notEquals_1() {
        assertFalse(Result.PASS.equals(Result.FAIL));
    }

    @Test
    public void notEquals_2() {
        assertFalse(Result.PASS.equals(Result.STOP));
    }

    @Test
    public void notEquals_3() {
        assertFalse(Result.FAIL.equals(Result.PASS));
    }

    @Test
    public void notEquals_4() {
        assertFalse(Result.FAIL.equals(Result.STOP));
    }

    @Test
    public void notEquals_5() {
        assertFalse(Result.STOP.equals(Result.PASS));
    }

    @Test
    public void notEquals_6() {
        assertFalse(Result.STOP.equals(Result.FAIL));
    }
}
