package se.michaelthelin.spotify.requests.data.playlists;

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

public class RemoveItemsFromPlaylistRequestTest_Purified extends AbstractDataTest<SnapshotResult> {

    private final RemoveItemsFromPlaylistRequest defaultRequest = ITest.SPOTIFY_API.removeItemsFromPlaylist(ITest.ID_PLAYLIST, ITest.TRACKS).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/playlists/RemoveItemsFromPlaylistRequest.json")).snapshotId(ITest.SNAPSHOT_ID).build();

    public RemoveItemsFromPlaylistRequestTest() throws Exception {
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
        assertHasBodyParameter(defaultRequest, "tracks", ITest.TRACKS);
    }

    @Test
    public void shouldComplyWithReference_3() {
        assertEquals("https://api.spotify.com:443/v1/playlists/3AGOiaoRXMSjswCLtuNqv5/tracks", defaultRequest.getUri().toString());
    }
}
