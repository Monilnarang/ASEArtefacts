package se.michaelthelin.spotify.requests.data.playlists;

import com.google.gson.Gson;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.ITest;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.special.SnapshotResult;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.michaelthelin.spotify.Assertions.assertHasBodyParameter;
import static se.michaelthelin.spotify.Assertions.assertHasHeader;

public class AddItemsToPlaylistRequestTest_Purified extends AbstractDataTest<SnapshotResult> {

    private final AddItemsToPlaylistRequest defaultRequest = ITest.SPOTIFY_API.addItemsToPlaylist(ITest.ID_PLAYLIST, new Gson().fromJson(ITest.URIS, String[].class)).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/playlists/AddItemsToPlaylistRequest.json")).position(ITest.POSITION).build();

    private final AddItemsToPlaylistRequest bodyRequest = ITest.SPOTIFY_API.addItemsToPlaylist(ITest.ID_PLAYLIST, ITest.URIS).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/playlists/AddItemsToPlaylistRequest.json")).position(ITest.POSITION, true).build();

    public AddItemsToPlaylistRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final SnapshotResult snapshotResult) {
        assertEquals("JbtmHBDBAYu3/bt8BOXKjzKx3i0b6LCa/wVjyl6qQ2Yf6nFXkbmzuEa+ZI/U1yF+", snapshotResult.getSnapshotId());
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/playlists/3AGOiaoRXMSjswCLtuNqv5/tracks?uris=spotify%3Atrack%3A01iyCAUm8EvOFqVWYJ3dVX%2Cspotify%3Atrack%3A01iyCAUm8EvOFqVWYJ3dVX&position=0", defaultRequest.getUri().toString());
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
        assertHasBodyParameter(bodyRequest, "uris", ITest.URIS);
    }

    @Test
    public void shouldComplyWithReference_6() {
        assertHasBodyParameter(bodyRequest, "position", ITest.POSITION);
    }

    @Test
    public void shouldComplyWithReference_7() {
        assertEquals("https://api.spotify.com:443/v1/playlists/3AGOiaoRXMSjswCLtuNqv5/tracks", bodyRequest.getUri().toString());
    }
}
