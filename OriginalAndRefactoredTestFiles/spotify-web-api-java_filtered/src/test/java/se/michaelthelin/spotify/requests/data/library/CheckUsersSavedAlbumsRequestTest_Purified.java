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

public class CheckUsersSavedAlbumsRequestTest_Purified extends AbstractDataTest<Boolean[]> {

    private final CheckUsersSavedAlbumsRequest defaultRequest = ITest.SPOTIFY_API.checkUsersSavedAlbums(ITest.ID_ALBUM, ITest.ID_ALBUM).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/library/CheckUsersSavedAlbumsRequest.json")).build();

    public CheckUsersSavedAlbumsRequestTest() throws Exception {
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
        assertEquals("https://api.spotify.com:443/v1/me/albums/contains?ids=5zT1JLIj9E57p3e1rFm9Uq%2C5zT1JLIj9E57p3e1rFm9Uq", defaultRequest.getUri().toString());
    }
}
