package DTO;
public class StudentDTO {
    private int id;
    private int studentNumber;
    private float gpa;
    private String name;


    public StudentDTO() {
    }

    public StudentDTO(int id, int studentNumber, float gpa, String name) {
        this.id = id;
        this.studentNumber = studentNumber;
        this.gpa = gpa;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", studentNumber=" + studentNumber +
                ", gpa=" + gpa +
                ", name='" + name + '\'' +
                '}';
    }
}
