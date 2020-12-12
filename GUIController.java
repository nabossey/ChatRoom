

import javax.swing.JOptionPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

/**
 * This program will implement a GUI application to simulate a Chat Room.  
 * 
 * @author nanaa
 */
public class GUIController extends Application {
	
	// Declares variables to hold the Labels for the instructions that will be displayed
	Label instructionTitleLabel;
	Label step1InstructionLabel;
	Label step2InstructionLabel;
	Label step3InstructionLabel;
	Label step4InstructionLabel;
	Label step5InstructionLabel;
	

	Button startServerButton;       // Declares variable to hold the "Start the Server" button
	Button startClientButton;    	// Declares variable to hold the "Start each Client" button
	Button exitButton;   			// Declares variable to hold the "Exit" button
	
	// Counter to make sure the server can only be started once
	int counter = 0;

	/**
	 * Sets the layout of the GUI, with all the labels, textfields, and buttons that the chat room application will need to perform all its functions. 
	 */
	@Override
	public void start(Stage stage)
	{
		// Create the Labels that will be used to display the instructions.
	
		instructionTitleLabel = new Label("Chat Room Controller");
		instructionTitleLabel.setFont(new Font(18));
		
		
		step1InstructionLabel = new Label("1. Start the server.");
		step2InstructionLabel = new Label("2. Start a client.");
		step3InstructionLabel = new Label("3. Enter a screen name in the clients's GUI ");
		step4InstructionLabel = new Label("4. Start more clients.");
		step5InstructionLabel = new Label("5. Enter a message in a client's GUI.");
		
	

		// Create the buttons that will be used by the Chat Room Controller to setup the server and start each client. 	
		startServerButton = new Button("Start the _Server");
		// Assign the letter S in the word Server as the mnemonic for the "startServerButton" button
		startServerButton.setMnemonicParsing(true);
		// Add a tooltip to the "startServerButton" button
		startServerButton.setTooltip(new Tooltip("Click here to start the server."));
		
		startClientButton = new Button("Start each _Client");
		// Assign the letter C in the word Client as the mnemonic for the "startClientButton" button
		startClientButton.setMnemonicParsing(true);
		// Add a tooltip to the "startServerButton" button
		startClientButton.setTooltip(new Tooltip("Click here to start a client."));
		
		exitButton = new Button("_Exit");
		// Assign the E key as the mnemonic for the "Exit" button
		exitButton.setMnemonicParsing(true);
		// Add a tooltip to the "Exit" button
		exitButton.setTooltip(new Tooltip("Click here to exit."));
		//exitButton.setPadding(new Insets(5, 18, 5, 18));
			
	
		// Sets event handlers on each button, which will perform different actions, depending on which button the user clicks. 
		startServerButton.setOnAction(new StartServerButtonEventHandler());
		startClientButton.setOnAction(new StartClientButtonEventHandler());	
		exitButton.setOnAction(new ExitButtonEventHandler());	
		
		
		// Create vertical box to place the labels that will display the instructions
		VBox instructionTitlePane = new VBox();
		instructionTitlePane.setAlignment(Pos.CENTER_LEFT);
		instructionTitlePane.getChildren().addAll(instructionTitleLabel);
		
		
		// Create vertical box to place the labels that will display the instructions
		VBox instructionPane = new VBox(2);
		instructionPane.setAlignment(Pos.CENTER_LEFT);
		instructionPane.setPadding(new Insets(10,180,15,70));
		instructionPane.setStyle("-fx-border-color: gray;");
		// Add the buttons to the children of the addActorPane vertical box
		instructionPane.getChildren().addAll(instructionTitlePane, step1InstructionLabel, step2InstructionLabel, step3InstructionLabel, step4InstructionLabel, step5InstructionLabel);
		
		
		HBox buttonPane = new HBox(15);
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.setPadding(new Insets(15, 0, 15, 0));
		// Add the buttons to the children of the buttonPane horizontal box
		buttonPane.getChildren().addAll(startServerButton, startClientButton, exitButton);
			
	
		// Create a vertical box that will nest the addActorBoxPane on top, followed by the addMovieBoxPane at the bottom. 
		VBox contentPane = new VBox();
		contentPane.setAlignment(Pos.CENTER);
		//contentPane.setPadding(new Insets(15, 50, 0, 50));
		contentPane.getChildren().addAll(instructionPane, buttonPane);
		
		
		// Create a BorderPane to place contentPane into the center of the GUI display.
		// contentPane contains all the nested Hbox's and Vbox's that were created to properly organize and display the contents of the GUI application.  
		BorderPane displayPane = new BorderPane();
		// Place the contentPane in the center region of the BorderPane.
		displayPane.setCenter(contentPane); 
		
		// Set displayPane as root of scene and set the scene on the stage
		Scene scene = new Scene(displayPane);
		stage.setTitle("Chat Room Controller");
		stage.setScene(scene);
		stage.show();	
	}

	
	// An event handler for the Start Server button, which will start the server which will be used to communicate with the clients of the chatroom. 
	class StartServerButtonEventHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent event)
		{
			// if no server has been started yet, then start the server
			if(counter < 1)
			{
				ChatServerExec server = new ChatServerExec();
				
				// I just choose a random port number of 9001 to start the server in. 
				server.startServer(9001);
				
				// display JOption Pane message if server has started
				JOptionPane.showMessageDialog(null, "The server has been started.");
				
				counter++;
			}
			// if a server has already been started, then display message saying another one cannot be started if the user clicks the button again. 
			else
			{
				JOptionPane.showMessageDialog(null, "Cannot Start more than one server.");	
			}
		}
	}
		
	// An event handler for the Start Client button, which will open up the chat room if a client is started successfully. 
	class StartClientButtonEventHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent event)
		{
			// can only start a client if  server has been stared first
			if(counter >= 1)
			{
				//Each time the “Start Client” button is selected in the GUI, create a new ChatClientExec.
				ChatClientExec client = new ChatClientExec();
				
				try
				{
					client.startClient();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			
			// if the server has not been started, then display message saying to start it first.
			else
			{
				JOptionPane.showMessageDialog(null, "Must start server first.");	
			}	
		}
	}

	// Will exit the program, if user clicks the "Exit" button. 
	class ExitButtonEventHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent event)
		{
			System.exit(0);		
		}
	}
	
	/**
	 * Will launch the GUI for the Actor Graph application.
	 */
	public static void main(String[] args) {
        launch(args);
    }	
}
