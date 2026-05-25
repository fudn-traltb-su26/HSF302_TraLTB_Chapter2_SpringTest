package hsf302.lab02.aspects;

// TODO 5.1: Import org.aspectj.lang.JoinPoint
// TODO 5.1: Import org.aspectj.lang.ProceedingJoinPoint
// TODO 5.1: Import org.aspectj.lang.annotation.Aspect
// TODO 5.1: Import org.aspectj.lang.annotation.Before
// TODO 5.1: Import org.aspectj.lang.annotation.AfterReturning
// TODO 5.1: Import org.aspectj.lang.annotation.Around
// TODO 5.1: Import org.springframework.stereotype.Component

/**
 * Task 5 — AOP Logging Aspect
 *
 * Hoàn thiện class này để logging tất cả method trong package services.
 *
 * Pointcut expression cần dùng:
 *   "execution(* hsf302.lab02.services.*.*(..))"
 */
// TODO 5.1: Thêm annotation @Aspect  (đánh dấu đây là một Aspect)
// TODO 5.1: Thêm annotation @Component (để Spring quản lý bean này)
public class LoggingAspect {

    // TODO 5.2: Tạo method logBefore(JoinPoint jp) với annotation @Before
    //
    //   @Before("execution(* hsf302.lab02.services.*.*(..))")
    //   public void logBefore(JoinPoint jp) {
    //       // In ra: "[BEFORE] " + jp.getSignature().getName()
    //   }
    //
    // Ví dụ output: "[BEFORE] addStudent"

    // TODO 5.3: Tạo method logAfterReturning(JoinPoint jp, Object result)
    //           với annotation @AfterReturning
    //
    //   @AfterReturning(
    //       pointcut = "execution(* hsf302.lab02.services.*.*(..))",
    //       returning = "result"
    //   )
    //   public void logAfterReturning(JoinPoint jp, Object result) {
    //       // In ra: "[AFTER] " + jp.getSignature().getName() + " returned: " + result
    //   }
    //
    // Ví dụ output: "[AFTER] getStudentInfo returned: Student{name='Alice', age=20}"

    // TODO 5.4: Tạo method logAround(ProceedingJoinPoint pjp) với annotation @Around
    //           - Lấy thời gian bắt đầu (System.currentTimeMillis())
    //           - Gọi:  Object result = pjp.proceed();
    //           - Tính thời gian đã qua
    //           - In ra: "[ELAPSED] " + pjp.getSignature().getName() + " took " + elapsed + "ms"
    //           - Trả về result
    //           - Method phải throws Throwable
    //
    //   @Around("execution(* hsf302.lab02.services.*.*(..))")
    //   public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
    //       // ...
    //   }
}
