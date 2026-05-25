package hsf302.lab02;

import hsf302.lab02.configs.AppConfig;
import hsf302.lab02.pojo.Student;
import hsf302.lab02.services.StudentService;
import hsf302.lab02.services.StudentServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Task 3 — StudentServiceImpl (20 điểm)
 * KHÔNG SỬA FILE NÀY.
 */
@DisplayName("Task 3 — StudentServiceImpl [20 pts]")
class Task03_StudentServiceTest {

    static AnnotationConfigApplicationContext ctx;
    static StudentService service;

    @BeforeAll
    static void setUpContext() {
        try {
            ctx = new AnnotationConfigApplicationContext(AppConfig.class);
            service = ctx.getBean(StudentService.class);
        } catch (Exception e) {
            // Context có thể fail nếu Task 1 chưa hoàn thành — tests sẽ fail gracefully
        }
    }

    @AfterAll
    static void tearDown() {
        if (ctx != null) ctx.close();
    }

    @Test
    @DisplayName("[5pts] StudentServiceImpl phải có annotation @Service")
    void t1_hasServiceAnnotation() {
        assertTrue(
            StudentServiceImpl.class.isAnnotationPresent(Service.class),
            "StudentServiceImpl thiếu @Service annotation"
        );
    }

    @Test
    @DisplayName("[5pts] Spring context phải chứa bean StudentService")
    void t2_beanExistsInContext() {
        assertNotNull(ctx, "ApplicationContext chưa được khởi tạo (kiểm tra Task 1 trước)");
        assertTrue(
            ctx.containsBean("studentServiceImpl"),
            "Spring context không tìm thấy bean 'studentServiceImpl'. Kiểm tra @Service"
        );
    }

    @Test
    @DisplayName("[5pts] addStudent() phải in ra 'Added student: <name>'")
    void t3_addStudentPrintsCorrectly() {
        assertNotNull(service, "StudentService bean không tồn tại trong context");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(baos));
        try {
            service.addStudent(new Student("Alice", 20));
        } finally {
            System.setOut(original);
        }

        String output = baos.toString();
        assertTrue(
            output.contains("Added student: Alice"),
            "addStudent() phải in ra 'Added student: Alice' nhưng đã in: " + output.trim()
        );
    }

    @Test
    @DisplayName("[5pts] getStudentInfo() phải trả về student.toString(), không được null")
    void t4_getStudentInfoReturnsNonNull() {
        assertNotNull(service, "StudentService bean không tồn tại trong context");
        Student bob = new Student("Bob", 22);
        String info = service.getStudentInfo(bob);
        assertNotNull(info, "getStudentInfo() không được trả về null");
        assertFalse(info.isEmpty(), "getStudentInfo() không được trả về chuỗi rỗng");
        assertTrue(
            info.contains("Bob"),
            "getStudentInfo() phải chứa tên sinh viên 'Bob', nhưng trả về: " + info
        );
    }
}
