package sample;

import com.sun.deploy.util.SessionState;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import jodd.json.JsonParser;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Brett on 8/25/16.
 */
public class ConnectionHandler implements Runnable {

	Socket connection;
	GraphicsContext gc = null;
	ArrayList<StrokeContainer> strokeList = new ArrayList<>();

	public ConnectionHandler(Socket connection, GraphicsContext gc) {
		this.connection = connection;
		this.gc = gc;
	}


	public void run() {
		handleIncomingConnections(connection, gc);
	}


	private void handleIncomingConnections(Socket inputSocket, GraphicsContext gc) {
		try {
			System.out.println("Incoming connection from " + inputSocket.getInetAddress().getHostAddress());

			BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(inputSocket.getInputStream()));
			PrintWriter outputToClient = new PrintWriter(inputSocket.getOutputStream(), true);

			String inputLine;
			while ((inputLine = inputFromClient.readLine()) != null) {
				StrokeContainer myStroke = jsonRestore(inputLine);
				gc.strokeOval(myStroke.posX, myStroke.posY, myStroke.strokeSize, myStroke.strokeSize);
				outputToClient.println("Thanks, buddy!");
			}
		}catch (IOException exception){
			exception.printStackTrace();
		}
	}


	public StrokeContainer jsonRestore(String jsonTD) {
		JsonParser graphicsContextParser = new JsonParser();
		StrokeContainer container = graphicsContextParser.parse(jsonTD, StrokeContainer.class);

		return container;
	}

//	public ArrayList<StrokeContainer> jsonListRestore(String jsonTD) {
//		JsonParser graphicsContextParser = new JsonParser();
//		ArrayList<StrokeContainer> myList = graphicsContextParser.parse(jsonTD, StrokeContainer.class);
//
//		return myList;
//	}


}

