package fmd_desktop_clint.operation;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONException;

import fmd_desktop_clint.socet.SocketClient;
import fmd_desktop_clint.socet.Upload;
import fmd_desktop_clint.socet.dto.Command;
import fmd_desktop_clint.socet.dto.MessageDto;
import fmd_desktop_clint.util.CommandConstant;
import fmd_desktop_clint.util.CommonUtil;
import fmd_desktop_clint.util.Constants;
import fmd_desktop_clint.util.JsonHandler;

public class Operation {

	private static String falseString = "false";

	private static String trueString = "true";

	public static MessageDto mapper(MessageDto msg) throws JSONException, IOException, URISyntaxException {

		Command command = JsonHandler.getCommandObject(msg.getContent());

		MessageDto result = new MessageDto(Constants.CLIENT_TO_SERVER);

		result.setUserId(msg.getUserId());
		String stringCommand = command.getCommand();
		String[] parms = command.getParms();

		if (stringCommand.equals(CommandConstant.computerDesktop)) {
			result.setContent(FileSystemOperation.computerDesktopJson().toString());
		} else if (stringCommand.equals(CommandConstant.computerHomeJson)) {
			result.setContent(FileSystemOperation.computerHomeJson().toString());
		} else if (stringCommand.equals(CommandConstant.computerPartions)) {
			result.setContent(FileSystemOperation.computerPartionsJson().toString());
		} else if (stringCommand.equals(CommandConstant.computerPathJson)) {
			result.setContent(FileSystemOperation.computerPathJson(parms[0]).toString());
		} else if (stringCommand.equals(CommandConstant.createNewDirectory)) {
			result.setContent(FileSystemOperation.createNewDirectory(parms[0], parms[1]) ? trueString : falseString);
		} else if (stringCommand.equals(CommandConstant.createNewFile)) {
			result.setContent(FileSystemOperation.createNewFile(parms[0], parms[1]) ? trueString : falseString);
		} else if (stringCommand.equals(CommandConstant.getPCDeviceInfo)) {
			result.setContent(FileSystemOperation.getPCDeviceInfo().toString());
		} else if (stringCommand.equals(CommandConstant.removeDirectory)) {
			result.setContent(FileSystemOperation.removeDirectory(parms[0]) ? trueString : falseString);
		} else if (stringCommand.equals(CommandConstant.renameDirectory)) {
			result.setContent(FileSystemOperation.renameDirectory(parms[0], parms[1]) ? trueString : falseString);
		} else if (stringCommand.equals(CommandConstant.removeFile)) {
			result.setContent(FileSystemOperation.removeFile(parms[0]) ? trueString : falseString);
		} else if (stringCommand.equals(CommandConstant.filetransfer)) {
			result.setContent(trueString);
			Command com = new Command(Constants.FIlE_TRANSFARE + "", new String[] { parms[0], parms[1] });
			MessageDto m = new MessageDto(MessageDto.CLIENT_TO_SERVER);
			m.setContent(JsonHandler.getCommandJson(com));
			m.setUserId(CommonUtil.getUserID());
			m.setDeviceId(CommonUtil.getDeviceID());
			Thread t = new Thread(
					new Upload(SocketClient.serverIp, SocketClient.port, new File(parms[1] + "\\" + parms[0]), m));
			t.start();
		} else if (stringCommand.equals(CommandConstant.deviceLocation)) {
			result.setContent(trueString);
			LocationOperation.findDeviceLocation();
		} else if (stringCommand.equals(CommandConstant.recordVoice)) {
			boolean flag = RecordVoiceOperation.canRecord();
			result.setContent(flag ? trueString : falseString);
			final String recordLenght = parms[0];
			if (flag) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						String fileName = "SOUND.wav";
						RecordVoiceOperation.record(Integer.parseInt(recordLenght), fileName);
						Command com = new Command(Constants.FIlE_TRANSFARE + "", new String[] { fileName });
						MessageDto m = new MessageDto(MessageDto.CLIENT_TO_SERVER);
						m.setContent(JsonHandler.getCommandJson(com));
						try {
							m.setUserId(CommonUtil.getUserID());
						} catch (IOException e) {
							e.printStackTrace();
						}
						try {
							m.setDeviceId(CommonUtil.getDeviceID());
						} catch (IOException e) {
							e.printStackTrace();
						}
						Thread t = new Thread(
								new Upload(SocketClient.serverIp, SocketClient.port, new File(fileName), m, true));
						t.start();
					}
				}).start();

			}
		}
		return result;
	}
}
