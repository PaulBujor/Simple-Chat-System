public class Main {
    public static void main(String[] args) {
        ServerThread server = new ServerThread();
        Thread t = new Thread(server);
        t.start();
    }
}
