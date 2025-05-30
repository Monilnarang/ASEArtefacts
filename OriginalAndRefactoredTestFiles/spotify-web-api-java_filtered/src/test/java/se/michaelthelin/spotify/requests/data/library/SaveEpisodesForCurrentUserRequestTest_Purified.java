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

public class SaveEpisodesForCurrentUserRequestTest_Purified extends AbstractDataTest<String> {

    private final SaveEpisodesForCurrentUserRequest defaultRequest = ITest.SPOTIFY_API.saveEpisodesForCurrentUser(ITest.ID_EPISODE, ITest.ID_EPISODE).setHttpManager(TestUtil.MockedHttpManager.returningJson(null)).build();

    private final SaveEpisodesForCurrentUserRequest bodyRequest = ITest.SPOTIFY_API.saveEpisodesForCurrentUser(ITest.EPISODES).setHttpManager(TestUtil.MockedHttpManager.returningJson(null)).build();

    public SaveEpisodesForCurrentUserRequestTest() throws Exception {
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
        assertEquals("https://api.spotify.com:443/v1/me/episodes?ids=4GI3dxEafwap1sFiTGPKd1%2C4GI3dxEafwap1sFiTGPKd1", defaultRequest.getUri().toString());
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
        assertHasBodyParameter(bodyRequest, "ids", ITest.EPISODES);
    }

    @Test
    public void shouldComplyWithReference_6() {
        assertEquals("https://api.spotify.com:443/v1/me/episodes", bodyRequest.getUri().toString());
    }
}
