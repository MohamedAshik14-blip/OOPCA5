import java.util.List;

public interface StudentDAO {
    List<StudentDTO> getAllStudents();
    StudentDTO getStudentById(int id);
    boolean deleteStudentById(int id);
    StudentDTO insertStudent(StudentDTO student);

}
