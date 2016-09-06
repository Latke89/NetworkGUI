package sample;

import javafx.scene.canvas.GraphicsContext;
import jodd.json.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Brett on 9/2/16.
 */
public class Server implements Runnable{

	GraphicsContext gc = null;

	@Override
	public void run() {
		startServer(gc);
	}

	public Server(GraphicsContext gc) {
		this.gc = gc;
	}

	//	public static void main(String[] args) {
//		startServer();
//	}

	public void startServer(GraphicsContext gc) {
		try {
			ServerSocket serverListener = new ServerSocket(8005);
//			System.out.println("Ready to accept incoming connections!");

			while (true) {
				Socket clientSocket = serverListener.accept();
				ConnectionHandler myHandler = new ConnectionHandler(clientSocket, gc);
				Thread myThread = new Thread(myHandler);
				myThread.start();

			}


		} catch (IOException serverException) {
			serverException.printStackTrace();
		}
	}


}






