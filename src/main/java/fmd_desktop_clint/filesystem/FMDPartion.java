package fmd_desktop_clint.filesystem;

import org.json.JSONException;
import org.json.JSONObject;

public class FMDPartion {

	public String name;
	public String path;
	public double totalSpace;
	public double usableSpace;

	public FMDPartion(String name, String path, double totalSpace, double usableSpace) {
		super();
		this.name = name;
		this.path = path;
		this.totalSpace = totalSpace;
		this.usableSpace = usableSpace;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTotalSpace() {
		return totalSpace;
	}

	public void setTotalSpace(double totalSpace) {
		this.totalSpace = totalSpace;
	}

	public double getUsableSpace() {
		return usableSpace;
	}

	public void setUsableSpace(double usableSpace) {
		this.usableSpace = usableSpace;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public JSONObject toJson() throws JSONException {
		JSONObject ob = new JSONObject();
		ob.put("name", name);
		ob.put("path", path);
		ob.put("totalSpace", totalSpace);
		ob.put("usableSpace", usableSpace);
		return ob;
	}

	@Override
	public String toString() {
		return "FMDPartion [name=" + name + ", totalSpace=" + totalSpace + ", usableSpace=" + usableSpace + "]";
	}

}
