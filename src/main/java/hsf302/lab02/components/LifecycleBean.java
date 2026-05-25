package hsf302.lab02.components;

import org.springframework.stereotype.Component;

// TODO 4.1: Import jakarta.annotation.PostConstruct
// TODO 4.1: Import jakarta.annotation.PreDestroy

/**
 * Task 4 — Bean Lifecycle
 *
 * Hoàn thiện class LifecycleBean với @PostConstruct và @PreDestroy callbacks.
 *
 * Lưu ý: annotation @Component đã được thêm sẵn — KHÔNG xóa nó.
 */
@Component
public class LifecycleBean {

    // TODO 4.1: Khai báo field:  private boolean initialized = false

    // TODO 4.2: Tạo method init() với annotation @PostConstruct
    //           - Gán:  initialized = true
    //           - In ra:  "[LIFECYCLE] LifecycleBean initialized"
    //           Spring sẽ tự gọi method này sau khi bean được tạo xong
    //
    // public void init() { ... }

    // TODO 4.3: Tạo method cleanup() với annotation @PreDestroy
    //           - Gán:  initialized = false
    //           - In ra:  "[LIFECYCLE] LifecycleBean destroyed"
    //           Spring sẽ tự gọi method này trước khi container shutdown
    //
    // public void cleanup() { ... }

    // TODO 4.4: Implement method isInitialized()
    //           Trả về giá trị của field initialized
    //           KHÔNG sửa chữ ký method, chỉ sửa phần thân
    public boolean isInitialized() {
        // TODO 4.4: Thay dòng dưới bằng:  return initialized;
        return false;
    }
}
