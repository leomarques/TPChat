import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JTextArea;

public class Receiver extends Thread {
	private JTextArea textArea;
	private BufferedReader in;

	public Receiver(JTextArea paramTextArea, BufferedReader paramIn) {
		textArea = paramTextArea;
		in = paramIn;
	}

	public void run() {
		try {
			// Read messages from the server and print them
			String message;
			while ((message = in.readLine()) != null) {
				textArea.append(message + "\n");
			}
		} catch (IOException ioe) {
			textArea.append("Connection to server broken.\n");
			ioe.printStackTrace();
		}
	}
}