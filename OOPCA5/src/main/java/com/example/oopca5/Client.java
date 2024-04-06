import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 8081;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                // Display menu and get user choice
                System.out.println("Menu:");
                System.out.println("1. Display Entity by ID");
                System.out.println("2. Display all Entities");
               System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

              
                switch (choice) {
                    case 1:
                        displayEntityById(out, in, scanner);
                        break;
                    case 2:
                        displayAllEntities(out, in);
                        break;
                    case 3:

                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    private static void displayEntityById(ObjectOutputStream out, ObjectInputStream in, Scanner scanner) throws IOException, ClassNotFoundException {
        System.out.print("Enter the entity ID: ");
        int entityId = scanner.nextInt();

        out.writeInt(entityId);
        out.flush();

        String jsonData = (String) in.readObject();
        System.out.println("Received JSON data from server:");
        System.out.println(jsonData);
    }

    private static void displayAllEntities(ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException {
        out.writeInt(-1);
        out.flush();

        String jsonData = (String) in.readObject();
        System.out.println("Received JSON data from server:");
        System.out.println(jsonData);
    }

  
}