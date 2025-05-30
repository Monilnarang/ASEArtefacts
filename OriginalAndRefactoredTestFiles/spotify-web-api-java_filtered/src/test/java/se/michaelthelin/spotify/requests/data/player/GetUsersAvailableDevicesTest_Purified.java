package se.michaelthelin.spotify.requests.data.player;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.ITest;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.miscellaneous.Device;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.*;

public class GetUsersAvailableDevicesTest_Purified extends AbstractDataTest<Device[]> {

    private final GetUsersAvailableDevicesRequest defaultRequest = ITest.SPOTIFY_API.getUsersAvailableDevices().setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/player/GetUsersAvailableDevices.json")).build();

    private final GetUsersAvailableDevicesRequest emptyRequest = ITest.SPOTIFY_API.getUsersAvailableDevices().setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/player/GetUsersAvailableDevices_None.json")).build();

    public GetUsersAvailableDevicesTest() throws Exception {
    }

    public void shouldReturnDefault(final Device[] devices) {
        assertEquals(2, devices.length);
        Device computerDevice = devices[0];
        Device smartPhoneDevice = devices[1];
        assertEquals(computerDevice.getId(), "5fbb3ba6aa454b5534c4ba43a8c7e8e45a63ad0e");
        assertFalse(computerDevice.getIs_active());
        assertFalse(computerDevice.getIs_private_session());
        assertFalse(computerDevice.getIs_restricted());
        assertEquals(computerDevice.getName(), "My fridge");
        assertEquals(computerDevice.getType(), "Computer");
        assertEquals(computerDevice.getVolume_percent(), 100);
        assertTrue(computerDevice.getSupports_volume());
        assertEquals(smartPhoneDevice.getId(), "dc96ab03e8ecad17a70945b000acfef7591cd34e");
        assertFalse(smartPhoneDevice.getIs_active());
        assertFalse(smartPhoneDevice.getIs_private_session());
        assertFalse(smartPhoneDevice.getIs_restricted());
        assertEquals(smartPhoneDevice.getName(), "My Smartphone");
        assertEquals(smartPhoneDevice.getType(), "Smartphone");
        assertEquals(smartPhoneDevice.getVolume_percent(), 100);
        assertFalse(smartPhoneDevice.getSupports_volume());
    }

    public void shouldReturnEmpty(final Device[] devices) {
        assertEquals(0, devices.length);
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/me/player/devices", defaultRequest.getUri().toString());
    }
}
