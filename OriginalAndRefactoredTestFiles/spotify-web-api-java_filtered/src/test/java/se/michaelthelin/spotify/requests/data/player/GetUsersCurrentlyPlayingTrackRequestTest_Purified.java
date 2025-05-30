package se.michaelthelin.spotify.requests.data.player;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.ITest;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.enums.CurrentlyPlayingType;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import se.michaelthelin.spotify.model_objects.specification.Episode;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.*;

public class GetUsersCurrentlyPlayingTrackRequestTest_Purified extends AbstractDataTest<CurrentlyPlaying> {

    private final GetUsersCurrentlyPlayingTrackRequest defaultRequest = ITest.SPOTIFY_API.getUsersCurrentlyPlayingTrack().setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/player/GetUsersCurrentlyPlayingTrackRequest.json")).market(ITest.MARKET).additionalTypes(ITest.ADDITIONAL_TYPES).build();

    private final GetUsersCurrentlyPlayingTrackRequest defaultEpisodeRequest = ITest.SPOTIFY_API.getUsersCurrentlyPlayingTrack().setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/player/GetUsersCurrentlyPlayingTrackRequest_Episode.json")).market(ITest.MARKET).additionalTypes(ITest.ADDITIONAL_TYPES).build();

    private final GetUsersCurrentlyPlayingTrackRequest emptyRequest = ITest.SPOTIFY_API.getUsersCurrentlyPlayingTrack().setHttpManager(TestUtil.MockedHttpManager.returningJson(null)).market(ITest.MARKET).additionalTypes(ITest.ADDITIONAL_TYPES).build();

    public GetUsersCurrentlyPlayingTrackRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final CurrentlyPlaying currentlyPlaying) {
        assertNull(currentlyPlaying.getContext());
        assertEquals(1516669900630L, (long) currentlyPlaying.getTimestamp());
        assertEquals(78810, (int) currentlyPlaying.getProgress_ms());
        assertFalse(currentlyPlaying.getIs_playing());
        assertNotNull(currentlyPlaying.getItem());
        assertTrue(currentlyPlaying.getItem() instanceof Track);
        assertNotNull(currentlyPlaying.getActions());
        assertEquals(4, currentlyPlaying.getActions().getDisallows().getDisallowedActions().size());
        assertEquals(CurrentlyPlayingType.TRACK, currentlyPlaying.getCurrentlyPlayingType());
    }

    public void shouldReturnDefaultEpisode(final CurrentlyPlaying currentlyPlaying) {
        assertEquals(1516669848357L, (long) currentlyPlaying.getTimestamp());
        assertNotNull(currentlyPlaying.getContext());
        assertEquals(currentlyPlaying.getContext().getType(), ModelObjectType.SHOW);
        assertEquals(3636145, (int) currentlyPlaying.getProgress_ms());
        assertNotNull(currentlyPlaying.getItem());
        assertTrue(currentlyPlaying.getItem() instanceof Episode);
        assertEquals(CurrentlyPlayingType.EPISODE, currentlyPlaying.getCurrentlyPlayingType());
        assertNotNull(currentlyPlaying.getActions());
        assertEquals(4, currentlyPlaying.getActions().getDisallows().getDisallowedActions().size());
        assertTrue(currentlyPlaying.getIs_playing());
    }

    public void shouldReturnEmpty(final CurrentlyPlaying currentlyPlaying) {
        assertNull(currentlyPlaying);
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/me/player/currently-playing?market=SE&additional_types=track%2Cepisode", defaultRequest.getUri().toString());
    }
}
