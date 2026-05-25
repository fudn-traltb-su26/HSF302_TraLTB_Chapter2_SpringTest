package hsf302.lab02;

import hsf302.lab02.configs.AppConfig;
import hsf302.lab02.pojo.Student;
import hsf302.lab02.services.StudentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Main class — dùng để chạy thử thủ công.
 * File này ĐÃ CHO SẴN, không cần sửa.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Starting Spring ApplicationContext ===");

        try (AnnotationConfigApplicationContext ctx =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {

            StudentService service = ctx.getBean(StudentService.class);

            Student alice = new Student("Alice", 20);
            Student bob   = new Student("Bob", 22);

            service.addStudent(alice);
            service.addStudent(bob);

            System.out.println(service.getStudentInfo(alice));
            System.out.println(service.getStudentInfo(bob));
        }

        System.out.println("=== ApplicationContext closed ===");
    }
}
