package sample;

import com.sun.deploy.util.SessionState;
import javafx.application.Platform;
import jodd.json.JsonParser;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Brett on 8/25/16.
 */
public class ConnectionHandler implements Runnable {

	Socket connection;

	public ConnectionHandler(Socket incomingConnection) {
		this.connection = incomingConnection;
	}

	public void run() {
		handleIncomingConnections(connection);
	}


	private void handleIncomingConnections(Socket inputSocket) {
		try {
			System.out.println("Incoming connection from " + inputSocket.getInetAddress().getHostAddress());

			BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(inputSocket.getInputStream()));
			PrintWriter outputToClient = new PrintWriter(inputSocket.getOutputStream(), true);

			Main myMain = new Main();
			myMain.main(null);

//			Platform.runLater(new RunnableGC(gc, stroke));


//			jsonRestore(myMain.getJsonString());

//			myMain.startSecondStage();




			String inputLine;
			while ((inputLine = inputFromClient.readLine()) != null) {
//				System.out.println(clientName + " says: " + inputLine);
				System.out.println("Received message: " + inputLine + " from " + inputSocket.toString());

				outputToClient.println("Message received loud and clear");
			}
		}catch (IOException exception){
			exception.printStackTrace();
		}
	}

	public RunnableGC jsonRestoreGC(String jsonTD) {
		JsonParser graphicsContextParser = new JsonParser();
		RunnableGC myGC = graphicsContextParser.parse(jsonTD, RunnableGC.class);

		return myGC;
	}

	public StrokeContainer jsonRestore(String jsonTD) {
		JsonParser graphicsContextParser = new JsonParser();
		StrokeContainer container = graphicsContextParser.parse(jsonTD, StrokeContainer.class);

		return container;
	}


}

