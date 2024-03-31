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

    @Test
    @DisplayName("Test createStudent method")
    void testcreateStudent() {
        StudentDTO newStudent = new StudentDTO(0, 123456, 3.5f, "Jane Smith");
        StudentDTO insertedStudent = studentDAO.insertStudent(newStudent);
        assertNotNull(insertedStudent);
        assertNotEquals(0, insertedStudent.getId());
    } 
    @Test
    @DisplayName("Test updateStudentById method")
    void testUpdateStudentById() {
        int studentIdToUpdate = 25;
        StudentDTO updatedStudent = new StudentDTO(studentIdToUpdate, 1234, 3.8f, "hello");
        StudentDTO result = studentDAO.updateStudentById(studentIdToUpdate, updatedStudent);
        assertNotNull(result);
        assertEquals(updatedStudent.getStudentNumber(), result.getStudentNumber());
        assertEquals(updatedStudent.getGpa(), result.getGpa());
        assertEquals(updatedStudent.getName(), result.getName());
    }

}
@Test
@DisplayName("Test deleteStudentById method")
void testDeleteStudentById() {
    int studentIdToDelete = 10;
    boolean isDeleted = studentDAO.deleteStudentById(studentIdToDelete);
    assertTrue(isDeleted);
    // Check if the student is actually deleted
    assertNull(studentDAO.getStudentById(studentIdToDelete));
}

@Test
@DisplayName("Test findStudentsUsingFilter method")
void testFindStudentsUsingFilter() {
    float gpaThreshold = 3.5f;
    List<StudentDTO> filteredStudents = studentDAO.findStudentsUsingFilter(gpaThreshold);
    assertNotNull(filteredStudents);
    // Check if all filtered students have GPA >= gpaThreshold
    for (StudentDTO student : filteredStudents) {
        assertTrue(student.getGpa() >= gpaThreshold);
    }
    // Check if the filtered list is not empty
    assertTrue(filteredStudents.size() > 0);
}

@Test
@DisplayName("Test JsonConverter.listEntitiesToJson method")
void testListEntitiesToJson() {
    List<StudentDTO> students = new ArrayList<>();
    students.add(new StudentDTO(1, 101, 3.6f, "John Doe"));
    students.add(new StudentDTO(2, 102, 3.7f, "Jane Smith"));
    students.add(new StudentDTO(3, 103, 3.8f, "Alice Johnson"));
    
    String studentsJson = JsonConverter.listEntitiesToJson(students);
    assertNotNull(studentsJson);
    // You may add more assertions here based on the expected JSON structure
}
