/**
 * The client follows the Chat Protocol which is as follows.
 * When the server sends "SUBMITNAME" the client replies with the
 * desired screen name.  The server will keep sending "SUBMITNAME"
 * requests as long as the client submits screen names that are
 * already in use.  When the server sends a line beginning
 * with "NAMEACCEPTED" the client is now allowed to start
 * sending the server arbitrary strings to be broadcast to all
 * chatters connected to the server.  When the server sends a
 * line beginning with "MESSAGE " then all characters following
 * this string should be displayed in its message area.
 * 
 * @author Nabeel Hussain
 */
public class ChatClientExec implements ChatClientExecInterface
{
	
	/**
     * Runs the client in its own thread
     */
	@Override
	public void startClient() throws Exception
	{ 
        //System.out.println("Client Connected");

        //Will create a new ChatClient and run it in its own thread. 
		Thread client = new Thread()
		{		        
			@Override 
            public void run()
			{
		         while (true)
		         {  	 
		        	ChatClient chatClient = new ChatClient();
		        	chatClient.run(); 	   		        	 	   		        	 
		         }
			}		
		};	
		client.start();
	}	
}
