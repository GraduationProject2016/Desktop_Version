package fmd_desktop_clint.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ComputerFilesSystem {
	public static String path;
	public static List<FMDFile> files;
	public static List<FMDDirectory> directories;
	public int numOfFiles;
	public int numOfFolders;

	public ComputerFilesSystem(String path_) throws IOException {
		path = path_;
		files = new ArrayList<FMDFile>();
		directories = new ArrayList<FMDDirectory>();

		ls();
	}

	public static void ls() throws IOException {
		String fileName, fileType;
		double fileSize;

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (!listOfFiles[i].isHidden()) {
				if (listOfFiles[i].isFile()) {
					fileName = listOfFiles[i].getName();
					fileSize = listOfFiles[i].length();
					fileType = Files.probeContentType(listOfFiles[i].toPath());
					files.add(new FMDFile(fileName, fileType, fileSize));
				} else if (listOfFiles[i].isDirectory()) {
					fileName = listOfFiles[i].getName();
					fileSize = listOfFiles[i].length();
					directories.add(new FMDDirectory(fileName, fileSize));
				}
			}
		}
	}

	public JSONObject toJson() throws JSONException {
		JSONObject object = new JSONObject();
		object.put("path", path);
		object.put("numoffiles", files.size());
		object.put("numoffolders", directories.size());

		JSONArray filesArray = new JSONArray();
		for (int i = 0; i < files.size(); i++) {
			filesArray.put(i, files.get(i).toJson());
		}
		object.put("files", filesArray);

		JSONArray foldersArray = new JSONArray();
		for (int i = 0; i < directories.size(); i++) {
			foldersArray.put(i, directories.get(i).toJson());
		}
		object.put("folders", foldersArray);
		return object;
	}

	public void fromJson(JSONObject object) throws JSONException {

		path = (String) object.get("path");
		numOfFiles = (int) object.get("numoffiles");
		numOfFolders = (int) object.get("numoffolders");

		JSONArray fileArray = (JSONArray) object.get("files");
		for (int i = 0; i < numOfFiles; i++) {
			JSONObject obj = (JSONObject) fileArray.get(i);
			files.add(new FMDFile((String) obj.getString("name"), (String) obj.getString("type"),
					(double) obj.get("size")));
		}

		JSONArray folderArray = (JSONArray) object.get("folders");
		for (int i = 0; i < numOfFolders; i++) {
			JSONObject obj = (JSONObject) folderArray.get(i);
			directories.add(new FMDDirectory((String) obj.getString("name"), (double) obj.get("size")));
		}

	}
}
