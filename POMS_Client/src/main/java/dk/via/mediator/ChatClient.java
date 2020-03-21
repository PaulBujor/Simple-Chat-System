package dk.via.mediator;

import dk.via.utility.Message;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient implements ServerModel{
    private String user;
    private String host;
    private int port;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Gson gson = new Gson();

    public ChatClient(String host, int port, String user) throws IOException {
        this.port = port;
        this.host = host;
        this.user = user;
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public boolean connect() throws IOException {
        out.println("/connect");
        if(in.readLine().equals("/connected"))
            return true;
        return false;
    }

    @Override
    public void disconnect() {
        out.println(gson.toJson(new Message(user, "/disconnect")));
    }

    @Override
    public void sendMessage(Message message) {
        out.println(gson.toJson(message));
    }

    @Override
    public String requestIP() throws IOException {
        out.println(gson.toJson(new Message(user, "", true)));
        return in.readLine();
    }
}
