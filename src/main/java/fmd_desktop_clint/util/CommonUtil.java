package fmd_desktop_clint.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CommonUtil {

	public static boolean isAddedDevice() throws IOException {
		File addDeviceFile = new File(Constants.FILE_PATH);
		BufferedReader brTest = new BufferedReader(new FileReader(addDeviceFile));
		String[] arr = brTest.readLine().split(" , ");
		return arr[0].equals("1");
	}

	public static int getUserID() throws IOException {
		File addDeviceFile = new File(Constants.FILE_PATH);
		BufferedReader brTest = new BufferedReader(new FileReader(addDeviceFile));
		String[] arr = brTest.readLine().split(" , ");
		return Integer.valueOf(arr[1]);
	}

	public static int getDeviceID() throws IOException {
		File addDeviceFile = new File(Constants.FILE_PATH);
		BufferedReader brTest = new BufferedReader(new FileReader(addDeviceFile));
		String[] arr = brTest.readLine().split(" , ");
		return Integer.valueOf(arr[2]);
	}

	public static void DeleteDevice() throws IOException {
		File addDeviceFile = new File(Constants.FILE_PATH);
		BufferedReader brTest = new BufferedReader(new FileReader(addDeviceFile));
		String[] arr = brTest.readLine().split(" , ");

		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Constants.FILE_PATH, false)))) {
			out.println(0 + " , " + arr[1] + " , " + 0);
		} catch (IOException e) {
		}
	}

	public static String getHostName() throws IOException {
		File hostNameFile = new File(Constants.HOSTNAME_File);
		String arr = null;
		if (hostNameFile.exists()) {
			BufferedReader brTest = new BufferedReader(new FileReader(hostNameFile));
			arr = brTest.readLine();
		}
		return arr;
	}
}
