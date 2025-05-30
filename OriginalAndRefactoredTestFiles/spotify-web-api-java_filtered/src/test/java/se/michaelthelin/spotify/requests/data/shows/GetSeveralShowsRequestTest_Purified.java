package se.michaelthelin.spotify.requests.data.shows;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.ShowSimplified;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetSeveralShowsRequestTest_Purified extends AbstractDataTest<ShowSimplified[]> {

    private final GetSeveralShowsRequest defaultRequest = SPOTIFY_API.getSeveralShows(ID_SHOW, ID_SHOW).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/shows/GetSeveralShowsRequest.json")).build();

    public GetSeveralShowsRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final ShowSimplified[] shows) {
        assertEquals(2, shows.length);
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/shows?ids=5AvwZVawapvyhJUIx71pdJ%2C5AvwZVawapvyhJUIx71pdJ", defaultRequest.getUri().toString());
    }
}
