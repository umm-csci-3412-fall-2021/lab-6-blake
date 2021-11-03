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

		Socket socket;
		OutputStream socketOutputStream ;

		public InputReader(Socket socket, OutputStream socketOutputStream){
		this.socket = socket;
		this.socketOutputStream  = socketOutputStream;
		}
	
		public void run() {

			try {

				InputStream stdIn = System.in;

				int data;
				while ((data = stdIn.read()) != -1) {
					socketOutputStream .write(data);
					socketOutputStream .flush();
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

		Socket socket;
		InputStream socketInputStream;

		public OutputWriter(Socket socket, InputStream socketInputStream){
			this.socket = socket;
			this.socketInputStream = socketInputStream;
		}

		public void run() {

			try {

				OutputStream stdOut = System.out;

				int data;
				while((data = socketInputStream.read()) != -1) {
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

		// Put your code here

		InputReader input = new InputReader(socket, socketOutputStream);
		Thread inThread = new Thread(input);
		inThread.start();
		inThread.join();

		OutputWriter output = new OutputWriter(socket, socketInputStream);
		Thread outThread = new Thread(output);
		outThread.start();
		outThread.join();
	
	}
}