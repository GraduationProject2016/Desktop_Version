package fmd_desktop_clint.operation;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class RecordVoiceOperation {

	static boolean flag;

	public static synchronized boolean canRecord() {
		AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		if (!AudioSystem.isLineSupported(info)) {
			System.out.println("line is not supported");
			return false;
		}
		return true;
	}

	public static synchronized boolean record(int trackLenghtInMileSecond, final String fileName) {
		System.out.println("starting sound test");
		try {
			flag = true;
			AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);

			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

			if (!AudioSystem.isLineSupported(info)) {
				System.out.println("line is not supported");
				return false;
			}

			final TargetDataLine targetline = (TargetDataLine) AudioSystem.getLine(info);
			targetline.open();
			System.out.println("reording is starting....");
			targetline.start();
			Thread thread = new Thread() {
				@Override
				public void run() {
					AudioInputStream audiostream = new AudioInputStream(targetline);
					File audioFile = new File(fileName);
					try {
						AudioSystem.write(audiostream, AudioFileFormat.Type.WAVE, audioFile);
					} catch (IOException ioe) {
						flag = false;
						ioe.printStackTrace();
					}
				}
			};
			thread.start();
			Thread.sleep(1000 + trackLenghtInMileSecond);
			targetline.stop();
			targetline.close();
			System.out.println("ended");
			return flag;
		} catch (LineUnavailableException lue) {
			lue.printStackTrace();
			return false;
		} catch (InterruptedException ie) {
			ie.printStackTrace();
			return false;
		}
	}
}
