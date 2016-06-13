package testingModule;

import java.io.IOException;

import org.json.JSONException;

import fmd_desktop_clint.operation.RecordVoiceOperation;

public class RecordingTest {

	/*****
	 * test Can Record method
	 * 
	 * @throws IOException
	 * @throws JSONException
	 ****/
	public static void testCanRecord() throws JSONException, IOException {
		System.out.println(RecordVoiceOperation.canRecord());
	}

	/*****
	 * test Record Voice
	 * 
	 * @throws IOException
	 * @throws JSONException
	 ****/
	public static void testRecordVoice() {
		String fileName = "recordFile.mp3";
		System.out.println(RecordVoiceOperation.record(1000, fileName));
	}

	public static void main(String[] args) {

		try {
			testCanRecord();
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
