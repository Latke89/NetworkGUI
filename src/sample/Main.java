package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jodd.json.JsonParser;
import jodd.json.JsonSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main extends Application {

	final double DEFAULT_SCENE_WIDTH = 800;
	final double DEFAULT_SCENE_HEIGHT = 600;
	int strokeSize = 10;
	boolean drawing = false;
	double xPosition;
	double yPosition;
	boolean sharing = false;
	String jsonString = null;

	GraphicsContext gc = null;


	StrokeContainer currentStroke;

	GraphicsContext graphicsContext2 = null;

	@Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");

		//This is a grid layout
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setGridLinesVisible(false);

		// add buttons and canvas to the grid
		Text sceneTitle = new Text("I can taste colors!");
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(sceneTitle, 0, 0);

		Button button = new Button("New paint window");
		Button serverButton = new Button("Connect to server");
		Button thirdButton = new Button("I'm a server!");
		HBox hbButton = new HBox(10);
		hbButton.setAlignment(Pos.TOP_LEFT);
		hbButton.getChildren().add(button);
		hbButton.getChildren().add(serverButton);
		hbButton.getChildren().add(thirdButton);
		grid.add(hbButton, 0, 1);

		thirdButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Server myServer = new Server(gc);
				Thread serverThread = new Thread(myServer);
				serverThread.start();
			}

		});

		serverButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				startClient();
				sharing = true;
			}

		});

		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("I can switch to another scene here ...");
//                primaryStage.setScene(loginScene);
                startSecondStage();
			}
		});

		// add canvas
		Canvas canvas = new Canvas(DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT-100);

		gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.GREEN);
		gc.setStroke(Color.BLUE);
		gc.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
		gc.setLineWidth(5);

		canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
//                System.out.println("x: " + e.getX() + ", y: " + e.getY());
				if (drawing == true) {
					if (e.isDragDetect()) {
						gc.strokeOval(e.getX(), e.getY(), strokeSize, strokeSize);
						currentStroke = new StrokeContainer(e.getX(), e.getY(), strokeSize);
						jsonString = jsonStringGenerate(currentStroke);
						startClient();
//						System.out.println(jsonString);

					}
				}

				xPosition = e.getX();
				yPosition = e.getY();

//				String jsonString = jsonStringGenerate(myContainer);
//				System.out.println(jsonStringGenerate(myContainer));
//				System.out.println(jsonString);

				if (graphicsContext2 != null) {
					graphicsContext2.strokeOval(xPosition, yPosition, strokeSize, strokeSize);
				}

//				System.out.println(xPosition);

			}
		});

		grid.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				System.out.println(e.getCode());
				if(e.getCode().equals(KeyCode.UP)) {
					strokeSize+=2;
					if(strokeSize > 100){
						strokeSize-=2;
					}
				}
				if(e.getCode().equals(KeyCode.DOWN)) {
					strokeSize-=2;
					if(strokeSize < 10) {
						strokeSize+=2;
					}
				}
				if(e.getCode().equals(KeyCode.D)){
					drawing = !drawing;
				}
			}
		});


		grid.add(canvas, 0, 2);



		Scene defaultScene = new Scene(grid, DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT);

        primaryStage.setScene(defaultScene);
        primaryStage.show();
    }

	public Main() {
	}

	public void startSecondStage() {
		Stage secondaryStage = new Stage();
		secondaryStage.setTitle("Welcome to JavaFX");

		// we're using a grid layout
		GridPane grid = new GridPane();

		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setGridLinesVisible(false);
//        grid.setPrefSize(primaryStage.getMaxWidth(), primaryStage.getMaxHeight());

		Text sceneTitle = new Text("Welcome to Paint application");
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(sceneTitle, 0, 0);


		// add canvas
		Canvas canvas = new Canvas(DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT-100);

		graphicsContext2 = canvas.getGraphicsContext2D();
//		graphicsContext2.setFill(Color.GREEN);
//		gc.setStroke(Color.BLUE);
		graphicsContext2.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
		graphicsContext2.setLineWidth(5);


//		gc.strokeOval(xPosition, yPosition, strokeSize, strokeSize);

		jsonStringGenerateGC(graphicsContext2);


		grid.add(canvas, 0, 2);

		// set our grid layout on the scene
		Scene defaultScene = new Scene(grid, DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT);

		secondaryStage.setScene(defaultScene);
		System.out.println("Constructing pylons...");

		secondaryStage.show();
	}

	public String jsonStringGenerate(StrokeContainer container) {
		JsonSerializer jsonSerializer = new JsonSerializer().deep(true);
		String jsonContainer = jsonSerializer.serialize(container);

		return jsonContainer;
	}

	public String jsonStringGenerateGC(GraphicsContext myGC) {
		JsonSerializer jsonSerializer = new JsonSerializer().deep(true);
		String jsonGC = jsonSerializer.serialize(myGC);

		return jsonGC;
	}


	public void startClient() {
		try {
			Socket clientSocket = new Socket("localhost", 8005);

			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


				out.println(jsonString);
				String serverResponse = in.readLine();
				System.out.println(serverResponse);


//			startSecondStage();

			clientSocket.close();
		} catch (IOException ioEx){
			ioEx.printStackTrace();
		}
	}

	public static void main(String[] args) {
        launch(args);
    }
}
