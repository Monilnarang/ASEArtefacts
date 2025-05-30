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

public class ChangePlaylistsDetailsRequestTest_Purified extends AbstractDataTest<String> {

    private final ChangePlaylistsDetailsRequest defaultRequest = ITest.SPOTIFY_API.changePlaylistsDetails(ITest.ID_PLAYLIST).setHttpManager(TestUtil.MockedHttpManager.returningJson(null)).collaborative(ITest.COLLABORATIVE).description(ITest.DESCRIPTION).name(ITest.NAME).public_(ITest.PUBLIC).build();

    public ChangePlaylistsDetailsRequestTest() throws Exception {
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
        assertHasBodyParameter(defaultRequest, "name", ITest.NAME);
    }

    @Test
    public void shouldComplyWithReference_4() {
        assertHasBodyParameter(defaultRequest, "public", ITest.PUBLIC);
    }

    @Test
    public void shouldComplyWithReference_5() {
        assertHasBodyParameter(defaultRequest, "collaborative", ITest.COLLABORATIVE);
    }

    @Test
    public void shouldComplyWithReference_6() {
        assertHasBodyParameter(defaultRequest, "description", ITest.DESCRIPTION);
    }

    @Test
    public void shouldComplyWithReference_7() {
        assertEquals("https://api.spotify.com:443/v1/playlists/3AGOiaoRXMSjswCLtuNqv5", defaultRequest.getUri().toString());
    }
}
