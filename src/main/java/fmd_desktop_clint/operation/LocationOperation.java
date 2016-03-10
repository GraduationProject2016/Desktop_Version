package fmd_desktop_clint.operation;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONException;

import fmd_desktop_clint.util.CommonUtil;

public class LocationOperation {
	
	public static void findDeviceLocation() throws JSONException, IOException, URISyntaxException {
		Desktop.getDesktop().browse(new URI("http://" + CommonUtil.getHostName()
				+ ":8080/fmd/webserviceconnector.html?qu=" + CommonUtil.getDeviceID()));
	}

}
