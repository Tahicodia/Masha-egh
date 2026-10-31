import java.math.BigDecimal;
import java.util.List;

public class ScholarshipService {
    public BigDecimal calculate(List<CourseGrade> grades, double gpa){
        boolean hasFailed = grades.stream().anyMatch(g -> g.getScore() < 60);
        if (hasFailed) return BigDecimal.ZERO;
        if (gpa >= 90.0) return new BigDecimal("2910.00");
        if (gpa >= 75.0) return new BigDecimal("2000.00");
        return BigDecimal.ZERO;
    }
}
