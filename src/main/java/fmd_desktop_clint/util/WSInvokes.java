package fmd_desktop_clint.util;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class WSInvokes {
	public static String isAuzorizedUser(String username, String password) throws JSONException, IOException {
		String login_by = "";
		login_by = SuperUtil.isEmail(username) ? "email" : "username";

		String url = "http://" + CommonUtil.getHostName() + ":8080/fmd/webService/user/login/" + login_by + "/"
				+ username + "/" + password;
		String response = WebServiceConnector.getResponeString(url);
		if (response == null)
			return "null";

		JSONObject obj = new JSONObject(response);
		if (obj.getString("status").equals("Success") && obj.getBoolean("active") == true) {
			SuperUtil.saveUserID(obj.getInt("id"));
			return "true";
		} else if (obj.getString("status").equals("Success") && obj.getBoolean("active") == false) {
			return "error_not_active";
		}
		return "false";
	}

	public static String registerUserDevice(String deviceName, String devicePassword)
			throws JSONException, IOException {

		String os = System.getProperty("os.name").toLowerCase().contains("windows") ? "WINDOWS" : "LINUX";
		int userID = CommonUtil.getUserID();
		String deviceID = SuperUtil.getMacAddress();

		deviceName = deviceName.replace(" ", "%20");
		String url = "http://" + CommonUtil.getHostName() + ":8080/fmd/webService/device/register/" + deviceName + "/"
				+ devicePassword + "/" + deviceID + "/" + os + "/" + userID;

		String response = WebServiceConnector.getResponeString(url);
		if (response == null)
			return "null";

		JSONObject obj = new JSONObject(response);

		if (obj.getString("status").equals("Success")) {
			SuperUtil.saveDeviceID(obj.getInt("id"));
			return "true";
		} else if (obj.getString("status").contains("MacAddressNotNniqe")) {
			return "error_MacAddressNotNniqe";
		}
		return "false";
	}

	public static boolean isDeletedDevice(String mac_address) throws JSONException, IOException {
		String url = "http://" + CommonUtil.getHostName() + ":8080/fmd/webService/device/devicefounded/" + mac_address;

		String response = WebServiceConnector.getResponeString(url);

		if (response == null)
			return false;

		JSONObject obj = new JSONObject(response);
		if (obj.getString("status").equals("founded"))
			return false;
		else if (obj.getString("status").equals("not founded"))
			return true;

		return false;
	}
	
	
}
