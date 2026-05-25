package hsf302.lab02;

import hsf302.lab02.configs.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Task 1 — AppConfig (10 điểm)
 * KHÔNG SỬA FILE NÀY.
 */
@DisplayName("Task 1 — AppConfig Annotations [10 pts]")
class Task01_AppConfigTest {

    @Test
    @DisplayName("[3pts] AppConfig phải có @Configuration")
    void t1_hasConfigurationAnnotation() {
        assertTrue(
            AppConfig.class.isAnnotationPresent(Configuration.class),
            "AppConfig thiếu @Configuration annotation"
        );
    }

    @Test
    @DisplayName("[4pts] AppConfig phải có @ComponentScan với basePackages chứa 'hsf302.lab02'")
    void t2_hasComponentScanAnnotation() {
        assertTrue(
            AppConfig.class.isAnnotationPresent(ComponentScan.class),
            "AppConfig thiếu @ComponentScan annotation"
        );
        ComponentScan cs = AppConfig.class.getAnnotation(ComponentScan.class);
        String[] packages = cs.basePackages();
        if (packages.length == 0) {
            packages = cs.value();
        }
        boolean found = false;
        for (String pkg : packages) {
            if (pkg.contains("hsf302.lab02")) {
                found = true;
                break;
            }
        }
        assertTrue(found,
            "@ComponentScan phải khai báo basePackages = \"hsf302.lab02\" (hoặc package cha chứa nó)"
        );
    }

    @Test
    @DisplayName("[3pts] AppConfig phải có @EnableAspectJAutoProxy")
    void t3_hasEnableAspectJAutoProxy() {
        assertTrue(
            AppConfig.class.isAnnotationPresent(EnableAspectJAutoProxy.class),
            "AppConfig thiếu @EnableAspectJAutoProxy annotation"
        );
    }

    @Test
    @DisplayName("[bonus] ApplicationContext phải khởi tạo thành công")
    void t4_contextLoads() {
        assertDoesNotThrow(() -> {
            try (AnnotationConfigApplicationContext ctx =
                         new AnnotationConfigApplicationContext(AppConfig.class)) {
                assertNotNull(ctx, "ApplicationContext không được null");
            }
        }, "ApplicationContext không thể khởi tạo — kiểm tra lại @Configuration và @ComponentScan");
    }
}
