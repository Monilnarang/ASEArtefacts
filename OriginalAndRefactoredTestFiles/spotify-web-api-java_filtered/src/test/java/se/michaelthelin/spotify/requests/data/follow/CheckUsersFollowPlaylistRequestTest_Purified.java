package se.michaelthelin.spotify.requests.data.follow;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckUsersFollowPlaylistRequestTest_Purified extends AbstractDataTest<Boolean[]> {

    private final CheckUsersFollowPlaylistRequest defaultRequest = SPOTIFY_API.checkUsersFollowPlaylist(ID_PLAYLIST, new String[] { ID_USER, ID_USER }).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/follow/CheckUsersFollowPlaylistRequest.json")).build();

    public CheckUsersFollowPlaylistRequestTest() throws Exception {
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
        assertEquals("https://api.spotify.com:443/v1/playlists/3AGOiaoRXMSjswCLtuNqv5/followers/contains?ids=abbaspotify%2Cabbaspotify", defaultRequest.getUri().toString());
    }
}
