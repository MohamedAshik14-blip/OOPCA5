import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    private Connection connection;

    public StudentDAOImpl() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/OOPCA5", "root", "123");
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<StudentDTO> students = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Student")) {
            while (rs.next()) {
                StudentDTO student = new StudentDTO();
                student.setId(rs.getInt("id"));
                student.setStudentNumber(rs.getInt("studentNumber"));
                student.setGpa(rs.getFloat("gpa"));
                student.setName(rs.getString("name"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public StudentDTO getStudentById(int id) {
        StudentDTO student = null;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Student WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                student = new StudentDTO();
                student.setId(rs.getInt("id"));
                student.setStudentNumber(rs.getInt("studentNumber"));
                student.setGpa(rs.getFloat("gpa"));
                student.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

@Override
    public boolean deleteStudentById(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM Student WHERE id = ?")) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
 @Override
    public StudentDTO insertStudent(StudentDTO student) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO Student (studentNumber, gpa, name) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, student.getStudentNumber());
            stmt.setFloat(2, student.getGpa());
            stmt.setString(3, student.getName());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    student.setId(rs.getInt(1));
                }
                return student;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
 @Override
    public StudentDTO updateStudentById(int id, StudentDTO updatedStudent) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE Student SET studentNumber = ?, gpa = ?, name = ? WHERE id = ?")) {
            stmt.setInt(1, updatedStudent.getStudentNumber());
            stmt.setFloat(2, updatedStudent.getGpa());
            stmt.setString(3, updatedStudent.getName());
            stmt.setInt(4, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 1) {
                updatedStudent.setId(id);
                return updatedStudent;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
