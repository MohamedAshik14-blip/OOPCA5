import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

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
        assertNotNull(students);
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

        assertNotNull(actualStudent);
        assertEquals(expectedStudent.getId(), actualStudent.getId());
        assertEquals(expectedStudent.getName(), actualStudent.getName());
        assertEquals(expectedStudent.getStudentNumber(), actualStudent.getStudentNumber());
        assertEquals(expectedStudent.getGpa(), actualStudent.getGpa());
    }
