import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        out = new PrintWriter(this.socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            System.out.printf("%s connected\n", socket.getInetAddress().toString());
            Gson gson = new Gson();

            if (!in.readLine().equals("/connect"))
                out.println("Disconnected");
            else {
                out.println("/connected");
                boolean userConnected = true;
                do {
                    Message message = gson.fromJson(in.readLine(), Message.class);
                    if(message.isIPRequest())
                        out.println(socket.getInetAddress().toString());
                    else if(message.getMessage().equals("/disconnect")) {
                        System.out.println("Disconnected");
                        userConnected = false;
                    }
                    else {
                        System.out.println(message);
                        out.println(message);
                        //TODO send to others
                    }
                } while (userConnected);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
