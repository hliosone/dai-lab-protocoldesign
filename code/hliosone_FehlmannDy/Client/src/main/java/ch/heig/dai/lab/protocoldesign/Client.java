package ch.heig.dai.lab.protocoldesign;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

enum OPERATION {ADD,SUB,MUL};

public class Client {
    final String SERVER_ADDRESS = "172.20.10.5";
    final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println(in.readLine());

            while(!socket.isClosed()) {


                // Display menu
                System.out.println("Select an operation:");
                System.out.println("1. ADD");
                System.out.println("2. MUL");
                System.out.println("3. SUB");
                System.out.println("4. QUIT");
                System.out.print("Enter your choice (1-4): ");

                int choice = Integer.parseInt(reader.readLine());

                // Get two numbers
                System.out.print("Enter the first integer: ");
                int num1 = Integer.parseInt(reader.readLine());

                System.out.print("Enter the second integer: ");
                int num2 = Integer.parseInt(reader.readLine());

                // Perform operation based on user choice
                switch (choice) {
                    case 1:
                        out.write("ADD " + num1 + " " + num2 + "\n");
                        out.flush();
                        System.out.println("Result of addition: " + in.readLine() + "\n");
                        break;
                    case 2:
                        out.write("MUL " + num1 + " " + num2 + "\n");
                        out.flush();
                        System.out.println("Result of multiplication: " + in.readLine() + "\n");
                        break;
                    case 3:
                        out.write("SUB " + num1 + " " + num2 + "\n");
                        out.flush();
                        System.out.println("Result of subtraction: " + in.readLine() + "\n");
                        break;
                    case 4:
                        out.write("QUIT\n");
                        out.flush();
                        socket.close();
                        System.out.println("Result of subtraction: " + in.readLine() + "\n");
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a number between 1 and 3.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid integers.");
        } catch (IOException e) {
            System.out.println("Client: exception while using client socket: " + e);
        }
    }
}