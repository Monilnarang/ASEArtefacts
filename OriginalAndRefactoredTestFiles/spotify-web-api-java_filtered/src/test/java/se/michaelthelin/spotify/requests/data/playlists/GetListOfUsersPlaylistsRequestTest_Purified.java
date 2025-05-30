package se.michaelthelin.spotify.requests.data.playlists;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.ITest;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GetListOfUsersPlaylistsRequestTest_Purified extends AbstractDataTest<Paging<PlaylistSimplified>> {

    private final GetListOfUsersPlaylistsRequest defaultRequest = ITest.SPOTIFY_API.getListOfUsersPlaylists(ITest.ID_USER).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/playlists/GetListOfUsersPlaylistsRequest.json")).limit(ITest.LIMIT).offset(ITest.OFFSET).build();

    private final GetListOfUsersPlaylistsRequest requestWithUserIdWithSymbol$ = ITest.SPOTIFY_API.getListOfUsersPlaylists(ITest.ID_USER_WITH_$).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/playlists/GetListOfUsersPlaylistsRequest_UserWith$.json")).limit(ITest.LIMIT).offset(ITest.OFFSET).build();

    public GetListOfUsersPlaylistsRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final Paging<PlaylistSimplified> playlistSimplifiedPaging) {
        assertEquals("https://api.spotify.com/v1/users/wizzler/playlists", playlistSimplifiedPaging.getHref());
        assertEquals(2, playlistSimplifiedPaging.getItems().length);
        assertEquals(9, (int) playlistSimplifiedPaging.getLimit());
        assertNull(playlistSimplifiedPaging.getNext());
        assertEquals(0, (int) playlistSimplifiedPaging.getOffset());
        assertNull(playlistSimplifiedPaging.getPrevious());
        assertEquals(9, (int) playlistSimplifiedPaging.getTotal());
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/users/abbaspotify/playlists?limit=10&offset=0", defaultRequest.getUri().toString());
    }
}
