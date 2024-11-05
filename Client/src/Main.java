import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        final int port = 33581;

        try (Socket socket = new Socket("127.0.0.1", port))
        {
            System.out.println("Connected to server");
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }
    }
}