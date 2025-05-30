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
import static se.michaelthelin.spotify.Assertions.assertHasHeader;

public class ReorderPlaylistsItemsRequestTest_Purified extends AbstractDataTest<SnapshotResult> {

    private final ReorderPlaylistsItemsRequest defaultRequest = ITest.SPOTIFY_API.reorderPlaylistsItems(ITest.ID_PLAYLIST, ITest.RANGE_START, ITest.INSERT_BEFORE).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/playlists/ReorderPlaylistsItemsRequest.json")).range_length(ITest.RANGE_LENGTH).snapshot_id(ITest.SNAPSHOT_ID).build();

    public ReorderPlaylistsItemsRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final SnapshotResult snapshotResult) {
        assertEquals("KsWY41k+zLqbx7goYX9zr+2IUZQtqbBNfk4ZOgEpIurvab4VSHhEL2L4za8HW6D0", snapshotResult.getSnapshotId());
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
        assertHasBodyParameter(defaultRequest, "range_start", ITest.RANGE_START);
    }

    @Test
    public void shouldComplyWithReference_4() {
        assertHasBodyParameter(defaultRequest, "range_length", ITest.RANGE_LENGTH);
    }

    @Test
    public void shouldComplyWithReference_5() {
        assertHasBodyParameter(defaultRequest, "insert_before", ITest.INSERT_BEFORE);
    }

    @Test
    public void shouldComplyWithReference_6() {
        assertHasBodyParameter(defaultRequest, "snapshot_id", ITest.SNAPSHOT_ID);
    }

    @Test
    public void shouldComplyWithReference_7() {
        assertEquals("https://api.spotify.com:443/v1/playlists/3AGOiaoRXMSjswCLtuNqv5/tracks", defaultRequest.getUri().toString());
    }
}
