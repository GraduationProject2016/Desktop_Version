/**
 * @author mohamed265
 * Created On : Nov 17, 2015 5:14:38 PM
 */
package fmd_desktop_clint.util;

/**
 * @author mohamed265
 * @author ibrahim
 */
public class Constants {

	// Operations Term
	public static final String STATES = "status";
	public static final String FAIL = "fail";
	public static final String SUCCESS = "Success";
	public static final String EmailNotUniqe = "EmailNotUniqe";
	public static final String UsernameNotUniqe = "UsernameNotUniqe";

	public static final String HOST_NAME = "http://localhost:8080";
	public static final String HOSTNAME_File = System.getenv("APPDATA") + "\\Find My Device\\hostname.txt";
	public static final String FILE_PATH = System.getenv("APPDATA") + "\\Find My Device\\configfile.txt";
	public static final String LOG_FILE = System.getenv("APPDATA") + "\\Find My Device\\log.txt";
	public static final String APPDATA = System.getenv("APPDATA") + "\\Find My Device";
	
	// Web Service Constants
	public static final String ID = "id";
	public static final String USER_NAME = "username";
	public static final String NAME = "name";
	public static final String PASSWORD = "password";
	public static final String EMAIL = "email";
	public static final String ACTIVE = "active";
	public static final String MOBILE_NO = "mobileno";
	public static final String MAC_ADDRESS = "macaddress";
	public static final String DEVICE_TYPE = "device_type";
	public static final String DEVICE_TYPE_ANDROID = "ANDROID";
	public static final Boolean DEVICE_TYPE_ANDROID_DB = false;
	public static final String DEVICE_TYPE_DESKTOP = "DESKTOP";
	public static final Boolean DEVICE_TYPE_DESKTOP_DB = true;

	// language Term
	public static final String LANGUAGE = "language";
	public static final String ENGLISH_LANGUAGE = "en";
	public static final String ARABIC_LANGUAGE = "ar";

	// communication type
	public static final Boolean SERVER_TO_CLIENT = true;
	public static final Boolean CLIENT_TO_SERVER = false;

	// message dto
	public static final String MESSAGE_CONTENT = "content";
	public static final String MESSAGE_DEVICE = "sender";
	public static final String MESSAGE_USER = "reciver";
	public static final String MESSAGE_TYPE = "type";

	// command
	public static final String COMAND_COMMAND = "command";
	public static final String COMAND_PARMS = "parms";
	public static final int FIlE_TRANSFARE = -1;

}
