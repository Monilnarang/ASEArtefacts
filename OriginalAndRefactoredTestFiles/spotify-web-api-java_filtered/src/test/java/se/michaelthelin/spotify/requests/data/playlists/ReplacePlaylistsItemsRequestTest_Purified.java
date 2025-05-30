package se.michaelthelin.spotify.requests.data.playlists;

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

public class ReplacePlaylistsItemsRequestTest_Purified extends AbstractDataTest<String> {

    private final ReplacePlaylistsItemsRequest defaultRequest = ITest.SPOTIFY_API.replacePlaylistsItems(ITest.ID_PLAYLIST, new String[] { "spotify:track:" + ITest.ID_TRACK, "spotify:track:" + ITest.ID_TRACK }).setHttpManager(TestUtil.MockedHttpManager.returningJson(null)).build();

    private final ReplacePlaylistsItemsRequest bodyRequest = ITest.SPOTIFY_API.replacePlaylistsItems(ITest.ID_PLAYLIST, ITest.TRACKS).setHttpManager(TestUtil.MockedHttpManager.returningJson(null)).build();

    public ReplacePlaylistsItemsRequestTest() throws Exception {
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
        assertEquals("https://api.spotify.com:443/v1/playlists/3AGOiaoRXMSjswCLtuNqv5/tracks?uris=spotify%3Atrack%3A01iyCAUm8EvOFqVWYJ3dVX%2Cspotify%3Atrack%3A01iyCAUm8EvOFqVWYJ3dVX", defaultRequest.getUri().toString());
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
        assertHasBodyParameter(bodyRequest, "uris", ITest.TRACKS);
    }

    @Test
    public void shouldComplyWithReference_6() {
        assertEquals("https://api.spotify.com:443/v1/playlists/3AGOiaoRXMSjswCLtuNqv5/tracks", bodyRequest.getUri().toString());
    }
}
