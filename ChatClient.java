import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * The ChatClient class, which will create a new chat room window in a separate GUI for each client.
 * 
 * @author nanaa
 */
public class ChatClient implements ChatClientInterface
{
    private BufferedReader in;
    private PrintWriter out;
    private JFrame chatBox = new JFrame("Chat Room");
    private JTextField messageTextField = new JTextField(40);
    private JTextArea messageBox = new JTextArea(15, 50);
    private Socket clientSocket;
    private static int PORT;
    
    public ChatClient()
    {
    	// I just choose a random port number of 9001 to start the server in.
    	PORT = 9001;	
    	
    	// The ChatClient class file provided for this assignment had the Java Swing package imported, so I will be
        // using Java Swing to create the chat box and textfields for the application.
    	
    	chatBox.setVisible(true);
    	messageTextField.setEditable(false);
    	messageBox.setEditable(false);
        chatBox.getContentPane().add(messageTextField, "North");
        chatBox.getContentPane().add(new JScrollPane(messageBox), "Center");
        chatBox.pack();

        // Add Listeners
        messageTextField.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                out.println(messageTextField.getText());
                messageTextField.setText("");
            }
        });
    }
    
    /**
     * Prompt for and return the desired screen name.
     * 
     * @return the screen name the user wishes to use
     */
    @Override
    public String getName()
    {
        return JOptionPane.showInputDialog(chatBox, "Choose a screen name:", "Screen name selection", JOptionPane.PLAIN_MESSAGE);
    }
    
    
    /**
     * The port number that the server is using
     * 
     * @return the port number for the client to connect to the server
     */
	@Override
	public int getServerPort()
	{	
		return PORT;
	}

    /**
     * Connects to the server then enters the processing loop.
     */
    public void run()
    {    
        try
        {
            // Make the connection to the server using a socket
        	// I just choose a random port number of 9001 to start the server in.
           clientSocket = new Socket("localhost", getServerPort());          
            
          //Use the input and output streams attached to the socket to communicate with the server  
            in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
                   
	        // read the messages from the server and perform the appropriate actions
	        while (true)
	        {
	        	//When the server sends "SUBMITNAME" the client replies with the desired screen name.
	            String line = in.readLine();
	            if (line.startsWith("SUBMITNAME"))
	            {
	            	// Use the getName() method to open a JOption pane box to allow the user to enter a desired screen name. 
	                out.println(getName());
	            }             
	            // Once a screen name has been accepted, then enable the chat textbox in the GUI for the user to enter messages
	            else if (line.startsWith("NAMEACCEPTED"))
	            {
	            	messageTextField.setEditable(true);
	            }
	            // When the client receives messages from the server, it will be displayed in the client’s textarea
	            else if (line.startsWith("MESSAGE"))
	            {
	            	messageBox.append(line.substring(8) + "\n");
	            }          
	        }
        }
		catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
