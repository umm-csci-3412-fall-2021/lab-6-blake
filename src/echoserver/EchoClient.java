package echoserver;

import java.net.*;
import java.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {

	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient();
		client.start();
	}

	class InputReader implements Runnable {

		private final Socket socket;

		public InputReader(Socket socket){
		this.socket = socket;
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

				socket.shutdownOutput();

				socket.close();
			}

			catch (IOException e) {
            e.printStackTrace();
       		}
		}	
	}

	class OutputWriter implements Runnable {

		private final Socket socket;

		public OutputWriter(Socket socket){
		this.socket = socket;
		}

		public void run() {

			try {
				InputStream socketIn = socket.getInputStream();
				OutputStream stdOut = System.out;

				int data;
				while((data = socketIn.read()) != -1) {
					stdOut.write(data);
					stdOut.flush();
				}

				socket.shutdownOutput();

				socket.close();
			}

			catch (IOException e) {
            e.printStackTrace();
       		}	
		}
	}

	private void start() throws IOException {
		Socket socket = new Socket("localhost", PORT_NUMBER);
		InputStream socketInputStream = socket.getInputStream();
		OutputStream socketOutputStream = socket.getOutputStream();

		// Put your code here.
		while(true) {

			InputReader input = new InputReader(socket);
			Thread inThread = new Thread(input);
			inThread.start();

			OutputWriter output = new OutputWriter(socket);
			Thread outThread = new Thread(output);
			outThread.start();
		}
	}
}