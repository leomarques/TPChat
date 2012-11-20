import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;

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
		} catch (SocketException ioe) {
			//Client quit, close thread
			return;
		} catch (IOException ioe) {
			textArea.append("Connection to server broken.\n");
		}
		textArea.append("Server closed.\n");
	}
}