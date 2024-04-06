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
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT_NUMBER);
            System.out.println("Server started. Waiting for clients...");
        } catch (IOException e) {
            System.err.println("Error starting the server: " + e.getMessage());
        }
    }
}
