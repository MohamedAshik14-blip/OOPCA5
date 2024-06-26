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
                System.out.println("3. Add an Entity");
                System.out.println("4. delete an Entity by ID");
                 System.out.println("5. Get Images List");
                  System.out.println("6. Exit");
        
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
                        addEntity(out, in, scanner);
                        break;
                        case 4:
                        deleteEntityById(out, in, scanner);
                        break;
                        case 5:
                        getImagesList(out, in);
                        break;
                     case 6:
                        exitClient(out);
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

    private static void addEntity(ObjectOutputStream out, ObjectInputStream in, Scanner scanner) throws IOException, ClassNotFoundException {
        out.writeInt(-2);
        out.flush();


        System.out.println("Enter the entity data in JSON format:");
        String jsonEntity = scanner.next();

        out.writeObject(jsonEntity);
        out.flush();


        String response = (String) in.readObject();
        System.out.println("Server response:");
        System.out.println(response);
    }
    private static void deleteEntityById(ObjectOutputStream out, ObjectInputStream in, Scanner scanner) throws IOException {
        out.writeInt(-3);
        out.flush();

        System.out.print("Enter the ID of the entity to delete: ");
        int entityId = scanner.nextInt();
        out.writeInt(entityId);
        out.flush();

        boolean isDeleted = in.readBoolean();
        if (isDeleted) {
            System.out.println("Entity with ID " + entityId + " has been successfully deleted.");
        } else {
            System.out.println("Failed to delete entity with ID " + entityId + ". Entity not found.");
        }
    }
    private static void getImagesList(ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException {
        out.writeInt(-4);
        out.flush();


        String jsonData = (String) in.readObject();
        System.out.println("Received image file names from server:");
        System.out.println(jsonData);

        System.out.print("Enter the name of the image file to download: ");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();


        out.writeInt(-5);
        out.flush();
        out.writeObject(fileName);
        out.flush();


        boolean fileExists = in.readBoolean();
        if (fileExists) {
            int fileSize = in.readInt();
            byte[] fileData = new byte[fileSize];
            in.readFully(fileData);
            saveImageFile(fileName, fileData);
            System.out.println("Image file '" + fileName + "' downloaded successfully.");
        } else {
            System.out.println("Image file '" + fileName + "' not found on the server.");
        }
    }
    
     private static void saveImageFile(String fileName, byte[] fileData) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName))) {
            bos.write(fileData);
        }
    }
    private static void exitClient(ObjectOutputStream out) throws IOException {

        out.writeInt(-6);
        out.flush();
        System.out.println("Exiting...");
    }
}
