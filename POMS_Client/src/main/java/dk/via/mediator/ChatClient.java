package dk.via.mediator;

import dk.via.model.Model;
import dk.via.utility.Message;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ChatClient implements ServerModel, Runnable {
    private String user;
    private String host;
    private int port;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
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
        //todo if disconnected thread still waits for message from server
        while (true) {
            Message received = null;
            try {
                received = gson.fromJson(in.readLine(), Message.class);
            }catch (SocketException e) {
                System.out.println("Unexpected error has occured");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                model.receiveMessage(received); //sends received message to model
            } catch (NullPointerException e) {
                System.out.println("You disconnected");
                break;
            }
        }
    }

    @Override
    public boolean connect() throws IOException {
        out.println("/connect");
        String reply = in.readLine();
        int users = Integer.parseInt(in.readLine());
        System.out.println(users);
        if (reply.equals("/connected")) {
            System.out.println("connected");
            return true;
        }
        return false;
    }

    @Override
    public void disconnect() {
        out.println(gson.toJson(new Message(user, "/disconnect")));
    }

    @Override
    public void sendMessage(Message message) {
        //sends message to server
        out.println(gson.toJson(message));
    }
    //TODO had to comment this out
/*
    @Override
    public void requestIP() throws IOException {
        //requests ip from server
        //todo move return into thread + MVVM fire event
        out.println(gson.toJson(new Message(user, "", true)));
    }*/
}
