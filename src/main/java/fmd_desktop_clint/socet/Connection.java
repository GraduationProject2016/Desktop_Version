package fmd_desktop_clint.socet;

import fmd_desktop_clint.socet.dto.MessageDto;
import fmd_desktop_clint.util.JsonHandler;

public class Connection {

	public SocketClient client;
	public Thread clientThread;

	public Connection() {
		try {
			client = new SocketClient();
			clientThread = new Thread(client);
			clientThread.start();
		} catch (Exception ex) {
		}
	}

	public void signIn() {
		try {
			MessageDto msg = new MessageDto(MessageDto.CLIENT_TO_SERVER);
			msg.setDeviceId(1);
			msg.setUserId(2);
			msg.setContent("sign_in");
			client.send(JsonHandler.getMessageDtoJson(msg));
			System.out.println(JsonHandler.getMessageDtoJson(msg));
		} catch (Exception ex) {
		}
	}

	public void signOut() {
		try {
			MessageDto msg = new MessageDto(MessageDto.CLIENT_TO_SERVER);
			msg.setDeviceId(1);
			msg.setUserId(2);
			msg.setContent("sign_out");
			client.send(JsonHandler.getMessageDtoJson(msg));
			System.out.println(JsonHandler.getMessageDtoJson(msg));
		} catch (Exception ex) {
		}
	}
}
