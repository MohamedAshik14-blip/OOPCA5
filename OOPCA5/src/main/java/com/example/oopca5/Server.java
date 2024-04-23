import DAO.StudentDAO;
import DAO.StudentDAOImpl;
import DTO.StudentDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

public class Server {
    public static final int PORT = 8081; // Specify the port number
    private static final StudentDAO studentDAO;

    static {
        try {
            studentDAO = new StudentDAOImpl();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize StudentDAO", e);
        }
    }

     public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                // Handle client request in a separate thread
                new Thread(() -> {
                    try {
                        handleClientRequest(clientSocket);
                    } catch (IOException | ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClientRequest(Socket clientSocket) throws IOException, ClassNotFoundException, SQLException {
        try (
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            while (true) {
                int requestType = in.readInt();

                if (requestType == -1) {
                    // Display all Entities
                    List<StudentDTO> allStudents = studentDAO.getAllStudents();
                    String jsonStudents = JsonConverter.listEntitiesToJson(allStudents);
                    out.writeObject(jsonStudents);
                    out.flush();
                }
                    
                    else if (requestType == -2) {
                    String jsonEntity = (String) in.readObject();
                    StudentDTO newStudent = JsonConverter.jsonToEntity(jsonEntity, StudentDTO.class);
                    StudentDTO addedStudent = studentDAO.insertStudent(newStudent);
                    String jsonResult = JsonConverter.entityToJson(addedStudent);
                    out.writeObject(jsonResult);
                    out.flush();
                }

                else if (requestType == -3) {

                    int entityId = in.readInt();
                    boolean isDeleted = studentDAO.deleteStudentById(entityId);
                    out.writeBoolean(isDeleted);
                    out.flush();
                }  
                     else if (requestType == -4) {

                    sendImagesList(out);
                }
                else if (requestType == -5) {

                    String fileName = (String) in.readObject();
                    sendImageFile(fileName, out);
                }
                     else if (requestType == -6) {

                    System.out.println("Client is quitting...");
                    return;
                }

                        
                else {
                    // Display Entity by ID
                    int studentId = requestType;
                    StudentDTO student = studentDAO.getStudentById(studentId);
                    String jsonStudent = (student != null) ? JsonConverter.entityToJson(student) : "{}";
                    out.writeObject(jsonStudent);
                    out.flush();
                }
            }
        }
        
    }
     private static void sendImagesList(ObjectOutputStream out) throws IOException {
        File imagesDirectory = new File("/Users/mohamedashiks/IdeaProjects/OOPCA5/src/main/java/images");
        if (!imagesDirectory.exists() || !imagesDirectory.isDirectory()) {
            out.writeObject("[]");
            out.flush();
        } else {
            String[] imageFiles = imagesDirectory.list();
            out.writeObject(JsonConverter.arrayToJson(imageFiles));
            out.flush();
        }
    }
    private static void sendImageFile(String fileName, ObjectOutputStream out) throws IOException {
        File file = new File("/Users/mohamedashiks/IdeaProjects/OOPCA5/src/main/java/images/" + fileName);
        if (!file.exists() || file.isDirectory()) {
            out.writeBoolean(false);
            out.flush();
            return;
        }

        byte[] fileData = Files.readAllBytes(file.toPath());
        out.writeBoolean(true);
        out.writeInt(fileData.length);
        out.write(fileData);
        out.flush();
    }
}
