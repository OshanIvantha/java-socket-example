import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{

    private Socket socket;
    private int clientNumber;

    private Server(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;

        System.out.println("New connection with client# " + clientNumber + " at " + socket);
    }

    public void run() {
        try {
            // Decorate the streams so we can send characters
            // and not just bytes.  Ensure output is flushed
            // after every newline.
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Send a welcome message to the client.
            out.println("Hello, you are client #" + clientNumber + ".");
            out.println("Enter a line with only a period to quit\n");

            // Get messages from the client, line by line; return them
            // capitalized
            while (true) {
                String input = in.readLine();
                if (input == null || input.equals(".")) {
                    break;
                }

                // Splitting the input.
                // If you are using JSON, you can deserialize here
                String[] inputList = input.split(",");
                for(int i = 0; i < inputList.length; i++){
                    System.out.println("Input " + i + " : " + inputList[i]);
                }

                out.println(input.toUpperCase());
            }
        } catch (IOException e) {
            System.out.println("Error handling client# " + clientNumber + ": " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Couldn't close a socket, what's going on?");
            }
            System.out.println("Connection with client# " + clientNumber + " closed");
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("The server is running.");

        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(9898);

        try {
            while (true) {
                new Server(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        }
    }

}
