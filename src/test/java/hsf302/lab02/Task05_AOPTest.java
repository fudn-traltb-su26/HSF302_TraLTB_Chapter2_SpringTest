package hsf302.lab02;

import hsf302.lab02.aspects.LoggingAspect;
import hsf302.lab02.configs.AppConfig;
import hsf302.lab02.pojo.Student;
import hsf302.lab02.services.StudentService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Task 5 — LoggingAspect AOP (25 điểm)
 * KHÔNG SỬA FILE NÀY.
 */
@DisplayName("Task 5 — LoggingAspect AOP [25 pts]")
class Task05_AOPTest {

    static AnnotationConfigApplicationContext ctx;
    static StudentService service;

    @BeforeAll
    static void setUpContext() {
        try {
            ctx = new AnnotationConfigApplicationContext(AppConfig.class);
            service = ctx.getBean(StudentService.class);
        } catch (Exception ignored) {}
    }

    @AfterAll
    static void tearDown() {
        if (ctx != null) ctx.close();
    }

    private Optional<Method> findMethodWithAnnotation(
            Class<?> clazz,
            Class<? extends java.lang.annotation.Annotation> ann) {
        return Arrays.stream(clazz.getDeclaredMethods())
                     .filter(m -> m.isAnnotationPresent(ann))
                     .findFirst();
    }

    // ──────────────── Annotation checks ────────────────

    @Test
    @DisplayName("[2pts] LoggingAspect phải có @Aspect")
    void t1_hasAspectAnnotation() {
        assertTrue(
            LoggingAspect.class.isAnnotationPresent(Aspect.class),
            "LoggingAspect thiếu @Aspect annotation"
        );
    }

    @Test
    @DisplayName("[3pts] LoggingAspect phải có @Component (để Spring quản lý)")
    void t2_hasComponentAnnotation() {
        assertTrue(
            LoggingAspect.class.isAnnotationPresent(Component.class),
            "LoggingAspect thiếu @Component annotation"
        );
    }

    @Test
    @DisplayName("[7pts] LoggingAspect phải có ít nhất 1 method với @Before")
    void t3_hasBeforeAdvice() {
        Optional<Method> beforeMethod = findMethodWithAnnotation(LoggingAspect.class, Before.class);
        assertTrue(beforeMethod.isPresent(),
            "LoggingAspect chưa có method nào được đánh dấu @Before"
        );
    }

    @Test
    @DisplayName("[7pts] LoggingAspect phải có ít nhất 1 method với @AfterReturning")
    void t4_hasAfterReturningAdvice() {
        Optional<Method> method = findMethodWithAnnotation(LoggingAspect.class, AfterReturning.class);
        assertTrue(method.isPresent(),
            "LoggingAspect chưa có method nào được đánh dấu @AfterReturning"
        );
    }

    @Test
    @DisplayName("[6pts] LoggingAspect phải có ít nhất 1 method với @Around")
    void t5_hasAroundAdvice() {
        Optional<Method> method = findMethodWithAnnotation(LoggingAspect.class, Around.class);
        assertTrue(method.isPresent(),
            "LoggingAspect chưa có method nào được đánh dấu @Around"
        );
    }

    // ──────────────── Integration / runtime checks ────────────────

    @Test
    @DisplayName("[bonus] @Before phải in '[BEFORE]' khi gọi addStudent()")
    void t6_beforeAdviceFiresOnAddStudent() {
        assertNotNull(service, "StudentService bean không tồn tại — kiểm tra Task 3 trước");

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
            output.contains("[BEFORE]"),
            "@Before advice phải in '[BEFORE] ...' khi gọi addStudent(). Output hiện tại:\n" + output
        );
    }

    @Test
    @DisplayName("[bonus] @AfterReturning phải in '[AFTER]' khi gọi getStudentInfo()")
    void t7_afterReturningAdviceFiresOnGetStudentInfo() {
        assertNotNull(service, "StudentService bean không tồn tại — kiểm tra Task 3 trước");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(baos));
        try {
            service.getStudentInfo(new Student("Bob", 22));
        } finally {
            System.setOut(original);
        }

        String output = baos.toString();
        assertTrue(
            output.contains("[AFTER]"),
            "@AfterReturning advice phải in '[AFTER] ...' khi gọi getStudentInfo(). Output:\n" + output
        );
    }

    @Test
    @DisplayName("[bonus] @Around phải in '[ELAPSED]' với thông tin thời gian")
    void t8_aroundAdviceMeasuresTime() {
        assertNotNull(service, "StudentService bean không tồn tại — kiểm tra Task 3 trước");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(baos));
        try {
            service.addStudent(new Student("Carol", 21));
        } finally {
            System.setOut(original);
        }

        String output = baos.toString();
        assertTrue(
            output.contains("[ELAPSED]"),
            "@Around advice phải in '[ELAPSED] ...' với thời gian thực thi. Output:\n" + output
        );
        assertTrue(
            output.contains("ms"),
            "@Around advice phải ghi thời gian theo đơn vị 'ms'. Output:\n" + output
        );
    }
}
