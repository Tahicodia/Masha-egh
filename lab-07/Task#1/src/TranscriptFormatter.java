import java.math.BigDecimal;
import java.util.List;

public class TranscriptFormatter {
    public String format(Student student, List<CourseGrade> grades, double gpa, BigDecimal scholarship){
        StringBuilder sb = new StringBuilder();
        sb.append("==========================================\n");
        sb.append("АКАДЕМІЧНА ДОВІДКА: ").append(student.getName())
                .append(" (ID: ").append(student.getId()).append(")\n");
        sb.append("==========================================\n");
        for (CourseGrade g : grades) {
            sb.append(String.format("%-30s | %d Кредитів | Оцінка: %d%n",
                    g.getCourseName(), g.getEctsCredits(), g.getScore()));
        }
        sb.append("------------------------------------------\n");
        sb.append(String.format("Середній бал (GPA): %.2f%n", gpa));
        sb.append("Призначена стипендія: ").append(scholarship).append(" грн\n");
        return sb.toString();
    }
}
