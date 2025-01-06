package mpks.jabia;

public class Main {
    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start();
        } catch (Exception exception) {
            System.err.println("Exception occurred: " + exception.getMessage());
        }
    }
}