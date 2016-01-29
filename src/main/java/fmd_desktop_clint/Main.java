package fmd_desktop_clint;

import java.io.IOException;

import fmd_desktop_clint.util.CommonUtil;
import fmd_desktop_clint.views.AddDevice;
import fmd_desktop_clint.views.login;

public class Main {
	public static void main(String[] args) throws IOException {
		// EventQueue.invokeLater(new Runnable() {
		// @Override
		// public void run() {
		// try {
		// TrayIcon icon = new TrayIcon(ImageIO.read(new
		// File("control_icon.png")));
		// SystemTray tray = SystemTray.getSystemTray();
		// tray.add(icon);
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
		// JFrame dialog = new JFrame();
		// dialog.setSize(100, 100);
		// dialog.setVisible(true);
		// }
		// });
		// System.out.println(System.getProperty("os.name").toLowerCase().contains("windows")
		// );
		new login();
		// System.out.println(
		// WebServiceConnector.getResponeString("http://codeforces.com/api/user.rating?handle=mazen_ibrahim"));

		// try {
		// SocketClient client = new SocketClient();
		// Thread clientThread = new Thread(client);
		// clientThread.start();
		//
		// MessageDto msg = new MessageDto(MessageDto.CLIENT_TO_SERVER);
		// msg.setDeviceId(1);
		// msg.setUserId(2);
		// msg.setContent("mohame265");
		// client.send(JsonHandler.getMessageDtoJson(msg));
		// System.out.println(JsonHandler.getMessageDtoJson(msg));
		// } catch (Exception ex) {
		// }
	}
}
