import java.io.IOException;

import fmd_desktop_clint.socet.SocketClient;
import fmd_desktop_clint.socet.dto.MessageDto;
import fmd_desktop_clint.util.JsonHandler;

public class Main {

	public static void main(String[] args) throws IOException {
		// int s = (Integer.valueOf("0110111", 2));
		// System.out.println((char) s);
		// String str = "kan yom 7obk agml sodfa";
		// for (int i = 0; i < str.length(); i++) {
		// if (str.charAt(i) != ' ')
		// System.out.print(Integer.toBinaryString(str.charAt(i)) + " ");
		// else
		// System.out.println();
		// }
		// System.out.println((2 << 25));
		try {
			SocketClient client = new SocketClient();
			Thread clientThread = new Thread(client);
			clientThread.start();

			MessageDto msg = new MessageDto(MessageDto.CLIENT_TO_SERVER);
			msg.setDeviceId(1);
			msg.setUserId(0);
			msg.setContent("sign_in");
			client.send(JsonHandler.getMessageDtoJson(msg));
			// client.send(JsonHandler.getMessageDtoJson(msg));
			// System.out.println(JsonHandler.getMessageDtoJson(msg));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// String[] arr = null;// new String[] {"ahhhh" ,"ohhh"};
		// Command command = new Command("mo" , arr);
		// String str = JsonHandler.getCommandJson(command);
		// System.out.println(str);
		// System.out.println(JsonHandler.getCommandObject(str));

	}
}
