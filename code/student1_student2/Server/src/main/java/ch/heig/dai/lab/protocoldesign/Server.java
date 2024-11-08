package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import java.util.List;

public class Server {
    final int SERVER_PORT = 1234;
    private final List<String> supportedOperations = List.of("ADD", "SUB", "MUL", "DIV", "QUIT");

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server is running...");

            while (!serverSocket.isClosed()) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                    // Send welcome message with supported operations
                    out.write("Welcome! Supported operations: " + String.join(", ", supportedOperations) + "\n");
                    out.flush();
                    System.out.println("Sent welcome message to client.");

                    // Read commands from the client
                    String command;
                    while (!socket.isClosed() && (command = in.readLine()) != null) {
                        System.out.println("Received command from client: " + command);
                        if (command.equals("QUIT")) {
                            System.out.println("Client disconnected.");
                            socket.close();
                            break;
                        }
                        String response = processCommand(command);
                        out.write(response + "\n");
                        out.flush();
                        System.out.println("Sent response to client: " + response);
                    }
                } catch (IOException e) {
                    System.err.println("Server: Error handling client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Server: Could not start on port " + SERVER_PORT + ": " + e.getMessage());
        }
    }

    private String processCommand(String command) {
        String[] parts = command.split(" ");
        if (parts.length != 3) {
            return "Error: Command format should be: OPERATION operand1 operand2";
        }

        String operation = parts[0].toUpperCase();
        try {
            int op1 = Integer.parseInt(parts[1]);
            int op2 = Integer.parseInt(parts[2]);

            return switch (operation) {
                case "ADD" -> String.valueOf(op1 + op2);
                case "SUB" -> String.valueOf(op1 - op2);
                case "MUL" -> String.valueOf(op1 * op2);
                case "DIV" -> op2 == 0 ? "Error: Division by zero" : String.valueOf(op1 / op2);
                default -> "Error: Unsupported operation";
            };
        } catch (NumberFormatException e) {
            return "Error: Operands must be integers";
        }
    }
}
