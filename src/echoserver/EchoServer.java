package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	
	public static final int PORT_NUMBER = 6013; 

	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	class SocketStream implements Runnable {
	
		private final Socket socket;
	
		public SocketStream(Socket socket){
		this.socket = socket;
		}

		public void run() {

			try {
				InputStream socketIn = socket.getInputStream();
				OutputStream socketOut = socket.getOutputStream(); 

				int data;
				while((data = socketIn.read()) != -1) {
					socketOut.write(data);
					socketOut.flush();
				}

				socket.shutdownOutput();

				socket.close();
			}

			catch (IOException e) {
        	e.printStackTrace();
       		}
		}
	} 

	private void start() throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
		while (true) {
			Socket socket = serverSocket.accept();

			// Put your code here.
			// This should do very little, essentially:
			SocketStream stream = new SocketStream(socket); // * Construct an instance of your runnable class
			Thread thread = new Thread(stream); // * Construct a Thread with your runnable
			// * Or use a thread pool
			thread.start(); // * Start that thread
		}
	}
}