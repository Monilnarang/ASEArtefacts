package se.michaelthelin.spotify.requests.data.playlists;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.ITest;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetPlaylistCoverImageRequestTest_Purified extends AbstractDataTest<Image[]> {

    private final GetPlaylistCoverImageRequest defaultRequest = ITest.SPOTIFY_API.getPlaylistCoverImage(ITest.ID_PLAYLIST).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/playlists/GetPlaylistCoverImageRequest.json")).build();

    public GetPlaylistCoverImageRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final Image[] images) {
        assertEquals(1, images.length);
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/playlists/3AGOiaoRXMSjswCLtuNqv5/images", defaultRequest.getUri().toString());
    }
}
