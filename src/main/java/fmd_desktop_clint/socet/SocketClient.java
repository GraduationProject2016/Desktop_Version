package fmd_desktop_clint.socet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import fmd_desktop_clint.socet.dto.MessageDto;

public class SocketClient implements Runnable {

	public int port;
	public String serverAddr;
	public Socket socket;

	public ObjectInputStream In;
	public ObjectOutputStream Out;

	public String username;
	public String password;

	public SocketClient() throws IOException {

		this.serverAddr = "localhost";
		this.port = 13000;
		socket = new Socket(InetAddress.getByName(serverAddr), port);

		Out = new ObjectOutputStream(socket.getOutputStream());
		Out.flush();
		In = new ObjectInputStream(socket.getInputStream());

	}

	@Override
	public void run() {
		boolean keepRunning = true;
		Scanner in = new Scanner(System.in);

		while (keepRunning) {
			try {
				MessageDto msg = (MessageDto) In.readObject();
				System.out.println("Incoming : " + msg.toString());

			} catch (Exception ex) {
				keepRunning = false;

				System.out.println("Exception SocketClient run()");
				ex.printStackTrace();
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

	public void closeThread(Thread t) {
		t = null;
	}
}
