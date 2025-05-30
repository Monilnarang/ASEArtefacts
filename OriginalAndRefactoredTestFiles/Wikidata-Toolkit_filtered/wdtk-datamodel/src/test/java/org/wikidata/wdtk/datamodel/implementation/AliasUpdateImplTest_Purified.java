package org.wikidata.wdtk.datamodel.implementation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
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
import org.hamcrest.Matchers;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.AliasUpdateBuilder;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.interfaces.AliasUpdate;
import org.wikidata.wdtk.datamodel.interfaces.MonolingualTextValue;

public class AliasUpdateImplTest_Purified {

    private static final MonolingualTextValue EN = Datamodel.makeMonolingualTextValue("hello", "en");

    private static final MonolingualTextValue EN2 = Datamodel.makeMonolingualTextValue("hi", "en");

    private static final MonolingualTextValue EN3 = Datamodel.makeMonolingualTextValue("hey", "en");

    private static final MonolingualTextValue EN4 = Datamodel.makeMonolingualTextValue("howdy", "en");

    private static final MonolingualTextValue SK = Datamodel.makeMonolingualTextValue("ahoj", "sk");

    @Test
    public void testEmpty_1() {
        assertTrue(new AliasUpdateImpl(null, Collections.emptyList(), Collections.emptyList()).isEmpty());
    }

    @Test
    public void testEmpty_2() {
        assertFalse(new AliasUpdateImpl(null, Arrays.asList(EN), Collections.emptyList()).isEmpty());
    }

    @Test
    public void testEmpty_3() {
        assertFalse(new AliasUpdateImpl(null, Collections.emptyList(), Arrays.asList(EN)).isEmpty());
    }

    @Test
    public void testEmpty_4() {
        assertFalse(new AliasUpdateImpl(Arrays.asList(EN), Collections.emptyList(), Collections.emptyList()).isEmpty());
    }

    @Test
    public void testEmpty_5() {
        assertFalse(new AliasUpdateImpl(Collections.emptyList(), Collections.emptyList(), Collections.emptyList()).isEmpty());
    }

    @Test
    public void testJson_1() {
        assertThat(AliasUpdateBuilder.create().build(), producesJson("null"));
    }

    @Test
    public void testJson_2() {
        assertThat(AliasUpdateBuilder.create().recreate(Collections.emptyList()).build(), producesJson("[]"));
    }

    @Test
    public void testJson_3() {
        assertThat(AliasUpdateBuilder.create().recreate(Arrays.asList(EN, EN2)).build(), producesJson("[{'language':'en','value':'hello'},{'language':'en','value':'hi'}]"));
    }

    @Test
    public void testJson_4() {
        assertThat(AliasUpdateBuilder.create().add(EN).build(), producesJson("[{'add':'','language':'en','value':'hello'}]"));
    }

    @Test
    public void testJson_5() {
        assertThat(AliasUpdateBuilder.create().remove(EN).build(), producesJson("[{'language':'en','remove':'','value':'hello'}]"));
    }
}
