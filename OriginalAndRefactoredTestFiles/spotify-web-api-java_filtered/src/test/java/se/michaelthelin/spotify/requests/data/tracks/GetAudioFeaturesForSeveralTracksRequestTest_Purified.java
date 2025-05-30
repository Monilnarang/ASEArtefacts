package se.michaelthelin.spotify.requests.data.tracks;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.ITest;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetAudioFeaturesForSeveralTracksRequestTest_Purified extends AbstractDataTest<AudioFeatures[]> {

    private final GetAudioFeaturesForSeveralTracksRequest defaultRequest = ITest.SPOTIFY_API.getAudioFeaturesForSeveralTracks(ITest.ID_TRACK, ITest.ID_TRACK).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/tracks/GetAudioFeaturesForSeveralTracksRequest.json")).build();

    public GetAudioFeaturesForSeveralTracksRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final AudioFeatures[] audioFeatures) {
        assertEquals(3, audioFeatures.length);
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/audio-features?ids=01iyCAUm8EvOFqVWYJ3dVX%2C01iyCAUm8EvOFqVWYJ3dVX", defaultRequest.getUri().toString());
    }
}
