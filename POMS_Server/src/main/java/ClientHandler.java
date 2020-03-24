import com.google.gson.Gson;
import utility.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private Broadcaster broadcaster;
    private Gson gson;

    public ClientHandler(Socket socket, Broadcaster broadcaster) throws IOException {
        this.socket = socket;
        this.broadcaster = broadcaster;
        in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        out = new PrintWriter(this.socket.getOutputStream(), true);
        gson = new Gson();
    }

    @Override
    public void run() {
        try {
            System.out.printf("%s connected\n", socket.getInetAddress().toString());

            if (!in.readLine().equals("/connect"))
                out.println("Disconnected");
            else {
                out.println("/connected");
                boolean userConnected = true;
                System.out.println("while start");
                do {
                    Message message = gson.fromJson(in.readLine(), Message.class);
                    System.out.println("message received");
                    if(message.isIPRequest())
                        out.println(socket.getInetAddress().toString());
                    else if(message.getMessage().equals("/disconnect")) {
                        System.out.println("Disconnected");
                        userConnected = false;
                    }
                    else {
                        System.out.println(message);
                        broadcaster.send(message);
                    }
                } while (userConnected);
                System.out.println("while end");
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Message message) {
        out.println(gson.toJson(message));
    }
}
