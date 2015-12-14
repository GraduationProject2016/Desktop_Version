package fmd_desktop_clint;

import fmd_desktop_clint.socet.SocketClient;
import fmd_desktop_clint.socet.dto.MessageDto;
import fmd_desktop_clint.util.JsonHandler;

public class Main {
	public static void main(String[] args) { 

		try {
			SocketClient client = new SocketClient();
			Thread clientThread = new Thread(client);
			clientThread.start();

			MessageDto msg = new MessageDto(MessageDto.CLIENT_TO_SERVER);
			msg.setDeviceId(1);
			msg.setUserId(2);
			msg.setContent("mohame265");
			client.send(JsonHandler.getMessageDtoJson(msg));
			System.out.println(JsonHandler.getMessageDtoJson(msg));
		} catch (Exception ex) {
		}

	}
}
