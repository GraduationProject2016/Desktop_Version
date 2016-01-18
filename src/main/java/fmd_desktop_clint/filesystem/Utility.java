package fmd_desktop_clint.filesystem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

public class Utility {

	public static List<FMDPartion> getComputerPartions() {
		List<FMDPartion> partions = new ArrayList<>();
		FileSystemView fsv = FileSystemView.getFileSystemView();

		File[] f = File.listRoots();
		for (int i = 0; i < f.length; i++) {
			if (f[i].canRead() && f[i].canWrite())
				partions.add(new FMDPartion(fsv.getSystemDisplayName(f[i]), (String.valueOf(f[i])),
						f[i].getTotalSpace(), f[i].getUsableSpace()));
		}
		return partions;
	}

	public static String getDesktopPath() {
		File home = FileSystemView.getFileSystemView().getHomeDirectory();
		return home.getAbsolutePath();
	}

	public static String getOSType() {
		return System.getProperty("os.name").toLowerCase();
	}

	// file size
	// double bytes = file.length();
	// double kilobytes = (bytes / 1024);
	// double megabytes = (kilobytes / 1024);
	// double gigabytes = (megabytes / 1024);
	// double terabytes = (gigabytes / 1024);
	// double petabytes = (terabytes / 1024);
	// double exabytes = (petabytes / 1024);
	// double zettabytes = (exabytes / 1024);
	// double yottabytes = (zettabytes / 1024);
}
