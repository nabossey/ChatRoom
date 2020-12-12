import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class ChatServerExecTestSTUDENT
{
	public int CHAT_ROOM_PORT;
	ChatServerExec chatServerExecSTUDENT;
    private Socket clientSocketSTUDENT;
    private BufferedReader clientIn;
    private PrintWriter clientOut;
	private ChatClient chatClientSTUDENT;

	@Before
	public void setUp() throws Exception {
		CHAT_ROOM_PORT  = 8080;
		chatServerExecSTUDENT = new ChatServerExec();
	}

	@After
	public void tearDown() throws Exception {
		chatServerExecSTUDENT = null;
	}


	@Test
	public void testStartServer() {
		String serverMsg="";
		try {
			chatServerExecSTUDENT.startServer(CHAT_ROOM_PORT);
			clientSocketSTUDENT = new Socket("localhost",CHAT_ROOM_PORT);
			       	
        	clientIn = new BufferedReader(new InputStreamReader(clientSocketSTUDENT.getInputStream()));
            clientOut = new PrintWriter(clientSocketSTUDENT.getOutputStream(), true);
            
            serverMsg = clientIn.readLine();
            assertEquals(serverMsg,"SUBMITNAME");
            clientOut.println("Nabeel Hussain");
            serverMsg = clientIn.readLine();
            assertEquals(serverMsg,"NAMEACCEPTED");
            clientOut.println("This is my student test message");
            serverMsg = clientIn.readLine();
            assertEquals(serverMsg,"MESSAGE Nabeel Hussain: This is my student test message");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
      
 
	}

}