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

public class GetSeveralArtistsRequestTest_Purified extends AbstractDataTest<Artist[]> {

    private final GetSeveralArtistsRequest defaultRequest = ITest.SPOTIFY_API.getSeveralArtists(ITest.ID_ARTIST, ITest.ID_ARTIST).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/artists/GetSeveralArtistsRequest.json")).build();

    public GetSeveralArtistsRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final Artist[] artists) {
        assertEquals(2, artists.length);
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/artists?ids=0LcJLqbBmaGUft1e9Mm8HV%2C0LcJLqbBmaGUft1e9Mm8HV", defaultRequest.getUri().toString());
    }
}
