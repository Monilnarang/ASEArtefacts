package se.michaelthelin.spotify.requests.data.episodes;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.ITest;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Episode;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetSeveralEpisodesRequestTest_Purified extends AbstractDataTest<Episode[]> {

    private final GetSeveralEpisodesRequest defaultRequest = ITest.SPOTIFY_API.getSeveralEpisodes(ITest.ID_EPISODE, ITest.ID_EPISODE).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/episodes/GetSeveralEpisodesRequest.json")).market(ITest.MARKET).build();

    public GetSeveralEpisodesRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final Episode[] episodes) {
        assertEquals(2, episodes.length);
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/episodes?ids=4GI3dxEafwap1sFiTGPKd1%2C4GI3dxEafwap1sFiTGPKd1&market=SE", defaultRequest.getUri().toString());
    }
}
