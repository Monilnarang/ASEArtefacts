package se.michaelthelin.spotify.requests.data.tracks;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.ITest;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.*;

public class GetTrackRequestTest_Purified extends AbstractDataTest<Track> {

    private final GetTrackRequest defaultRequest = ITest.SPOTIFY_API.getTrack(ITest.ID_TRACK).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/tracks/GetTrackRequest.json")).market(ITest.MARKET).build();

    public GetTrackRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final Track track) {
        assertNotNull(track.getAlbum(), "");
        assertNotNull(track.getArtists(), "");
        assertEquals(57, track.getAvailableMarkets().length);
        assertEquals(1, (int) track.getDiscNumber());
        assertEquals(222200, (int) track.getDurationMs());
        assertFalse(track.getIsExplicit());
        assertNotNull(track.getExternalIds());
        assertNotNull(track.getExternalUrls());
        assertEquals("https://api.spotify.com/v1/tracks/3n3Ppam7vgaVa1iaRUc9Lp", track.getHref());
        assertEquals("3n3Ppam7vgaVa1iaRUc9Lp", track.getId());
        assertEquals("Mr. Brightside", track.getName());
        assertEquals(73, (int) track.getPopularity());
        assertEquals("https://p.scdn.co/mp3-preview/4839b070015ab7d6de9fec1756e1f3096d908fba", track.getPreviewUrl());
        assertEquals(2, (int) track.getTrackNumber());
        assertEquals(ModelObjectType.TRACK, track.getType());
        assertEquals("spotify:track:3n3Ppam7vgaVa1iaRUc9Lp", track.getUri());
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/tracks/01iyCAUm8EvOFqVWYJ3dVX?market=SE", defaultRequest.getUri().toString());
    }
}
