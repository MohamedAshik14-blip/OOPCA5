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
                 System.out.println("3. Delete student by ID");
                System.out.println("4. Insert a new student");
                System.out.println("5. update Student By Id");
                 System.out.println("6. Filter by Gpa  ");
                System.out.println("7. Exit");
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

                        System.out.print("Enter student ID to delete: ");
                        int idToDelete = scanner.nextInt();
                        scanner.nextLine();
                        boolean isDeleted = studentDAO.deleteStudentById(idToDelete);
                        if (isDeleted) {
                            System.out.println("Student with ID " + idToDelete + " deleted successfully.");
                        } else {
                            System.out.println("Failed to delete student with ID " + idToDelete);
                        }
                        break;
                    case 4:

                        System.out.print("Enter student number: ");
                        int studentNumber = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter GPA: ");
                        float gpa = scanner.nextFloat();
                        scanner.nextLine();
                        System.out.print("Enter student name: ");
                        String name = scanner.nextLine();
                        StudentDTO newStudent = new StudentDTO(0, studentNumber, gpa, name);
                        StudentDTO insertedStudent = studentDAO.insertStudent(newStudent);
                        if (insertedStudent != null) {
                            System.out.println("New student inserted with ID " + insertedStudent.getId());
                        } else {
                            System.out.println("Failed to insert new student.");
                        }
                        break;
                        case 5:
                        System.out.print("Enter student ID to update: ");
                        int idToUpdate = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter new student number: ");
                        int newStudentNumber = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter new GPA: ");
                        float newGpa = scanner.nextFloat();
                        scanner.nextLine();
                        System.out.print("Enter new student name: ");
                        String newName = scanner.nextLine();
                        StudentDTO updatedStudent = new StudentDTO(idToUpdate, newStudentNumber, newGpa, newName);
                        StudentDTO updatedStudentResult = studentDAO.updateStudentById(idToUpdate, updatedStudent);
                        if (updatedStudentResult != null) {
                            System.out.println("Student with ID " + idToUpdate + " updated successfully.");
                        } else {
                            System.out.println("Failed to update student with ID " + idToUpdate);
                        }
                        break;

                           case 6:

                        System.out.print("Enter GPA threshold: ");
                        float gpaThreshold = scanner.nextFloat();
                        scanner.nextLine(); 
                        List<StudentDTO> filteredStudents = studentDAO.findStudentsUsingFilter(gpaThreshold);
                        if (!filteredStudents.isEmpty()) {
                            System.out.println("Students with GPA >= " + gpaThreshold + ":");
                            for (StudentDTO student : filteredStudents) {
                                System.out.println(student);
                            }
                        } else {
                            System.out.println("No students found matching the filter.");
                        }
                        break;


                    case 7:

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
