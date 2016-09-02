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

	public static void main(String[] args) {
		try {
			ServerSocket serverListener = new ServerSocket(8005);
			Socket clientSocket = serverListener.accept();

			System.out.println("Incoming connection from " + clientSocket.getInetAddress().getHostAddress());

			String inputLine;
			BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			inputLine = inputFromClient.readLine();
			System.out.println(inputLine);

			PrintWriter outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);
			String outMessage = "I'm a message!";
			outputToClient.println(outMessage);
		} catch (IOException ioEx){
			ioEx.printStackTrace();
		}
	}





}
