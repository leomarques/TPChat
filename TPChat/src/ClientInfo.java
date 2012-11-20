/**
 * ClientInfo class contains information about a client, connected to the server.
 */

import java.net.Socket;

public class ClientInfo {
	public Socket mSocket = null;
	public ClientListener mClientListener = null;
	public ClientSender mClientSender = null;
	
	public String toString() {
		String ip = mSocket.getInetAddress().getHostAddress();
		String port = "" + mSocket.getPort();
		return ip + ":" + port;
	}
}