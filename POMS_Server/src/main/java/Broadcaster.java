import com.google.gson.Gson;
import utility.Message;

import java.util.ArrayList;

public class Broadcaster {
    //holds all connected clients
    private ArrayList<ClientHandler> clients;

    public Broadcaster() {
        clients = new ArrayList<ClientHandler>();
    }

    public void addClient(ClientHandler client) {
        clients.add(client);
        updateClients();
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
        updateClients();
    }

    private void updateClients() {
        send(new Message("Server", Integer.toString(clients.size()), false, true));
    }

    //calls ClientHandler.send method for each connected client, used to transmit message to all clients
    public void send(Message message) {
            synchronized (this) {
            for (ClientHandler client : clients) {
                client.send(message);
            }
        }
    }
}
