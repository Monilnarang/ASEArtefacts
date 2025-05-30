package org.wikidata.wdtk.datamodel.implementation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.wikidata.wdtk.datamodel.implementation.JsonTestUtils.producesJson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.TermUpdateBuilder;
import org.wikidata.wdtk.datamodel.interfaces.MonolingualTextValue;
import org.wikidata.wdtk.datamodel.interfaces.TermUpdate;

public class TermUpdateImplTest_Purified {

    private static final MonolingualTextValue EN = Datamodel.makeMonolingualTextValue("hello", "en");

    private static final MonolingualTextValue EN2 = Datamodel.makeMonolingualTextValue("hi", "en");

    private static final MonolingualTextValue SK = Datamodel.makeMonolingualTextValue("ahoj", "sk");

    private static final MonolingualTextValue CS = Datamodel.makeMonolingualTextValue("nazdar", "cs");

    @Test
    public void testEmpty_1() {
        assertTrue(new TermUpdateImpl(Collections.emptyList(), Collections.emptyList()).isEmpty());
    }

    @Test
    public void testEmpty_2() {
        assertFalse(new TermUpdateImpl(Arrays.asList(EN), Collections.emptyList()).isEmpty());
    }

    @Test
    public void testEmpty_3() {
        assertFalse(new TermUpdateImpl(Collections.emptyList(), Arrays.asList("sk")).isEmpty());
    }

    @Test
    public void testJson_1() {
        assertThat(TermUpdateBuilder.create().build(), producesJson("{}"));
    }

    @Test
    public void testJson_2() {
        assertThat(TermUpdateBuilder.create().put(EN).build(), producesJson("{'en':{'language':'en','value':'hello'}}"));
    }

    @Test
    public void testJson_3() {
        assertThat(TermUpdateBuilder.create().remove("en").build(), producesJson("{'en':{'language':'en','remove':''}}"));
    }
}
