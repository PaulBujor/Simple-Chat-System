public class Main {
    public static void main(String[] args) {
        //Starts server
        ServerThread server = new ServerThread();
        Thread t = new Thread(server);
        t.start();
    }
}
