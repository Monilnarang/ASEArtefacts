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
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.michaelthelin.spotify.Assertions.assertHasBodyParameter;
import static se.michaelthelin.spotify.Assertions.assertHasHeader;

public class SaveAlbumsForCurrentUserRequestTest_Purified extends AbstractDataTest<String> {

    private final SaveAlbumsForCurrentUserRequest defaultRequest = ITest.SPOTIFY_API.saveAlbumsForCurrentUser(ITest.ID_ALBUM, ITest.ID_ALBUM).setHttpManager(TestUtil.MockedHttpManager.returningJson(null)).build();

    private final SaveAlbumsForCurrentUserRequest bodyRequest = ITest.SPOTIFY_API.saveAlbumsForCurrentUser(ITest.ALBUMS.getAsJsonArray()).setHttpManager(TestUtil.MockedHttpManager.returningJson(null)).build();

    public SaveAlbumsForCurrentUserRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final String string) {
        assertNull(string);
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/me/albums?ids=5zT1JLIj9E57p3e1rFm9Uq%2C5zT1JLIj9E57p3e1rFm9Uq", defaultRequest.getUri().toString());
    }

    @Test
    public void shouldComplyWithReference_3() {
        assertHasAuthorizationHeader(bodyRequest);
    }

    @Test
    public void shouldComplyWithReference_4() {
        assertHasHeader(defaultRequest, "Content-Type", "application/json");
    }

    @Test
    public void shouldComplyWithReference_5() {
        assertHasBodyParameter(bodyRequest, "ids", ITest.ALBUMS);
    }

    @Test
    public void shouldComplyWithReference_6() {
        assertEquals("https://api.spotify.com:443/v1/me/albums", bodyRequest.getUri().toString());
    }
}
