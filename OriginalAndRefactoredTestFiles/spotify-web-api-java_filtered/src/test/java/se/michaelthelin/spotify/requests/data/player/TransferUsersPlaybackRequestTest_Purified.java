package se.michaelthelin.spotify.requests.data.player;

import com.google.gson.JsonParser;
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

public class TransferUsersPlaybackRequestTest_Purified extends AbstractDataTest<String> {

    private final TransferUsersPlaybackRequest defaultRequest = ITest.SPOTIFY_API.transferUsersPlayback(JsonParser.parseString("[\"" + ITest.DEVICE_ID + "\"]").getAsJsonArray()).setHttpManager(TestUtil.MockedHttpManager.returningJson(null)).play(ITest.PLAY).build();

    public TransferUsersPlaybackRequestTest() throws Exception {
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
        assertHasBodyParameter(defaultRequest, "device_ids", "[\"" + ITest.DEVICE_ID + "\"]");
    }

    @Test
    public void shouldComplyWithReference_4() {
        assertHasBodyParameter(defaultRequest, "play", ITest.PLAY);
    }

    @Test
    public void shouldComplyWithReference_5() {
        assertEquals("https://api.spotify.com:443/v1/me/player", defaultRequest.getUri().toString());
    }
}
