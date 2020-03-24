import com.google.gson.Gson;
import utility.Message;

import java.util.ArrayList;

public class Broadcaster {
    private ArrayList<ClientHandler> clients;

    public Broadcaster() {
        clients = new ArrayList<ClientHandler>();
    }

    public void addClient(ClientHandler client) {
        clients.add(client);
    }

    public void send(Message message) {
            System.out.println(clients.size());
            synchronized (this) {
            for (ClientHandler client : clients) {
                client.send(message);
            }
        }
    }
}
