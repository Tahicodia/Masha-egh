import java.util.List;

public class GpaCalculator {
    public double calculate(List<CourseGrade> grades){
        double weighted = 0;
        int credits  = 0;
        for(CourseGrade g : grades){
            weighted += g.getScore() * g.getEctsCredits();
            credits += g.getEctsCredits();
        }
        return weighted/credits;
    }
}
