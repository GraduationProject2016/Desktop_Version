

import java.io.IOException;

import org.json.JSONException;

import fmd_desktop_clint.operation.FileSystemOperation;

public class Test {

	public static void main(String[] arr) throws JSONException, IOException {
		// (copy to here ) any print statement from below to test it
		System.out.println(FileSystemOperation.removeDirectory("C:\\Users\\IbrahimAli\\Desktop\\dds.iss"));
	}

	public void content() throws JSONException, IOException {
		/***** get desktop content ****/
		System.out.println(FileSystemOperation.computerDesktopJson());

		/** get current user content (Documents & Music & Videos & ....) **/
		System.out.println(FileSystemOperation.computerHomeJson());

		/** get partions details ( My Computer ) **/
		System.out.println(FileSystemOperation.computerPartionsJson());

		/** get content of given path **/
		System.out.println(FileSystemOperation.computerPathJson("D:\\"));

		/** rename file or Dir **/
		System.out.println(FileSystemOperation.renameDirectory("path here", "newFIleName"));

		/** delete file or Dir **/
		System.out.println(FileSystemOperation.removeDirectory("path here"));

		/** create new Dir **/
		System.out.println(FileSystemOperation.createNewDirectory("path here", "newFIleName"));

		/** create new file **/
		System.out.println(FileSystemOperation.createNewFile("path here", "newFIleName"));
	}

}
