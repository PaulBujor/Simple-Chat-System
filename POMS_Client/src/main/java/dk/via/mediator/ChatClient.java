package dk.via.mediator;

import dk.via.model.Model;
import dk.via.utility.Message;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient implements ServerModel, Runnable{
    private String user;
    private String host;
    private int port;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean running = false;
    private Model model;
    private Gson gson = new Gson();

    public ChatClient(String host, int port, String user, Model model) throws IOException {
        this.port = port;
        this.host = host;
        this.user = user;
        this.model = model;
        socket = new Socket(host, port);
        //creates input and output streams(connections) to the server
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {
        //todo if disconnected thread still wwaits for message from server
        //todo make getting ip pretty
        //todo just send @received to model and let model do distinction between ip and message
        while(running) {
            Message received = null;
            try {
                received = gson.fromJson(in.readLine(), Message.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!received.isIPRequest())
                model.receiveMessage(received); //sends received message to model
        }
    }

    @Override
    public boolean connect() throws IOException {
        out.println("/connect");
        if(in.readLine().equals("/connected")) {
            System.out.println("connected");
            running = true;
            return true;
        }
        return false;
    }

    @Override
    public void disconnect() {
        running = false;
        //todo stop thread when this happens
        out.println(gson.toJson(new Message(user, "/disconnect")));
    }

    @Override
    public void sendMessage(Message message) {
        //sends message to server
        out.println(gson.toJson(message));
    }

    @Override
    public String requestIP() throws IOException {
        //requests ip from server
        //todo move return into thread + MVVM fire event
        out.println(gson.toJson(new Message(user, "", true)));
        return in.readLine();
    }
}
