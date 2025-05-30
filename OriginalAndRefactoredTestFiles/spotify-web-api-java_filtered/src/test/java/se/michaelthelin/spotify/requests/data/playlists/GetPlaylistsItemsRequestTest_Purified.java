package se.michaelthelin.spotify.requests.data.playlists;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.ITest;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Episode;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.*;

public class GetPlaylistsItemsRequestTest_Purified extends AbstractDataTest<Paging<PlaylistTrack>> {

    private final GetPlaylistsItemsRequest defaultRequest = ITest.SPOTIFY_API.getPlaylistsItems(ITest.ID_PLAYLIST).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/playlists/GetPlaylistsItemsRequest.json")).fields(ITest.FIELDS).limit(ITest.LIMIT).market(ITest.MARKET).offset(ITest.OFFSET).additionalTypes(ITest.ADDITIONAL_TYPES).build();

    private final GetPlaylistsItemsRequest defaultEpisodeRequest = ITest.SPOTIFY_API.getPlaylistsItems(ITest.ID_PLAYLIST).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/playlists/GetPlaylistsItemsRequest_Episode.json")).fields(ITest.FIELDS).limit(ITest.LIMIT).market(ITest.MARKET).offset(ITest.OFFSET).additionalTypes(ITest.ADDITIONAL_TYPES).build();

    public GetPlaylistsItemsRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final Paging<PlaylistTrack> playlistTrackPaging) {
        assertEquals("https://api.spotify.com/v1/users/spotify_espa%C3%B1a/playlists/21THa8j9TaSGuXYNBU5tsC/tracks", playlistTrackPaging.getHref());
        assertEquals(2, playlistTrackPaging.getItems().length);
        assertTrue(playlistTrackPaging.getItems()[0].getTrack() instanceof Track);
        assertEquals(100, (int) playlistTrackPaging.getLimit());
        assertNull(playlistTrackPaging.getNext());
        assertEquals(0, (int) playlistTrackPaging.getOffset());
        assertNull(playlistTrackPaging.getPrevious());
        assertEquals(58, (int) playlistTrackPaging.getTotal());
    }

    public void shouldReturnDefaultEpisode(final Paging<PlaylistTrack> playlistTrackPaging) {
        assertEquals("https://api.spotify.com/v1/playlists/21THa8j9TaSGuXYNBU5tsC/tracks?offset=0&limit=100&additional_types=episode", playlistTrackPaging.getHref());
        assertEquals(1, playlistTrackPaging.getItems().length);
        assertTrue(playlistTrackPaging.getItems()[0].getTrack() instanceof Episode);
        assertEquals(100, (int) playlistTrackPaging.getLimit());
        assertNull(playlistTrackPaging.getNext());
        assertEquals(0, (int) playlistTrackPaging.getOffset());
        assertNull(playlistTrackPaging.getPrevious());
        assertEquals(58, (int) playlistTrackPaging.getTotal());
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/playlists/3AGOiaoRXMSjswCLtuNqv5/tracks?fields=description&limit=10&market=SE&offset=0&additional_types=track%2Cepisode", defaultRequest.getUri().toString());
    }
}
