package testingModule;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONException;

import fmd_desktop_clint.operation.LocationOperation;

public class LocationTest {

	/*****
	 * test retrieve location
	 * 
	 * @throws URISyntaxException
	 * 
	 * @throws IOException
	 * @throws JSONException
	 ****/
	public static void testFindDeviceLocation() throws JSONException, IOException, URISyntaxException {
		LocationOperation.findDeviceLocation();
	}

	public static void main(String[] args) {

		try {
			LocationOperation.findDeviceLocation();
		} catch (JSONException | IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
