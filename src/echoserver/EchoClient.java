package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class InputReader implements Runnable {

	private final Socket socket;

	public static void Main(Socket socket) {
		InputReader obj = new InputReader();
		Thread inThread = new Thread(obj);
		inThread.start();
	}
	
	public void run() {

		try {
			InputStream stdIn = System.in;
			OutputStream socketOut = socket.getOutputStream();

			int data;
			while ((data = stdIn.read()) != -1) {
				socketOut.write(data);
				socketOut.flush();
			}
		}

		catch (IOException e) {
            e.printStackTrace();
        }
	}	
}

public class OutputWriter implements Runnable {

	private final Socket socket;
	
	public static void Main(Socket socket) {
		OutputWriter obj = new OutputWriter();
		Thread outThread = new Thread(obj);
		outThread.start();
	}

	public void run() {

		try {
			InputStream socketIn = socket.getInputStream();
			OutputStream stdOut = System.out;

			int data;
			while((data = socketIn.read()) != -1) {
				stdOut.write();
				stdOut.flush();
			}
		}

		catch (IOException e) {
            e.printStackTrace();
        }	
	}
}

public class EchoClient {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient();
		client.start();
	}

	private void start() throws IOException {
		Socket socket = new Socket("localhost", PORT_NUMBER);
		InputStream socketInputStream = socket.getInputStream();
		OutputStream socketOutputStream = socket.getOutputStream();

		// Put your code here.
	}
}