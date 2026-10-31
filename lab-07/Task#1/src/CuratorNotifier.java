public class CuratorNotifier {
    public void notify(String email, Student student, String transcript){
        if (email == null || email.isBlank()) return;
        System.out.println("Надсилання пошти куратору " + email + " через mail.univ.local");
        System.out.println("Тема: Успішність студента " + student.getName());
        System.out.println("Тіло: " + transcript);
    }
}
