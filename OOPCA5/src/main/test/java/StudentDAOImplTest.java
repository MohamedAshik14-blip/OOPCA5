import DAO.StudentDAOImpl;
import DTO.StudentDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentDAOImplTest {

    private static StudentDAOImpl studentDAO;

    @BeforeAll
    static void setUp() {
        try {
            studentDAO = new StudentDAOImpl();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test getAllStudents method")
    void testGetAllStudents() {
        List<StudentDTO> students = studentDAO.getAllStudents();
        assertTrue(students.size() > 0);
    }

    @Test
    @DisplayName("Test getStudentById method")
    void testGetStudentById() {
        StudentDTO expectedStudent = new StudentDTO();
        expectedStudent.setId(3);
        expectedStudent.setName("Michael Davis");
        expectedStudent.setStudentNumber(1003);
        expectedStudent.setGpa(3.9f);

        StudentDTO actualStudent = studentDAO.getStudentById(3);

        assertEquals(expectedStudent.getId(), actualStudent.getId());
        assertEquals(expectedStudent.getName(), actualStudent.getName());
        assertEquals(expectedStudent.getStudentNumber(), actualStudent.getStudentNumber());
        assertEquals(expectedStudent.getGpa(), actualStudent.getGpa());
    }

    @Test
    @DisplayName("Test insertStudent method")
    void testInsertStudent() {
        StudentDTO newStudent = new StudentDTO(0, 123456, 3.5f, "Jane Smith");
        StudentDTO insertedStudent = studentDAO.insertStudent(newStudent);
        assertNotEquals(0, insertedStudent.getId());
    }

    @Test
    @DisplayName("Test updateStudentById method")
    void testUpdateStudentById() {
        int studentIdToUpdate = 15;
        StudentDTO updatedStudent = new StudentDTO(studentIdToUpdate, 123456, 3.8f, "Smith");
        StudentDTO result = studentDAO.updateStudentById(studentIdToUpdate, updatedStudent);
        assertEquals(updatedStudent.getStudentNumber(), result.getStudentNumber());
        assertEquals(updatedStudent.getGpa(), result.getGpa());
        assertEquals(updatedStudent.getName(), result.getName());
    }

    @Test
    @DisplayName("Test deleteStudentById method")
    void testDeleteStudentById() {
        StudentDTO student = new StudentDTO();
        student.setStudentNumber(1234567);
        student.setGpa(3.8f);
        student.setName("jhon");
        student = studentDAO.insertStudent(student);

        boolean isDeleted = studentDAO.deleteStudentById(student.getId());
        assertTrue(isDeleted);
        assertNull(studentDAO.getStudentById(student.getId()));
    }

    @Test
    @DisplayName("Test findStudentsUsingFilter method")
    void testFindStudentsUsingFilter() {
        float gpaThreshold = 3.5f;
        List<StudentDTO> filteredStudents = studentDAO.findStudentsUsingFilter(gpaThreshold);
        assertTrue(filteredStudents.size() > 0);
    }

    // Feature 7: Convert List of Entities to a JSON String
    @Test
    void testListEntitiesToJson() {
        List<StudentDTO> students = studentDAO.getAllStudents();
        String studentsJson = JsonConverter.listEntitiesToJson(students);
        assertFalse(studentsJson.isEmpty());
    }

    // Feature 8: Convert a single Entity by Key as a JSON String
    @Test
    void testEntityToJson() {
        int idToFind = 1;
        StudentDTO student = studentDAO.getStudentById(idToFind);
        String studentJson = JsonConverter.entityToJson(student);
        assertFalse(studentJson.isEmpty());
    }
}
