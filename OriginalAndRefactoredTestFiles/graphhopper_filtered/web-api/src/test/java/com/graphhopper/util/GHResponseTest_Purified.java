package com.graphhopper.util;

import com.graphhopper.GHResponse;
import com.graphhopper.ResponsePath;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GHResponseTest_Purified {

    @Test
    public void testHasNoErrorIfEmpty_1() {
        assertFalse(new GHResponse().hasErrors());
    }

    @Test
    public void testHasNoErrorIfEmpty_2() {
        GHResponse rsp = new GHResponse();
        rsp.add(new ResponsePath());
        assertFalse(rsp.hasErrors());
    }
}
