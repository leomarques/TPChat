/* 
 * The Server class is entry point for the program. It opens a server
 * socket, starts the dispatcher thread and infinitely accepts client connections,
 * creates threads for handling them and starts these threads.
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	public static final int LISTENING_PORT = 2002;

	public static void main(String[] args) {
		// Open server socket for listening
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(LISTENING_PORT);
			System.out.println("Server started on port " + LISTENING_PORT);
		} catch (IOException se) {
			System.err.println("Can not start listening on port "
					+ LISTENING_PORT);
			se.printStackTrace();
			System.exit(-1);
		}

		// Start ServerDispatcher thread
		ServerDispatcher serverDispatcher = new ServerDispatcher();
		serverDispatcher.start();

		ConnectionsHandler connectionsHandler = new ConnectionsHandler(
				serverSocket, serverDispatcher);
		connectionsHandler.start();

		Scanner scanner = new Scanner(System.in);
		while (true) {
			if (scanner.nextLine().startsWith("q")) {
				connectionsHandler.interrupt();
				try {
					serverDispatcher.closeAllClients();
					serverDispatcher.interrupt();
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				scanner.close();
				System.exit(0);
			}
		}
	}

}

class ConnectionsHandler extends Thread {

	private ServerSocket serverSocket;
	private ServerDispatcher serverDispatcher;

	public ConnectionsHandler(ServerSocket paramSocket,
			ServerDispatcher paramServerDispatcher) {
		serverSocket = paramSocket;
		serverDispatcher = paramServerDispatcher;
	}

	public void run() {
		// Accept and handle client connections
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				ClientInfo clientInfo = new ClientInfo();
				clientInfo.mSocket = socket;
				ClientListener clientListener = new ClientListener(clientInfo,
						serverDispatcher);
				ClientSender clientSender = new ClientSender(clientInfo,
						serverDispatcher);
				clientInfo.mClientListener = clientListener;
				clientInfo.mClientSender = clientSender;
				clientListener.start();
				clientSender.start();
				serverDispatcher.addClient(clientInfo);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}
