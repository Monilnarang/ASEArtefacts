package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.DatamodelMapper;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.SiteLink;

public class SiteLinkImplTest_Purified {

    private final ObjectMapper mapper = new DatamodelMapper("http://example.com/entity/");

    private final List<ItemIdValue> badges = Arrays.asList(new ItemIdValueImpl("Q43", "http://example.com/entity/"), new ItemIdValueImpl("Q42", "http://example.com/entity/"));

    private final SiteLink s1 = new SiteLinkImpl("Dresden", "enwiki", badges);

    private final SiteLink s2 = new SiteLinkImpl("Dresden", "enwiki", badges);

    private final String JSON_SITE_LINK = "{\"site\":\"enwiki\", \"title\":\"Dresden\", \"badges\":[\"Q42\",\"Q43\"]}";

    @Test
    public void fieldsIsCorrect_1() {
        assertEquals(s1.getPageTitle(), "Dresden");
    }

    @Test
    public void fieldsIsCorrect_2() {
        assertEquals(s1.getSiteKey(), "enwiki");
    }

    @Test
    public void fieldsIsCorrect_3() {
        assertEquals(s1.getBadges(), badges);
    }

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(s1, s1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(s1, s2);
    }

    @Test
    public void equalityBasedOnContent_3() {
        SiteLink sDiffTitle = new SiteLinkImpl("Berlin", "enwiki", badges);
        assertNotEquals(s1, sDiffTitle);
    }

    @Test
    public void equalityBasedOnContent_4() {
        SiteLink sDiffSiteKey = new SiteLinkImpl("Dresden", "dewiki", badges);
        assertNotEquals(s1, sDiffSiteKey);
    }

    @Test
    public void equalityBasedOnContent_5() {
        SiteLink sDiffBadges = new SiteLinkImpl("Dresden", "enwiki", Collections.emptyList());
        assertNotEquals(s1, sDiffBadges);
    }

    @Test
    public void equalityBasedOnContent_6() {
        assertNotEquals(s1, null);
    }

    @Test
    public void equalityBasedOnContent_7() {
        assertNotEquals(s1, this);
    }
}
