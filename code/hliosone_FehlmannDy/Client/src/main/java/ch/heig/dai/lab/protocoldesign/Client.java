package ch.heig.dai.lab.protocoldesign;
import com.sun.org.omg.CORBA.OperationMode;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

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

                int choice = Integer.parseInt(reader.readLine());
                int num1 = 0, num2 = 0;

                // Display menu
                Operation.printMenuOptions();

                if(Operation.getOperation(choice) != Operation.QUIT) {
                    // Get two numbers
                    System.out.print("Enter the first integer: ");
                    num1 = Integer.parseInt(reader.readLine());

                    System.out.print("Enter the second integer: ");
                    num2 = Integer.parseInt(reader.readLine());
                }

                // Perform operation based on user choice
                switch (Operation.getOperation(choice)) {
                    case ADD:
                        out.write("ADD " + num1 + " " + num2 + "\n");
                        out.flush();
                        System.out.println("Result of addition: " + in.readLine() + "\n");
                        break;
                    case MUL:
                        out.write("MUL " + num1 + " " + num2 + "\n");
                        out.flush();
                        System.out.println("Result of multiplication: " + in.readLine() + "\n");
                        break;
                    case SUB:
                        out.write("SUB " + num1 + " " + num2 + "\n");
                        out.flush();
                        System.out.println("Result of subtraction: " + in.readLine() + "\n");
                        break;
                    case DIV:
                        out.write("DIV " + num1 + " " + num2 + "\n");
                        out.flush();
                        System.out.println("Result of division: " + in.readLine() + "\n");
                        break;
                    case QUIT:
                        out.write("QUIT\n");
                        out.flush();
                        socket.close();
                        System.out.println("Quit program");
                        break;
                    case null:
                    default:
                        System.out.println("Invalid choice. Please select a number between 1 and 5.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid integers.");
        } catch (IOException e) {
            System.out.println("Client: exception while using client socket: " + e);
        }
    }
}