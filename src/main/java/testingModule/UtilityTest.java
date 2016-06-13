package testingModule;

import java.io.IOException;

import org.json.JSONException;

import fmd_desktop_clint.util.SuperUtil;

public class UtilityTest {

	/*****
	 * test is email method (success)
	 * 
	 * @throws IOException
	 * @throws JSONException
	 ****/
	public static void testIsEmailMethodSuccess() {
		String email = "ibrahim.ali@gamil.com";
		System.out.println(SuperUtil.isEmail(email));
	}

	/*****
	 * test is email method (failure)
	 * 
	 * @throws IOException
	 * @throws JSONException
	 ****/
	public static void testIsEmailMethodFailure() {
		String email = "ibrahim.ali";
		System.out.println(SuperUtil.isEmail(email));
	}

	/*****
	 * test getting mac address
	 * 
	 * @throws IOException
	 * @throws JSONException
	 ****/
	public static void testGetMacAddress() {
		String mac_add = "E4-D5-3D-5B-8A-C4";
		System.out.println(SuperUtil.getMacAddress().equals(mac_add));
	}

	public static void main(String[] args) {
		testIsEmailMethodFailure();
	}
}
