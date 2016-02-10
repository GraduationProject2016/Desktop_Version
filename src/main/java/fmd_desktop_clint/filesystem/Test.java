package fmd_desktop_clint.filesystem;

import java.io.IOException;

import org.json.JSONException;

public class Test {

	public static void main(String[] arr) throws JSONException, IOException {
		// (copy to here ) any print statement from below to test it

		System.out.println(Operation.computerPartionsJson());
	}

	public void content() throws JSONException, IOException {
		/***** get desktop content ****/
		System.out.println(Operation.computerDesktopJson());

		/** get current user content (Documents & Music & Videos & ....) **/
		System.out.println(Operation.computerHomeJson());

		/** get partions details ( My Computer ) **/
		System.out.println(Operation.computerPartionsJson());

		/** get content of given path **/
		System.out.println(Operation.computerPathJson("D:\\"));

		/** rename file or Dir **/
		System.out.println(Operation.renameDirectory("path here", "newFIleName"));

		/** delete file or Dir **/
		System.out.println(Operation.removeDirectory("path here"));

		/** create new Dir **/
		System.out.println(Operation.createNewDirectory("path here", "newFIleName"));

		/** create new file **/
		System.out.println(Operation.createNewFile("path here", "newFIleName"));
	}

}
