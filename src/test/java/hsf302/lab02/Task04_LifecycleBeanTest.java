package hsf302.lab02;

import hsf302.lab02.components.LifecycleBean;
import hsf302.lab02.configs.AppConfig;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Task 4 — LifecycleBean (25 điểm)
 * KHÔNG SỬA FILE NÀY.
 */
@DisplayName("Task 4 — LifecycleBean Lifecycle [25 pts]")
class Task04_LifecycleBeanTest {

    private Optional<Method> findMethodWithAnnotation(Class<?> clazz,
                                                       Class<? extends java.lang.annotation.Annotation> ann) {
        return Arrays.stream(clazz.getDeclaredMethods())
                     .filter(m -> m.isAnnotationPresent(ann))
                     .findFirst();
    }

    @Test
    @DisplayName("[3pts] LifecycleBean phải có field 'initialized' kiểu boolean")
    void t1_hasInitializedField() {
        assertDoesNotThrow(
            () -> LifecycleBean.class.getDeclaredField("initialized"),
            "LifecycleBean thiếu field 'initialized'"
        );
        try {
            var f = LifecycleBean.class.getDeclaredField("initialized");
            assertEquals(boolean.class, f.getType(),
                "Field 'initialized' phải kiểu boolean (primitive)"
            );
        } catch (NoSuchFieldException ignored) {}
    }

    @Test
    @DisplayName("[10pts] LifecycleBean phải có method với @PostConstruct")
    void t2_hasPostConstructMethod() {
        Optional<Method> method = findMethodWithAnnotation(LifecycleBean.class, PostConstruct.class);
        assertTrue(method.isPresent(),
            "LifecycleBean chưa có method nào được đánh dấu @PostConstruct"
        );
    }

    @Test
    @DisplayName("[10pts] LifecycleBean phải có method với @PreDestroy")
    void t3_hasPreDestroyMethod() {
        Optional<Method> method = findMethodWithAnnotation(LifecycleBean.class, PreDestroy.class);
        assertTrue(method.isPresent(),
            "LifecycleBean chưa có method nào được đánh dấu @PreDestroy"
        );
    }

    @Test
    @DisplayName("[bonus] isInitialized() phải trả về true sau khi context khởi tạo")
    void t4_isInitializedAfterContextStart() {
        try (AnnotationConfigApplicationContext ctx =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {
            LifecycleBean bean = ctx.getBean(LifecycleBean.class);
            assertTrue(bean.isInitialized(),
                "isInitialized() phải trả về true sau khi @PostConstruct đã chạy"
            );
        }
    }

    @Test
    @DisplayName("[bonus] @PostConstruct phải in '[LIFECYCLE] LifecycleBean initialized'")
    void t5_postConstructPrintsMessage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(baos));
        try (AnnotationConfigApplicationContext ctx =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {
            // context starts here, @PostConstruct fires
        } finally {
            System.setOut(original);
        }
        String output = baos.toString();
        assertTrue(
            output.contains("[LIFECYCLE] LifecycleBean initialized"),
            "@PostConstruct phải in '[LIFECYCLE] LifecycleBean initialized' nhưng đã in:\n" + output
        );
    }

    @Test
    @DisplayName("[bonus] @PreDestroy phải in '[LIFECYCLE] LifecycleBean destroyed'")
    void t6_preDestroyPrintsMessage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(baos));
        try (AnnotationConfigApplicationContext ctx =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {
            // context will close here, @PreDestroy fires
        } finally {
            System.setOut(original);
        }
        String output = baos.toString();
        assertTrue(
            output.contains("[LIFECYCLE] LifecycleBean destroyed"),
            "@PreDestroy phải in '[LIFECYCLE] LifecycleBean destroyed' nhưng đã in:\n" + output
        );
    }
}
