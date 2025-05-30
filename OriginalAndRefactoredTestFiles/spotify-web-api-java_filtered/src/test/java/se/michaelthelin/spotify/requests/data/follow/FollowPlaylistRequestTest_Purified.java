package se.michaelthelin.spotify.requests.data.follow;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.michaelthelin.spotify.Assertions.assertHasBodyParameter;
import static se.michaelthelin.spotify.Assertions.assertHasHeader;

public class FollowPlaylistRequestTest_Purified extends AbstractDataTest<String> {

    private final FollowPlaylistRequest defaultRequest = SPOTIFY_API.followPlaylist(ID_PLAYLIST, PUBLIC).setHttpManager(TestUtil.MockedHttpManager.returningJson(null)).build();

    public FollowPlaylistRequestTest() throws Exception {
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
        assertHasBodyParameter(defaultRequest, "public", PUBLIC);
    }

    @Test
    public void shouldComplyWithReference_4() {
        assertEquals("https://api.spotify.com:443/v1/playlists/3AGOiaoRXMSjswCLtuNqv5/followers", defaultRequest.getUri().toString());
    }
}
