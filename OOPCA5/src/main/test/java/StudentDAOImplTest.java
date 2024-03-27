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
