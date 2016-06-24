package fmd_desktop_clint.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("resource")
public class SuperUtil {

	public static void logout() throws IOException {
		File addDeviceFile = new File(Constants.FILE_PATH);
		BufferedReader brTest = new BufferedReader(new FileReader(addDeviceFile));
		String[] arr = brTest.readLine().split(" , ");

		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Constants.FILE_PATH, false)))) {
			out.println(arr[0] + " , " + 0 + " , " + arr[2]);
		} catch (IOException e) {
		}
	}

	public static void saveDeviceID(int deviceID) throws IOException {
		File addDeviceFile = new File(Constants.FILE_PATH);
		BufferedReader brTest = new BufferedReader(new FileReader(addDeviceFile));
		String[] arr = brTest.readLine().split(" , ");

		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Constants.FILE_PATH, false)))) {
			out.println(arr[0] + " , " + arr[1] + " , " + deviceID);
		} catch (IOException e) {
		}
	}

	public static void saveUserID(int userID) throws IOException {
		File addDeviceFile = new File(Constants.FILE_PATH);
		BufferedReader brTest = new BufferedReader(new FileReader(addDeviceFile));
		String[] arr = brTest.readLine().split(" , ");

		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Constants.FILE_PATH, false)))) {
			out.println(arr[0] + " , " + userID + " , " + arr[2]);
		} catch (IOException e) {
		}
	}

	public static void markDeviceAsAdded() throws IOException {
		File addDeviceFile = new File(Constants.FILE_PATH);
		BufferedReader brTest = new BufferedReader(new FileReader(addDeviceFile));
		String[] arr = brTest.readLine().split(" , ");

		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Constants.FILE_PATH, false)))) {
			out.println(1 + " , " + arr[1] + " , " + arr[2]);
		} catch (IOException e) {
		}
	}

	public static void errorMsg(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
	}

	public static String getMacAddress() {
		InetAddress ip = null;
		StringBuilder sb = new StringBuilder();
		try {
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();
			for (int i = 0; i < mac.length; i++)
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));

		} catch (UnknownHostException | SocketException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static boolean isEmail(String input) {
		int atIndex = input.indexOf('@');

		if (atIndex == -1)
			return false;

		int lastDotIndex = input.lastIndexOf('.');

		return atIndex < lastDotIndex && lastDotIndex - atIndex != 1 && lastDotIndex != input.length() - 1
				&& atIndex != 0;
	}

	public void copyConfigsFiles() {
		String jarPath = getautostart();
		if (!new File(jarPath + "\\Find My Device.exe").exists()) {
			try {
				copyFile(getrunningdir() + "\\Find My Device.exe", jarPath + "\\Find My Device.exe");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		URL url = getClass().getResource("/resources/webrecording.jar");
		File webrecordingJar = new File(url.getPath());

		if (!new File(Constants.APPDATA + "\\webrecording.jar").exists()) {
			try {
				copyFile(webrecordingJar.getAbsolutePath(), Constants.APPDATA + "\\webrecording.jar");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void copyFile(String source, String dest) throws IOException {
		if (!new File(dest).exists()) {
			InputStream input = null;
			OutputStream output = null;
			try {
				input = new FileInputStream(source);
				output = new FileOutputStream(dest);
				byte[] buf = new byte[1024];
				int bytesRead;
				while ((bytesRead = input.read(buf)) > 0) {
					output.write(buf, 0, bytesRead);
				}
			} finally {
				input.close();
				output.close();
			}
		}
	}

	public static String getautostart() {
		return System.getProperty("java.io.tmpdir").replace("Local\\Temp\\",
				"Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup");
	}

	public static String getrunningdir() throws IOException {
		String runningdir = new File(".").getCanonicalPath().toString();
		return runningdir;
	}
}
