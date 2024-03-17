import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {

            StudentDAOImpl studentDAO = new StudentDAOImpl();
            Scanner scanner = new Scanner(System.in);


            while (true) {
                System.out.println("Menu:");
                System.out.println("1. Show all students");
                System.out.println("2. Find student by ID");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:

                        List<StudentDTO> students = studentDAO.getAllStudents();
                        for (StudentDTO student : students) {
                            System.out.println(student);
                        }
                        break;
                    case 2:

                        System.out.print("Enter student ID: ");
                        int idToFind = scanner.nextInt();
                        scanner.nextLine();
                        StudentDTO foundStudent = studentDAO.getStudentById(idToFind);
                        if (foundStudent != null) {
                            System.out.println(foundStudent);
                        } else {
                            System.out.println("Student not found.");
                        }
                        break;


                    case 3:

                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
