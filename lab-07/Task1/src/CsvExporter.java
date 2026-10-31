import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CsvExporter {
    public void export(Student student, double gpa, BigDecimal scholarship){
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String line = String.format("%s;%s;%.2f;%s;%s%n", student.getId(), student.getName(), gpa, scholarship, date);
        try (FileOutputStream fos = new FileOutputStream("scholarship_registry.csv", true)){
            fos.write(line.getBytes(StandardCharsets.UTF_8));
        }catch (IOException e){
            System.out.println("Помилка експорту у CSV" + e.getMessage());
        }
    }
}
