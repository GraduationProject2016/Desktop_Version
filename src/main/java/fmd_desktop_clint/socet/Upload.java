package fmd_desktop_clint.socet;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import org.glassfish.jersey.message.internal.MsgTraceEvent;

import fmd_desktop_clint.socet.dto.Command;
import fmd_desktop_clint.socet.dto.MessageDto;
import fmd_desktop_clint.util.Constants;
import fmd_desktop_clint.util.JsonHandler;

public class Upload implements Runnable {

	public String addr;
	public int port;
	public Socket socket;
	public FileInputStream In;
	public OutputStream Out;
	public File file;
	MessageDto msg;
	boolean delete;

	public Upload(String addr, int port, File filepath, MessageDto m) {
		super();
		try {
			// System.out.println("Upload constract");
			msg = m;
			file = filepath;
			socket = new Socket(InetAddress.getByName(addr), port);
			Out = socket.getOutputStream();
			In = new FileInputStream(filepath);
			delete = false;
		} catch (Exception ex) {
			System.out.println("Exception [Upload : Upload(...)]");
		}
	}

	public Upload(String addr, int port, File filepath, MessageDto m, boolean delete) {
		super();
		try {
			// System.out.println("Upload constract");
			msg = m;
			file = filepath;
			socket = new Socket(InetAddress.getByName(addr), port);
			Out = socket.getOutputStream();
			In = new FileInputStream(filepath);
			this.delete = delete;
		} catch (Exception ex) {
			System.out.println("Exception [Upload : Upload(...)]");
		}
	}

	@Override
	public void run() {
		System.out.println("in upload runB " + file.getAbsolutePath());
		try {
			ObjectOutputStream streamOut = null;
			streamOut = new ObjectOutputStream(socket.getOutputStream());
			streamOut.flush();
			streamOut.writeObject(JsonHandler.getMessageDtoJson(msg));
			streamOut.flush();
			Thread.sleep(1000);
			byte[] buffer = new byte[1024];
			int count;

			while ((count = In.read(buffer)) >= 0) {
				// System.out.println(count);
				Out.write(buffer, 0, count);
			}
			Out.flush();

			if (streamOut != null)
				streamOut.close();

			if (In != null) {
				In.close();
			}
			if (Out != null) {
				Out.close();
			}
			if (socket != null) {
				socket.close();
			}

			if (delete)
				file.delete();

		} catch (Exception ex) {
			System.out.println("Exception [Upload : run()]");
			ex.printStackTrace();
		}
	}

}