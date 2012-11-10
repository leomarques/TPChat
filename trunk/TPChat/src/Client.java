/**
 * Client connects to Server and prints all the messages
 * received from the server. It also allows the user to send messages to the
 * server. Client thread reads messages and prints them to the standard
 * output. Sender thread reads messages from the standard input and sends them
 * to the server.
 */

import java.io.*;
import java.net.*;

public class Client {
	public static final String SERVER_HOSTNAME = "localhost";
	public static final int SERVER_PORT = 2002;

	public static void main(String[] args) {
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			// Connect to Server
			Socket socket = new Socket(SERVER_HOSTNAME, SERVER_PORT);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			System.out.println("Connected to server " + SERVER_HOSTNAME + ":"
					+ SERVER_PORT);
		} catch (IOException ioe) {
			System.err.println("Can not establish connection to "
					+ SERVER_HOSTNAME + ":" + SERVER_PORT);
			ioe.printStackTrace();
			System.exit(-1);
		}

		// Create and start Sender thread
		Sender sender = new Sender(out);
		sender.setDaemon(true);
		sender.start();

		try {
			// Read messages from the server and print them
			String message;
			while ((message = in.readLine()) != null) {
				System.out.println(message);
			}
		} catch (IOException ioe) {
			System.err.println("Connection to server broken.");
			ioe.printStackTrace();
		}

	}
}

class Sender extends Thread {
	private PrintWriter mOut;

	public Sender(PrintWriter aOut) {
		mOut = aOut;
	}

	/**
	 * Until interrupted reads messages from the standard input (keyboard) and
	 * sends them to the chat server through the socket.
	 */
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			while (!isInterrupted()) {
				String message = in.readLine();
				mOut.println(message);
				mOut.flush();
			}
		} catch (IOException ioe) {
			// Communication is broken
		}
	}
}