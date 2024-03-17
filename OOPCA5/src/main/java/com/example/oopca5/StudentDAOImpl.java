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




}
