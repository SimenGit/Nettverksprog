import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 8000;

    public static void main(String[] args) throws IOException{
        new Server().runServer();
    }

    public void runServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("PORT: " + PORT);
        System.out.println("Tjener kjører!");
        while(true) {
            Socket socket = serverSocket.accept();
            ServerThread thread = new ServerThread(socket);
            thread.start();
        }
    }
}
