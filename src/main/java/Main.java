import java.io.IOException;

import fmd_desktop_clint.socet.SocketClient;
import fmd_desktop_clint.socet.dto.MessageDto;
import fmd_desktop_clint.util.JsonHandler;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println((2 << 25));
		try {
			SocketClient client = new SocketClient();
			Thread clientThread = new Thread(client);
			clientThread.start();

			MessageDto msg = new MessageDto(MessageDto.CLIENT_TO_SERVER);
			msg.setDeviceId(1);
			msg.setUserId(0);
			msg.setContent("mohame265");
			client.send(JsonHandler.getMessageDtoJson(msg));
			//client.send(JsonHandler.getMessageDtoJson(msg));
			//System.out.println(JsonHandler.getMessageDtoJson(msg));
		} catch (Exception ex) {
		}

		// String[] arr = null;// new String[] {"ahhhh" ,"ohhh"};
		// Command command = new Command("mo" , arr);
		// String str = JsonHandler.getCommandJson(command);
		// System.out.println(str);
		// System.out.println(JsonHandler.getCommandObject(str));

	}
}
