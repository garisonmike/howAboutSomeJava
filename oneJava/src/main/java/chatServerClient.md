	Simple Chat Application in Java (Client–Server Model)

	Brief explanation of how the system works
The server keeps a thread-safe set of connected clients. Each new connection gets its own thread via ClientHandler. When someone sends a message the server timestamps it and forwards it to everyone else, then echoes it back to the sender too so they see it in their own chat window.
The client runs the network listener on a background thread so the UI never freezes. All UI updates go through SwingUtilities.invokeLater since Swing isn't thread-safe.
When someone joins or leaves the server broadcasts a notification to the whole room and sends an updated user list so every client's sidebar stays in sync.


	Description of the socket communication process.
The server opens a ServerSocket on port 5123 and sits in a loop calling accept(), which blocks until a client connects. Each time one does, a new Socket is handed off to a ClientHandler thread so the server can immediately go back to waiting for the next one.
On the client side, ChatClient opens a Socket to the server's IP and port. Once that connection is established the first thing it sends is the username — the server uses that to identify the client for the rest of the session.
From there both sides wrap their socket's streams in a BufferedReader and PrintWriter so they can send plain text lines back and forth. The server reads incoming lines in a loop — if it's /quit it closes the connection, otherwise it timestamps the message and broadcasts it. The client does the same on its listener thread, reading lines and routing them to the right display style depending on what they contain.
When a client disconnects — cleanly or not — the finally block in ClientHandler fires, removes them from the active set and broadcasts a leave notification to whoever is still connected.


	Steps to compile and run the application.	
If you're on NetBeans just open the two projects:
  Right click and select 'Run File' or use shortcut "Shift + F6"
  Start with the ChatServer.
  You can run the ChatClient code file severally to spin up several clients.
  In the ChatClient GUI, type the server address (localhost if it's on your machine), pick a username and select connect.
  Check the NetBeans Output pane again.
  You will see a new sub-tab next to your Server output tab.
  You can click between them to see the Server logs updating in real-time as your Clients connect.
