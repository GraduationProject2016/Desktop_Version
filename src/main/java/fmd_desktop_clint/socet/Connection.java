package fmd_desktop_clint.socet;

import fmd_desktop_clint.socet.dto.MessageDto;
import fmd_desktop_clint.util.CommonUtil;
import fmd_desktop_clint.util.JsonHandler;

public class Connection {

	public int userID;
	public int deviceID;
	public SocketClient client;
	public Thread clientThread;

	public Connection() {
		try {
			userID = CommonUtil.getUserID();
			deviceID = CommonUtil.getDeviceID();

			client = new SocketClient();
			clientThread = new Thread(client);
			clientThread.start();
			signIn();
		} catch (Exception ex) {
		}
	}

	public boolean isConnected() {
		if (client != null && client.is_connected) {
			return true;
		}
		return false;
	}

	public Boolean signIn() {
		try {
			MessageDto msg = new MessageDto(MessageDto.CLIENT_TO_SERVER);
			msg.setDeviceId(deviceID);
			msg.setUserId(userID);
			msg.setContent("sign_in");
			client.send(JsonHandler.getMessageDtoJson(msg));
			System.out.println(JsonHandler.getMessageDtoJson(msg));
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	public void signOut() {
		try {
			MessageDto msg = new MessageDto(MessageDto.CLIENT_TO_SERVER);
			msg.setDeviceId(deviceID);
			msg.setUserId(userID);
			msg.setContent("sign_out");
			client.send(JsonHandler.getMessageDtoJson(msg));
			System.out.println(JsonHandler.getMessageDtoJson(msg));
		} catch (Exception ex) {
		}
	}
}
