import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TPChatClient {
	public static final String SERVER_HOSTNAME = "localhost";
	public static final int SERVER_PORT = 2002;

	public static void main(String[] args) {
		ClientGUI clientGUI = new ClientGUI();
		JTextArea textArea = clientGUI.getTextArea();
		JTextField textField = clientGUI.getTextField();

		textArea.append("Welcome to TPChat!\n");

		BufferedReader in = null;
		PrintWriter out = null;
		try {
			// Connect to Server
			Socket socket = new Socket(SERVER_HOSTNAME, SERVER_PORT);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			textArea.append("Connected to server " + SERVER_HOSTNAME + ":"
					+ SERVER_PORT);
		} catch (IOException ioe) {
			textArea.append("Can not establish connection to "
					+ SERVER_HOSTNAME + ":" + SERVER_PORT);
			ioe.printStackTrace();
			System.exit(-1);
		}

		textArea.append("\n\n");

		Receiver receiver = new Receiver(textArea, in);
		receiver.start();

		textField.addKeyListener(new Sender(out, textField));
	}

}
