package fmd_desktop_clint.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CommonUtil {

	public static boolean isAddedDevice() throws IOException {
		File addDeviceFile = new File("configfile.txt");
		BufferedReader brTest = new BufferedReader(new FileReader(addDeviceFile));
		String[] arr = brTest.readLine().split(" , ");
		return arr[0].equals("1");
	}

	public static int getUserID() throws IOException {
		File addDeviceFile = new File("configfile.txt");
		BufferedReader brTest = new BufferedReader(new FileReader(addDeviceFile));
		String[] arr = brTest.readLine().split(" , ");
		return Integer.valueOf(arr[1]);
	}

	public static int getDeviceID() throws IOException {
		File addDeviceFile = new File("configfile.txt");
		BufferedReader brTest = new BufferedReader(new FileReader(addDeviceFile));
		String[] arr = brTest.readLine().split(" , ");
		return Integer.valueOf(arr[2]);
	}
}
