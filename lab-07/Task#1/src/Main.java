import java.util.List;

public class Main{
    public static void main(){
        Student student = new Student("KP-101", "Агеєнко Ігор");
        List<CourseGrade> grades = List.of(
                new CourseGrade("Програмування", 95, 5),
                new CourseGrade("Бази даних", 88, 4),
                new CourseGrade("Алгоритми", 92, 6)
        );

        new StudentGradeManager().processStudentAcademicRecord(student, grades, "curator@chnu.edu.ua");
    }
}