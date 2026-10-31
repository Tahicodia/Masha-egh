import java.math.BigDecimal;
import java.util.List;

public class StudentGradeManager {
    private final GradeValidator validator = new GradeValidator();
    private final GpaCalculator gpaCalculator = new GpaCalculator();
    private final ScholarshipService scholarshipService = new ScholarshipService();
    private final TranscriptFormatter formatter = new TranscriptFormatter();
    private final CsvExporter exporter = new CsvExporter();
    private final CuratorNotifier notifier = new CuratorNotifier();

    public void processStudentAcademicRecord(Student student, List<CourseGrade> grades, String curatorEmail){
        validator.validate(student, grades);
        double gpa = gpaCalculator.calculate(grades);
        BigDecimal scholarship = scholarshipService.calculate(grades, gpa);
        String transcript = formatter.format(student, grades, gpa, scholarship);
        exporter.export(student, gpa, scholarship);
        notifier.notify(curatorEmail, student, transcript);
    }
}
