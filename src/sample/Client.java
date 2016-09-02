package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Brett on 9/2/16.
 */
public class Client {

	public static void main(String[] args) {

		try {
			Socket clientSocket = new Socket("localhost", 8005);

			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			out.println("Marvin says hello!");
			String serverResponse = in.readLine();

			// close the connection
			clientSocket.close();
		} catch (IOException ioEx){
			ioEx.printStackTrace();
		}
	}


}
