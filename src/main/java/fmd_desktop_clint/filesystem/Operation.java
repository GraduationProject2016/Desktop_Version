package fmd_desktop_clint.filesystem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Operation {

	public Operation() {

	}

	public static boolean removeDirectory(String path) {
		try {
			File file = new File(path);

			if (file.delete())
				return true;
			else
				return false;

		} catch (Exception e) {
			return false;
		}
	}

	public static boolean renameDirectory(String path, String newName) {
		try {

			// File (or directory) with old name
			File file = new File(path);

			// File (or directory) with new name
			File file2;
			if (Utility.getOSType().contains("win")) {
				file2 = new File(file.getParent() + "\\" + newName);
			} else {
				file2 = new File(file.getParent() + "/" + newName);
			}

			// Rename file (or directory)
			boolean success = file.renameTo(file2);

			if (!success)
				return false;

			return true;

		} catch (Exception e) {
			return false;
		}
	}

	public static boolean createNewDirectory(String path, String name) {
		File directory;
		if (Utility.getOSType().contains("win")) {
			directory = new File(path + "\\" + name);
		} else {
			directory = new File(path + "/" + name);
		}

		if (directory.exists())
			return false;

		return directory.mkdir();

	}

	public static boolean createNewFile(String path, String name) throws IOException {
		File directory;
		if (Utility.getOSType().contains("win")) {
			directory = new File(path + "\\" + name);
		} else {
			directory = new File(path + "/" + name);
		}

		if (directory.exists())
			return false;

		return directory.createNewFile();

	}

	public static JSONObject computerPathJson(String path) throws JSONException, IOException {
		ComputerFilesSystem obj = new ComputerFilesSystem(path);
		return obj.toJson();
	}

	public static JSONObject computerHomeJson() throws JSONException, IOException {
		ComputerFilesSystem obj = new ComputerFilesSystem(
				FileSystemView.getFileSystemView().getDefaultDirectory().getParent());
		return obj.toJson();
	}

	public static JSONObject computerDesktopJson() throws JSONException, IOException {
		ComputerFilesSystem obj = new ComputerFilesSystem(Utility.getDesktopPath());
		return obj.toJson();
	}

	public static JSONObject computerPartionsJson() throws JSONException {
		JSONObject object = new JSONObject();
		List<FMDPartion> partions = Utility.getComputerPartions();

		JSONArray ps = new JSONArray();
		for (int i = 0; i < partions.size(); i++) {
			ps.put(i, partions.get(i).toJson());
		}
		object.put("partions", ps);
		object.put("numOfPartions", partions.size());
		return object;
	}

}
