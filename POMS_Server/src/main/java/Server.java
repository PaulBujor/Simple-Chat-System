import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    //gets internet IP from Amazon Web Services
    public static String getIP() throws IOException{
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                whatismyip.openStream()));

        String ip = in.readLine();
        return ip;
    }

    public static void main(String[] args) throws IOException {
        System.out.printf("Server IP: %s\n", getIP());
        System.out.print("Server PORT: ");
        int port = (new Scanner(System.in)).nextInt();
        ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

        System.out.println("Starting server...");

        ServerSocket serverSocket = new ServerSocket(port);
        while(true) {
            System.out.println("Waiting for client...");
            Socket socket = serverSocket.accept();

            ClientHandler client = new ClientHandler(socket);
            clients.add(client);
            Thread clientThread = new Thread(client);
            clientThread.start();
        }
    }
}
