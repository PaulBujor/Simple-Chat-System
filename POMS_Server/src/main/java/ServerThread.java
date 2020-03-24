import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerThread implements Runnable {
    private Broadcaster broadcaster;
    private int port;

    public ServerThread() {
        broadcaster = new Broadcaster();
    }

    //gets internet IP of server using Amazon Web Services
    private String getIP() throws IOException{
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                whatismyip.openStream()));

        String ip = in.readLine();
        return ip;
    }

    public void run() {
        try {
            System.out.printf("Server IP: %s\n", getIP());
            System.out.print("Server PORT: ");
            port = (new Scanner(System.in)).nextInt();

            System.out.println("Starting server...");

            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("Server started...");
            while (true) {
                //Waits for new client and starts new ClientHandler thread for that client
                Socket socket = serverSocket.accept();

                ClientHandler client = new ClientHandler(socket, broadcaster);
                broadcaster.addClient(client);
                Thread clientThread = new Thread(client);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
