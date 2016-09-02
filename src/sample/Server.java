package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Brett on 9/2/16.
 */
public class Server {

	public void startServer() {
		try {
			ServerSocket serverListener = new ServerSocket(8005);
			Socket clientSocket = serverListener.accept();

			System.out.println("Incoming connection from " + clientSocket.getInetAddress().getHostAddress());

			BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (IOException ioEx){
			ioEx.printStackTrace();
		}
	}





}
