package se.michaelthelin.spotify.requests.data.library;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.ITest;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckUsersSavedTracksRequestTest_Purified extends AbstractDataTest<Boolean[]> {

    private final CheckUsersSavedTracksRequest defaultRequest = ITest.SPOTIFY_API.checkUsersSavedTracks(ITest.ID_TRACK, ITest.ID_TRACK).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/library/CheckUsersSavedTracksRequest.json")).build();

    public CheckUsersSavedTracksRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final Boolean[] booleans) {
        assertEquals(2, booleans.length);
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/me/tracks/contains?ids=01iyCAUm8EvOFqVWYJ3dVX%2C01iyCAUm8EvOFqVWYJ3dVX", defaultRequest.getUri().toString());
    }
}
