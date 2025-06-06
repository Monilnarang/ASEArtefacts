package se.michaelthelin.spotify.requests.data.artists;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.ITest;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetArtistsRelatedArtistsRequestTest_Purified extends AbstractDataTest<Artist[]> {

    private final GetArtistsRelatedArtistsRequest defaultRequest = ITest.SPOTIFY_API.getArtistsRelatedArtists(ITest.ID_ARTIST).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/artists/GetArtistsRelatedArtistsRequest.json")).build();

    public GetArtistsRelatedArtistsRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final Artist[] artists) {
        assertEquals(1, artists.length);
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/artists/0LcJLqbBmaGUft1e9Mm8HV/related-artists", defaultRequest.getUri().toString());
    }
}
