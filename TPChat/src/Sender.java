import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

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