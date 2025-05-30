package se.michaelthelin.spotify.requests.authorization.client_credentials;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.AbstractAuthorizationTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.michaelthelin.spotify.Assertions.assertHasBodyParameter;
import static se.michaelthelin.spotify.Assertions.assertHasHeader;

public class ClientCredentialsRequestTest_Purified extends AbstractAuthorizationTest<ClientCredentials> {

    private final ClientCredentialsRequest defaultRequest = SPOTIFY_API.clientCredentials().setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/authorization/client_credentials/ClientCredentials.json")).grant_type("client_credentials").build();

    public ClientCredentialsRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final ClientCredentials clientCredentials) {
        assertEquals("taHZ2SdB-bPA3FsK3D7ZN5npZS47cMy-IEySVEGttOhXmqaVAIo0ESvTCLjLBifhHOHOIuhFUKPW1WMDP7w6dj3MAZdWT8CLI2MkZaXbYLTeoDvXesf2eeiLYPBGdx8tIwQJKgV8XdnzH_DONk", clientCredentials.getAccessToken());
        assertEquals("Bearer", clientCredentials.getTokenType());
        assertEquals(3600, (int) clientCredentials.getExpiresIn());
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertHasHeader(defaultRequest, "Content-Type", "application/x-www-form-urlencoded");
    }

    @Test
    public void shouldComplyWithReference_3() {
        assertHasBodyParameter(defaultRequest, "grant_type", "client_credentials");
    }

    @Test
    public void shouldComplyWithReference_4() {
        assertEquals("https://accounts.spotify.com:443/api/token", defaultRequest.getUri().toString());
    }
}
