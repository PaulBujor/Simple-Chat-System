import com.google.gson.Gson;
import utility.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler implements Runnable {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private Broadcaster broadcaster;
    private Gson gson;

    public ClientHandler(Socket socket, Broadcaster broadcaster) throws IOException {
        this.socket = socket;
        this.broadcaster = broadcaster;
        gson = new Gson();
        //creates input and output streams(connections) to the client
        in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        out = new PrintWriter(this.socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            System.out.printf("%s connected\n", socket.getInetAddress().toString());

            if (!in.readLine().equals("/connect"))
                out.println("Disconnected");
            else {
                out.println("/connected");
                out.println(broadcaster.getNumberOfClients());
                boolean userConnected = true;
                do {
                    //reads message from server
                    Message message = gson.fromJson(in.readLine(), Message.class);
                    if(message.isIPRequest())
                        out.println(gson.toJson(new Message("server", socket.getInetAddress().toString(), true))); //returns IP address of client
                    else if(message.getMessage().equals("/disconnect")) {
                        System.out.println("Disconnected");
                        userConnected = false; //condition to exit loop
                        broadcaster.removeClient(this); //removes current client from Broadcaster
                    }
                    else {
                        //sends message
                        System.out.println("message sent");
                        System.out.println(message);
                        broadcaster.send(message);
                    }
                } while (userConnected);
            }
            socket.close();
        } catch (SocketException e) {
            //if user force-closes program
            System.out.println("Client disconnected");
            broadcaster.removeClient(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Message message) {
        //sends message to THIS client
        out.println(gson.toJson(message));
    }
}
