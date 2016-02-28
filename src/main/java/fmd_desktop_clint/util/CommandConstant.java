package fmd_desktop_clint.util;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.filechooser.FileSystemView;

import org.json.JSONException;
import org.json.JSONObject;

import fmd_desktop_clint.filesystem.ComputerFilesSystem;
import fmd_desktop_clint.filesystem.Utility;

public class CommandConstant { 

	public static final String removeDirectory = "removeDirectory";
	
	public static final String removeFile = "removeFile";

	public static final String renameDirectory = "renameDirectory";

	public static final String createNewDirectory = "createNewDirectory";

	public static final String createNewFile = "createNewFile";

	public static final String getPCDeviceInfo = "getPCDeviceInfo";

	public static final String computerPathJson = "computerPath";

	public static final String computerHomeJson = "computerHome";

	public static final String computerDesktop = "computerDesktop";

	public static final String computerPartions = "computerPartions";
	
	public static final String filetransfer = "filetransfer";
	
	public static final String deviceLocation = "deviceLocation";

}
