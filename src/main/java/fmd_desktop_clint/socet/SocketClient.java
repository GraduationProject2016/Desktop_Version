package fmd_desktop_clint.socet;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import fmd_desktop_clint.filesystem.Operation;
import fmd_desktop_clint.socet.dto.Command;
import fmd_desktop_clint.socet.dto.MessageDto;
import fmd_desktop_clint.util.CommandConstant;
import fmd_desktop_clint.util.CommonUtil;
import fmd_desktop_clint.util.Constants;
import fmd_desktop_clint.util.JsonHandler;

public class SocketClient implements Runnable {

	public int port;
	public String serverAddr;
	public Socket socket;

	public ObjectInputStream In;
	public ObjectOutputStream Out;

	public String username;
	public String password;

	public SocketClient() throws IOException {
		//http://localhost:8080/
		// TODO add host name
		// this.serverAddr = "localhost"; // getHostName()
		String[] arr = CommonUtil.getHostName().split(":");
		this.serverAddr = arr[1].substring(2);
		//System.out.println(serverAddr);
		//System.out.println(serverAddr.substring(2));
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
				MessageDto msg = JsonHandler.getMessageDtoObject((String) In.readObject());
				Command command = JsonHandler.getCommandObject(msg.getContent());
				System.out.println("Incoming : " + msg.toString());

				MessageDto result = new MessageDto(Constants.CLIENT_TO_SERVER);
				result.setUserId(msg.getUserId());
				String stringCommand = command.getCommand();
				String[] parms = command.getParms();
				if (stringCommand.equals(CommandConstant.computerDesktop)) {
					result.setContent(Operation.computerDesktopJson().toString());
				} else if (stringCommand.equals(CommandConstant.computerHomeJson)) {
					result.setContent(Operation.computerHomeJson().toString());
				} else if (stringCommand.equals(CommandConstant.computerPartions)) {
					result.setContent(Operation.computerPartionsJson().toString());
				} else if (stringCommand.equals(CommandConstant.computerPathJson)) {
					result.setContent(Operation.computerPathJson(parms[0]).toString());
				} else if (stringCommand.equals(CommandConstant.createNewDirectory)) {
					result.setContent(Operation.createNewDirectory(parms[0], parms[1]) ? "true" : "false");
				} else if (stringCommand.equals(CommandConstant.createNewFile)) {
					result.setContent(Operation.createNewFile(parms[0], parms[1]) ? "true" : "false");
				} else if (stringCommand.equals(CommandConstant.getPCDeviceInfo)) {
					result.setContent(Operation.getPCDeviceInfo().toString());
				} else if (stringCommand.equals(CommandConstant.removeDirectory)) {
					result.setContent(Operation.removeDirectory(parms[0]) ? "true" : "false");
				} else if (stringCommand.equals(CommandConstant.renameDirectory)) {
					result.setContent(Operation.renameDirectory(parms[0], parms[1]) ? "true" : "false");
				}else if(stringCommand.equals(CommandConstant.removeFile)){
					result.setContent(Operation.removeFile(parms[0])? "true" : "false");
				} 
				else if (stringCommand.equals(CommandConstant.filetransfer)) {
					result.setContent("true");

					Command com = new Command(Constants.FIlE_TRANSFARE + "", new String[] { parms[0], parms[1] });
					MessageDto m = new MessageDto(MessageDto.CLIENT_TO_SERVER);
					// System.out.println(m);
					m.setContent(JsonHandler.getCommandJson(com));
					// m.setUserId(1);
					// m.setDeviceId(1);
					m.setUserId(CommonUtil.getUserID());
					m.setDeviceId(CommonUtil.getDeviceID());

					Thread t = new Thread(new Upload(serverAddr, port, new File(parms[1] + "\\" + parms[0]), m));
					t.start();
				}
				send(JsonHandler.getMessageDtoJson(result));
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
