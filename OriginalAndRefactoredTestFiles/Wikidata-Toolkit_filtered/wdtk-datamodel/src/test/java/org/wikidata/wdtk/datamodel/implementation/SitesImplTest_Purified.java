package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.interfaces.SiteLink;

public class SitesImplTest_Purified {

    private SitesImpl sites;

    @Before
    public void setUp() {
        this.sites = new SitesImpl();
        this.sites.setSiteInformation("enwiki", "wikipedia", "en", "mediawiki", "http://en.wikipedia.org/w/$1", "http://en.wikipedia.org/wiki/$1");
        this.sites.setSiteInformation("dewiki", "wikipedia", "de", "mediawiki", "//de.wikipedia.org/w/$1", "//de.wikipedia.org/wiki/$1");
        this.sites.setSiteInformation("somesite", "group", "language", "something else", "http://example.org/file/$1", "http://example.org/page/$1");
    }

    @Test
    public void unknownSiteKey_1() {
        assertNull(this.sites.getGroup("somekey"));
    }

    @Test
    public void unknownSiteKey_2() {
        assertNull(this.sites.getSiteType("somekey"));
    }

    @Test
    public void unknownSiteKey_3() {
        assertNull(this.sites.getLanguageCode("somekey"));
    }

    @Test
    public void unknownSiteKey_4() {
        assertNull(this.sites.getFileUrl("somekey", "filename"));
    }

    @Test
    public void unknownSiteKey_5() {
        assertNull(this.sites.getPageUrl("somekey", "page name"));
    }

    @Test
    public void knownSiteKey_1() {
        assertEquals(this.sites.getGroup("enwiki"), "wikipedia");
    }

    @Test
    public void knownSiteKey_2() {
        assertEquals(this.sites.getSiteType("enwiki"), "mediawiki");
    }

    @Test
    public void knownSiteKey_3() {
        assertEquals(this.sites.getLanguageCode("enwiki"), "en");
    }

    @Test
    public void knownSiteKey_4() {
        assertEquals(this.sites.getFileUrl("enwiki", "filename"), "http://en.wikipedia.org/w/filename");
    }

    @Test
    public void knownSiteKey_5() {
        assertEquals(this.sites.getPageUrl("enwiki", "Page name"), "http://en.wikipedia.org/wiki/Page_name");
    }

    @Test
    public void knownSiteKey_6() {
        assertEquals(this.sites.getPageUrl("somesite", "Page name"), "http://example.org/page/Page+name");
    }
}
