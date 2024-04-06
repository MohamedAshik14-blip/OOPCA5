import DAO.StudentDAO;
import DAO.StudentDAOImpl;
import DTO.StudentDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

public class Server {
    public static final int SERVER_PORT_NUMBER = 8081; // Specify the port number
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
                }else {
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
