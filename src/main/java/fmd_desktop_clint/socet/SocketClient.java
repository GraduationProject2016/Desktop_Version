package fmd_desktop_clint.socet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import fmd_desktop_clint.operation.Operation;
import fmd_desktop_clint.socet.dto.MessageDto;
import fmd_desktop_clint.util.CommonUtil;
import fmd_desktop_clint.util.JsonHandler;

public class SocketClient implements Runnable {

	public static int port;
	public static String serverIp;
	public Socket socket;

	public ObjectInputStream In;
	public ObjectOutputStream Out;

	public boolean is_connected;

	public SocketClient() throws IOException {
		init(CommonUtil.getHostName());
	}

	public SocketClient(String ip) throws IOException {
		init(ip);
	}

	private void init(String ip) throws UnknownHostException, IOException {
		serverIp = ip;
		port = 13000;
		is_connected = false;
		socket = new Socket(InetAddress.getByName(serverIp), port);
		Out = new ObjectOutputStream(socket.getOutputStream());
		Out.flush();
		In = new ObjectInputStream(socket.getInputStream());
	}

	@Override
	public void run() {
		while (true) {
			is_connected = true;
			try {
				MessageDto msg = JsonHandler.getMessageDtoObject((String) In.readObject());
				System.out.println("Incoming : " + msg.toString());
				send(JsonHandler.getMessageDtoJson(Operation.mapper(msg)));
			} catch (Exception ex) {
				is_connected = false;
				System.out.println("Exception SocketClient run()");
				ex.printStackTrace();
				break;
			}
		}
	}

	public void send(String msg) {
		try {
			Out.writeObject(msg);
			Out.flush();
			System.out.println("Outgoing : " + msg.toString());
		} catch (IOException ex) {
			System.out.println("Exception SocketClient send()");
		}
	}
}
