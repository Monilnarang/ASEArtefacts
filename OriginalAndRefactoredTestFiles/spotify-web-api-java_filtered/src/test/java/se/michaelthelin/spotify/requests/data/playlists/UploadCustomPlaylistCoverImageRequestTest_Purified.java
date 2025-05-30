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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.michaelthelin.spotify.Assertions.assertHasHeader;

public class UploadCustomPlaylistCoverImageRequestTest_Purified extends AbstractDataTest<String> {

    private final UploadCustomPlaylistCoverImageRequest defaultRequest = ITest.SPOTIFY_API.uploadCustomPlaylistCoverImage(ITest.ID_PLAYLIST).setHttpManager(TestUtil.MockedHttpManager.returningJson(null)).image_data(ITest.IMAGE_DATA != null ? ITest.IMAGE_DATA : "").build();

    public UploadCustomPlaylistCoverImageRequestTest() throws Exception {
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
        assertHasHeader(defaultRequest, "Content-Type", "image/jpeg");
    }

    @Test
    public void shouldComplyWithReference_3() {
        assertNotNull(defaultRequest.getBody());
    }

    @Test
    public void shouldComplyWithReference_4() {
        assertEquals("https://api.spotify.com:443/v1/playlists/3AGOiaoRXMSjswCLtuNqv5/images", defaultRequest.getUri().toString());
    }
}
