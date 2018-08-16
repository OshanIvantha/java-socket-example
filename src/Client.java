import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private BufferedReader in;
    private PrintWriter out;

    public void connectToServer() throws IOException {
        JFrame frame = new JFrame("Capitalize Client");
        JTextArea messageArea = new JTextArea(8, 60);

        // Get the server address from a dialog box.
        String serverAddress = JOptionPane.showInputDialog(frame, "Enter IP Address of the Server:",
                "Welcome to the Capitalization Program", JOptionPane.QUESTION_MESSAGE);

        // Make connection and initialize streams
        Socket socket = new Socket(serverAddress, 9898);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Consume the initial welcoming messages from the server
        for (int i = 0; i < 3; i++) {
            messageArea.append(in.readLine() + "\n");
        }
    }

    public void talkWithServer() {
        out.println("Oshan Ivantha");
        String response;
        try {
            response = in.readLine();
            if (response == null || response.equals("")) {
                System.exit(0);
            }
        } catch (IOException ex) {
            response = "Error: " + ex;
        }
        System.out.println("Response: " + response + "\n");
        System.exit(0);
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.connectToServer();
        client.talkWithServer();
    }

}
