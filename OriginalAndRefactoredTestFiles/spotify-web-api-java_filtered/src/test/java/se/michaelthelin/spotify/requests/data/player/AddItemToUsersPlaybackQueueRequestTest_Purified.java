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
import static se.michaelthelin.spotify.Assertions.assertHasHeader;

public class AddItemToUsersPlaybackQueueRequestTest_Purified extends AbstractDataTest<String> {

    private final AddItemToUsersPlaybackQueueRequest defaultRequest = ITest.SPOTIFY_API.addItemToUsersPlaybackQueue("spotify:track:" + ITest.ID_TRACK).setHttpManager(TestUtil.MockedHttpManager.returningJson(null)).device_id(ITest.DEVICE_ID).build();

    public AddItemToUsersPlaybackQueueRequestTest() throws Exception {
    }

    public void shouldReturnDefault(String string) {
        assertNull(string);
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasHeader(defaultRequest, "Content-Type", "application/json");
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/me/player/queue?uri=spotify%3Atrack%3A01iyCAUm8EvOFqVWYJ3dVX&device_id=5fbb3ba6aa454b5534c4ba43a8c7e8e45a63ad0e", defaultRequest.getUri().toString());
    }
}
