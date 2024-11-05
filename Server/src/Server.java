import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final Logger logger;
    ServerSocket socket;
    final int socketPort = 33581;
    boolean running;

    public Server() throws IOException {
        logger = new Logger();
        running = true;
    }

    public void run() throws IOException {
        logger.write("Server started!");
        socket = new ServerSocket(socketPort);
        logger.write("Socket opened.");

        while (running) {
            Socket clientSocket = socket.accept();
            logger.write("Client connected! " + clientSocket);
        }

    }
}
