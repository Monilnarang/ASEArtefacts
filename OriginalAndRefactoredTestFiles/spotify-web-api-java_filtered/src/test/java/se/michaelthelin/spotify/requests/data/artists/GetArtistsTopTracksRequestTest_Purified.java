package se.michaelthelin.spotify.requests.data.artists;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.ITest;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetArtistsTopTracksRequestTest_Purified extends AbstractDataTest<Track[]> {

    private final GetArtistsTopTracksRequest defaultRequest = ITest.SPOTIFY_API.getArtistsTopTracks(ITest.ID_ARTIST, ITest.COUNTRY).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/artists/GetArtistsTopTracksRequest.json")).build();

    public GetArtistsTopTracksRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final Track[] tracks) {
        assertEquals(1, tracks.length);
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/artists/0LcJLqbBmaGUft1e9Mm8HV/top-tracks?country=SE", defaultRequest.getUri().toString());
    }
}
