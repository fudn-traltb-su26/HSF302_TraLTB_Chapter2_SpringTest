# HSF302 — Lab 02: Spring Framework Core

> **Chapter:** 02 — Introduction to Spring Framework  
> **Tổng điểm:** 100 điểm  
> **Cách nộp bài:** Push code lên GitHub — điểm được chấm **tự động** sau mỗi lần push  

![GitHub Classroom](https://img.shields.io/badge/GitHub%20Classroom-Autograded-blue?logo=github)

---

## Xem điểm sau khi push

Sau khi push code lên GitHub, điểm sẽ xuất hiện **trong vòng 2–3 phút** ở 3 nơi:

**1. Tab "Actions" của repo:**
```
GitHub repo → Actions → GitHub Classroom Autograding → [commit mới nhất]
→ Mở job "Autograding" → Xem step "Generate grade summary"
```

**2. PR "Feedback" (GitHub Classroom tạo sẵn):**
```
GitHub repo → Pull requests → "Feedback"
→ Bot sẽ tự post comment bảng điểm sau mỗi lần push
```

**3. Checks trên commit:**
```
GitHub repo → Commits → [commit mới nhất]
→ Dấu ✅ / ❌ bên cạnh commit hash → "Details"
→ Mỗi test method hiện rõ passed/failed
```

---

## Mục tiêu

Sau khi hoàn thành lab này, sinh viên có thể:

- Cấu hình Spring IoC Container bằng Java-based config (`@Configuration`)
- Hiểu và áp dụng Dependency Injection (Constructor Injection)
- Quản lý Bean Lifecycle với `@PostConstruct` / `@PreDestroy`
- Viết Aspect cơ bản với `@Before`, `@AfterReturning`, `@Around`

---

## Cấu trúc project

```
spring-lab02/
├── pom.xml
├── README.md                          ← File này
├── grader.py                          ← Script chấm điểm tự động
└── src/
    ├── main/
    │   ├── java/hsf302/lab02/
    │   │   ├── configs/
    │   │   │   └── AppConfig.java         ← TODO Task 1
    │   │   ├── pojo/
    │   │   │   └── Student.java           ← TODO Task 2
    │   │   ├── services/
    │   │   │   ├── StudentService.java    ← (đã cho sẵn)
    │   │   │   └── StudentServiceImpl.java← TODO Task 3
    │   │   ├── components/
    │   │   │   └── LifecycleBean.java     ← TODO Task 4
    │   │   └── aspects/
    │   │       └── LoggingAspect.java     ← TODO Task 5
    │   └── resources/
    │       └── log4j.properties
    └── test/
        └── java/hsf302/lab02/
            ├── Task01_AppConfigTest.java  (10 điểm)
            ├── Task02_StudentPojoTest.java(20 điểm)
            ├── Task03_StudentServiceTest.java (20 điểm)
            ├── Task04_LifecycleBeanTest.java  (25 điểm)
            └── Task05_AOPTest.java            (25 điểm)
```

---

## Hướng dẫn thực hiện

### Quy tắc chung

- **KHÔNG** được sửa bất kỳ file nào trong thư mục `src/test/`
- **KHÔNG** được sửa `StudentService.java` (interface đã cho sẵn)
- **KHÔNG** được sửa `grader.py`
- Chỉ sửa những file có comment `// TODO`
- Giữ nguyên **tên class**, **tên package**, **tên method** đã khai báo

---

## TODO List

---

### ✅ Task 1 — AppConfig (10 điểm)

**File:** `src/main/java/hsf302/lab02/configs/AppConfig.java`

| # | TODO | Mô tả | Điểm |
|---|------|--------|------|
| 1.1 | `@Configuration` | Đánh dấu class là Spring configuration class | 3 |
| 1.2 | `@ComponentScan` | Scan component trong package `"hsf302.lab02"` | 4 |
| 1.3 | `@EnableAspectJAutoProxy` | Bật AspectJ auto proxy để AOP hoạt động | 3 |

**Gợi ý:**
```java
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
```

---

### ✅ Task 2 — Student POJO (20 điểm)

**File:** `src/main/java/hsf302/lab02/pojo/Student.java`

| # | TODO | Mô tả | Điểm |
|---|------|--------|------|
| 2.1 | Field `name` | `private String name` | 2 |
| 2.2 | Field `age` | `private int age` | 2 |
| 2.3 | Constructor | `public Student(String name, int age)` | 4 |
| 2.4 | `getName()` | Getter trả về `name` | 3 |
| 2.5 | `getAge()` | Getter trả về `age` | 3 |
| 2.6 | `setName()` | Setter nhận `String name` | 3 |
| 2.7 | `setAge()` | Setter nhận `int age` | 3 |

---

### ✅ Task 3 — StudentServiceImpl (20 điểm)

**File:** `src/main/java/hsf302/lab02/services/StudentServiceImpl.java`

| # | TODO | Mô tả | Điểm |
|---|------|--------|------|
| 3.1 | `@Service` | Đánh dấu class là Spring service bean | 5 |
| 3.2 | `addStudent()` | In ra `"Added student: " + student.getName()` | 7 |
| 3.3 | `getStudentInfo()` | Trả về `student.toString()` (không được trả về `null`) | 8 |

**Lưu ý:** `StudentServiceImpl` phải **implement** `StudentService` interface.

---

### ✅ Task 4 — LifecycleBean (25 điểm)

**File:** `src/main/java/hsf302/lab02/components/LifecycleBean.java`

| # | TODO | Mô tả | Điểm |
|---|------|--------|------|
| 4.1 | Field `initialized` | `private boolean initialized = false` | 3 |
| 4.2 | `@PostConstruct` trên `init()` | Set `initialized = true`, in `"[LIFECYCLE] LifecycleBean initialized"` | 10 |
| 4.3 | `@PreDestroy` trên `cleanup()` | Set `initialized = false`, in `"[LIFECYCLE] LifecycleBean destroyed"` | 10 |
| 4.4 | `isInitialized()` | Trả về giá trị field `initialized` | 2 |

**Gợi ý:**
```java
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
```

---

### ✅ Task 5 — LoggingAspect (25 điểm)

**File:** `src/main/java/hsf302/lab02/aspects/LoggingAspect.java`

| # | TODO | Mô tả | Điểm |
|---|------|--------|------|
| 5.1 | `@Aspect` + `@Component` | Đánh dấu class là Aspect và Spring bean | 5 |
| 5.2 | `@Before` advice | Intercept tất cả method trong `hsf302.lab02.services.*.*(..)`, in `"[BEFORE] <tên method>"` | 7 |
| 5.3 | `@AfterReturning` advice | In `"[AFTER] <tên method> returned: <result>"` | 7 |
| 5.4 | `@Around` advice | Đo thời gian thực thi, in `"[ELAPSED] <tên method> took <X>ms"`, gọi `pjp.proceed()` | 6 |

**Pointcut expression dùng cho cả 3 advice:**
```
execution(* hsf302.lab02.services.*.*(..))
```

---

## Cách chạy kiểm tra

### Chạy tất cả test (local)

```bash
# -fae = fail-at-end: chạy hết tất cả test dù có task chưa làm, rồi mới báo lỗi
mvn test -fae
```

### Chạy test của một task cụ thể

```bash
mvn test -Dtest=Task01_AppConfigTest
mvn test -Dtest=Task02_StudentPojoTest
mvn test -Dtest=Task03_StudentServiceTest
mvn test -Dtest=Task04_LifecycleBeanTest
mvn test -Dtest=Task05_AOPTest
```

### Chạy grader để xem bảng điểm (local)

```bash
# Bước 1: Chạy tất cả test để sinh report
mvn test -fae

# Bước 2: Xem bảng điểm ở terminal
python grader.py
```

### Nộp bài

```bash
git add .
git commit -m "implement task X: mô tả ngắn"
git push
# → Mở GitHub → Actions → xem kết quả tự động sau ~2 phút
```

---

## Ví dụ output khi hoàn thành đúng

```
=== HSF302 Lab 02 — Grade Report ===

Task 1 — AppConfig            [ 10/10 ] ████████████████████ PASSED
Task 2 — Student POJO         [ 20/20 ] ████████████████████ PASSED
Task 3 — StudentServiceImpl   [ 20/20 ] ████████████████████ PASSED
Task 4 — LifecycleBean        [ 25/25 ] ████████████████████ PASSED
Task 5 — LoggingAspect        [ 25/25 ] ████████████████████ PASSED

------------------------------------
TOTAL                         [100/100]
```

---

## Tài liệu tham khảo

- Lecture slide: `Chapter_02_Spring_Framework.md`
- [Spring Framework Docs](https://docs.spring.io/spring-framework/reference/)
- [Maven Repository](https://mvnrepository.com/)
