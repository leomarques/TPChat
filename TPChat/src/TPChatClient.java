import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// Connect to Server
public class TPChatClient {
	public static final int SERVER_PORT = 2002;

	public static void main(String[] args) {
		ClientGUI clientGUI = new ClientGUI();
		JTextArea textArea = clientGUI.getTextArea();
		JTextField textField = clientGUI.getTextField();

		String serverHostName = (String) JOptionPane
				.showInputDialog("Server hostname:");
		textArea.append("Welcome to TPChat!\n");

		BufferedReader in = null;
		PrintWriter out = null;
		Socket socket = null;
		try {
			// Connect to Server
			socket = new Socket(serverHostName, SERVER_PORT);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			textArea.append("Connected to server " + serverHostName + ":"
					+ SERVER_PORT);
		} catch (IOException ioe) {
			textArea.append("Can not establish connection to " + serverHostName
					+ ":" + SERVER_PORT);
			ioe.printStackTrace();
			System.exit(-1);
		}

		textArea.append("\n\n");

		Receiver receiver = new Receiver(textArea, in);
		receiver.start();

		textField.addKeyListener(new Sender(out, textField));

		clientGUI.addWindowListener(new SocketCloser(socket, receiver));
	}
}

class SocketCloser implements WindowListener {

	private Socket socket;
	private Receiver receiver;

	public SocketCloser(Socket paramSocket, Receiver paramReceiver) {
		socket = paramSocket;
		receiver = paramReceiver;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void windowClosing(WindowEvent e) {
		receiver.stop();

		try {
			socket.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}
}
