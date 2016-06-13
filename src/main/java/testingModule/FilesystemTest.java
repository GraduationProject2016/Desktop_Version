package testingModule;

import java.io.IOException;

import org.json.JSONException;

import fmd_desktop_clint.operation.FileSystemOperation;

public class FilesystemTest {

	/*****
	 * get desktop content
	 * 
	 * @throws IOException
	 * @throws JSONException
	 ****/
	public static void testComputerDesktopJson() throws JSONException, IOException {
		System.out.println(FileSystemOperation.computerDesktopJson());
	}

	/**
	 * get current user content (Documents & Music & Videos & ....)
	 * 
	 * @throws IOException
	 * @throws JSONException
	 **/
	public static void testcomputerHomeJson() throws JSONException, IOException {
		System.out.println(FileSystemOperation.computerHomeJson());
	}

	/**
	 * get partions details ( My Computer )
	 * 
	 * @throws JSONException
	 **/
	public static void testcomputerPartionsJson() throws JSONException {
		System.out.println(FileSystemOperation.computerPartionsJson());
	}

	/**
	 * get content of given path
	 * 
	 * @throws IOException
	 * @throws JSONException
	 **/
	public static void testcomputerPathJson() throws JSONException, IOException {
		System.out.println(FileSystemOperation.computerPathJson("D:\\"));
	}

	/** rename file or Dir **/
	public static void testrenameDirectory() {
		System.out.println(FileSystemOperation.renameDirectory("path here", "newFIleName"));
	}

	/** delete file or Dir **/
	public static void testremoveDirectory() {
		System.out.println(FileSystemOperation.removeDirectory("path here"));
	}

	/** create new Dir **/
	public static void testcreateNewDirectory() {
		System.out.println(FileSystemOperation.createNewDirectory("path here", "newFIleName"));
	}

	/**
	 * create new file
	 * 
	 * @throws IOException
	 **/
	public static void testcreateNewFile() throws IOException {
		System.out.println(FileSystemOperation.createNewFile("path here", "newFIleName"));
	}

	public static void main(String[] args) {
		// to do test
	}

	/*
	 * 
	 * try { SocketClient client = new SocketClient("192.168.43.154");
	 * //SocketClient client = new SocketClient("localhost"); Thread
	 * clientThread = new Thread(client); clientThread.start();
	 * 
	 * MessageDto msg = new MessageDto(MessageDto.CLIENT_TO_SERVER);
	 * msg.setDeviceId(1); msg.setUserId(1); msg.setContent("sign_in");
	 * client.send(JsonHandler.getMessageDtoJson(msg)); //
	 * client.send(JsonHandler.getMessageDtoJson(msg)); //
	 * System.out.println(JsonHandler.getMessageDtoJson(msg)); } catch
	 * (Exception ex) { ex.printStackTrace(); }
	 * 
	 */

}
