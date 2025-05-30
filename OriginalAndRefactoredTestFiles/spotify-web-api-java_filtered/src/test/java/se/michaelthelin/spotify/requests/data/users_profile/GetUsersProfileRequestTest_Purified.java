package se.michaelthelin.spotify.requests.data.users_profile;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.ITest;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetUsersProfileRequestTest_Purified extends AbstractDataTest<User> {

    private final GetUsersProfileRequest defaultRequest = ITest.SPOTIFY_API.getUsersProfile(ITest.ID_USER_NON_ASCII).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/users_profile/GetUsersProfileRequest.json")).build();

    public GetUsersProfileRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final User user) {
        assertEquals("Lilla Namo", user.getDisplayName());
        assertNotNull(user.getExternalUrls());
        assertNotNull(user.getFollowers());
        assertEquals("https://api.spotify.com/v1/users/tuggareutangranser", user.getHref());
        assertEquals("tuggareutangranser", user.getId());
        assertEquals(1, user.getImages().length);
        assertEquals(ModelObjectType.USER, user.getType());
        assertEquals("spotify:user:tuggareutangranser", user.getUri());
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/users/abbasp%C3%B6tify", defaultRequest.getUri().toString());
    }
}
