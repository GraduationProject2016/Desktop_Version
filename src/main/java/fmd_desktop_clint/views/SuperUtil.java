package fmd_desktop_clint.views;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fmd_desktop_clint.util.Constants;

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

	public static void errorMsg(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
	}

	public static boolean isEmail(String input) {
		int atIndex = input.indexOf('@');

		if (atIndex == -1)
			return false;

		int lastDotIndex = input.lastIndexOf('.');

		return atIndex < lastDotIndex && lastDotIndex - atIndex != 1 && lastDotIndex != input.length() - 1
				&& atIndex != 0;
	}
 
}
