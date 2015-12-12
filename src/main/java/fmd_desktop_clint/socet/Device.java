/**
 * @author mohamed265
 * Created On : Nov 27, 2015 10:07:56 PM
 */
package fmd_desktop_clint.socet;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mohamed265
 */

public class Device implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private User user;

	private String name;

	private String password;

	private String macAddress;

	private Date lastActiveDate;

	private Boolean online;

	private Boolean type;

	private Boolean active;

	private String status;

	public Device() {

	}

	public Device(User user, String macAddress) {
		super();
		this.user = user;
		this.macAddress = macAddress;
	}

	public Integer getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public Date getLastActiveDate() {
		return lastActiveDate;
	}

	public Boolean getOnline() {
		return online;
	}

	public Boolean getType() {
		return type;
	}

	public Boolean getActive() {
		return active;
	}

	public String getStatus() {
		return status;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public void setLastActiveDate(Date lastActiveDate) {
		this.lastActiveDate = lastActiveDate;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	public void setType(Boolean type) {
		this.type = type;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", name=" + name + ", password=" + password + ", macAddress=" + macAddress
				+ ", lastActiveDate=" + lastActiveDate + ", online=" + online + ", type=" + type + ", active=" + active
				+ ", status=" + status + "]";
	}

}
