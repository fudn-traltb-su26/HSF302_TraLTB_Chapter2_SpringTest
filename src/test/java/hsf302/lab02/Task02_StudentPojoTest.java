package hsf302.lab02;

import hsf302.lab02.pojo.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Task 2 — Student POJO (20 điểm)
 * KHÔNG SỬA FILE NÀY.
 */
@DisplayName("Task 2 — Student POJO [20 pts]")
class Task02_StudentPojoTest {

    private Student student;

    @BeforeEach
    void setUp() throws Exception {
        Constructor<Student> ctor = Student.class.getDeclaredConstructor(String.class, int.class);
        student = ctor.newInstance("Alice", 20);
    }

    @Test
    @DisplayName("[2pts] Student phải có field 'name' kiểu String")
    void t1_hasNameField() throws Exception {
        Field f = assertDoesNotThrow(
            () -> Student.class.getDeclaredField("name"),
            "Student thiếu field 'name'"
        );
        assertEquals(String.class, f.getType(), "Field 'name' phải kiểu String");
    }

    @Test
    @DisplayName("[2pts] Student phải có field 'age' kiểu int")
    void t2_hasAgeField() {
        Field f = assertDoesNotThrow(
            () -> Student.class.getDeclaredField("age"),
            "Student thiếu field 'age'"
        );
        assertEquals(int.class, f.getType(), "Field 'age' phải kiểu int");
    }

    @Test
    @DisplayName("[4pts] Student phải có constructor(String name, int age)")
    void t3_hasConstructor() {
        assertDoesNotThrow(
            () -> Student.class.getDeclaredConstructor(String.class, int.class),
            "Student thiếu constructor(String name, int age)"
        );
    }

    @Test
    @DisplayName("[3pts] getName() phải trả về tên đã truyền vào constructor")
    void t4_getName() throws Exception {
        assertEquals("Alice", student.getName(),
            "getName() phải trả về 'Alice' (giá trị đã truyền vào constructor)"
        );
    }

    @Test
    @DisplayName("[3pts] getAge() phải trả về tuổi đã truyền vào constructor")
    void t5_getAge() throws Exception {
        assertEquals(20, student.getAge(),
            "getAge() phải trả về 20 (giá trị đã truyền vào constructor)"
        );
    }

    @Test
    @DisplayName("[3pts] setName() phải cập nhật giá trị name")
    void t6_setName() throws Exception {
        student.setName("Bob");
        assertEquals("Bob", student.getName(),
            "getName() phải trả về 'Bob' sau khi gọi setName('Bob')"
        );
    }

    @Test
    @DisplayName("[3pts] setAge() phải cập nhật giá trị age")
    void t7_setAge() throws Exception {
        student.setAge(25);
        assertEquals(25, student.getAge(),
            "getAge() phải trả về 25 sau khi gọi setAge(25)"
        );
    }
}
