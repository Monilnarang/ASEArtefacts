package se.michaelthelin.spotify.requests.data.player;

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

public class StartResumeUsersPlaybackRequestTest_Purified extends AbstractDataTest<String> {

    private final StartResumeUsersPlaybackRequest defaultRequest = ITest.SPOTIFY_API.startResumeUsersPlayback().setHttpManager(TestUtil.MockedHttpManager.returningJson(null)).context_uri(ITest.CONTEXT_URI).device_id(ITest.DEVICE_ID).offset(ITest.OFFSET_JSON).uris(ITest.URIS).position_ms(ITest.POSITION_MS).build();

    public StartResumeUsersPlaybackRequestTest() throws Exception {
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
        assertHasHeader(defaultRequest, "Content-Type", "application/json");
    }

    @Test
    public void shouldComplyWithReference_3() {
        assertHasBodyParameter(defaultRequest, "context_uri", ITest.CONTEXT_URI);
    }

    @Test
    public void shouldComplyWithReference_4() {
        assertHasBodyParameter(defaultRequest, "uris", ITest.URIS);
    }

    @Test
    public void shouldComplyWithReference_5() {
        assertHasBodyParameter(defaultRequest, "offset", ITest.OFFSET_JSON);
    }

    @Test
    public void shouldComplyWithReference_6() {
        assertHasBodyParameter(defaultRequest, "position_ms", ITest.POSITION_MS);
    }

    @Test
    public void shouldComplyWithReference_7() {
        assertEquals("https://api.spotify.com:443/v1/me/player/play?device_id=5fbb3ba6aa454b5534c4ba43a8c7e8e45a63ad0e", defaultRequest.getUri().toString());
    }
}
