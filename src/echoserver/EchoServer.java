package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
	
	public static final int PORT_NUMBER = 6013; 

	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	} 

	class SocketStream implements Runnable {
	
		Socket socket;
		InputStream socketInputStream;
		OutputStream socketOutputStream;
	
		public SocketStream(Socket socket, InputStream socketInputStream, OutputStream socketOutputStream){
			this.socket = socket;
			this.socketInputStream = socketInputStream;
			this.socketOutputStream = socketOutputStream;

		}

		public void run() {

			try {

				int data;
				while((data = socketInputStream.read()) != -1) {
					socketOutputStream.write(data);
					socketOutputStream.flush();
				}

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

			InputStream socketInputStream = socket.getInputStream();
			OutputStream socketOutputStream = socket.getOutputStream();

			ExecutorService pool = Executors.newCachedThreadPool();

			// Put your code here.
			// This should do very little, essentially:
			SocketStream stream = new SocketStream(socket, socketInputStream, socketOutputStream); // * Construct an instance of your runnable class
			Thread thread = new Thread(stream); // * Construct a Thread with your runnable
			// * Or use a thread pool
			// * Start that thread
			pool.execute(thread);
		}
	}
}