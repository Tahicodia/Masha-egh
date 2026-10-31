import java.util.List;

public class GradeValidator {
    public void validate(Student student, List<CourseGrade> grades){
        if (student.getId() == null || student.getId().isBlank() || student.getName() == null || student.getName().isBlank()){
            throw new IllegalArgumentException("Ідентифікатор та ім'я студента не можуть бути порожніми");
        }
        if (grades == null || grades.isEmpty()){
            throw new IllegalStateException("Перелік дисциплін порожній");
        }
        for (CourseGrade g : grades){
            if (g.getScore() < 0 || g.getScore() >  100){
                throw new IllegalArgumentException("Неможлива оцінка " + g.getScore() + " з предмета " + g.getCourseName());
            }
        }
    }
}