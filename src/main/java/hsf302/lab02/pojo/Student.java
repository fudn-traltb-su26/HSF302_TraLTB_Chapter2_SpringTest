package hsf302.lab02.pojo;

/**
 * Task 2 — Student POJO
 *
 * Hoàn thiện class Student với đầy đủ fields, constructor và getter/setter.
 */
public class Student {

    // TODO 2.1: Khai báo field:  private String name
    // TODO 2.2: Khai báo field:  private int age

    // TODO 2.3: Tạo constructor nhận 2 tham số: (String name, int age)
    //           và gán vào các field tương ứng

    // TODO 2.4: Tạo method:  public String getName()
    //           Trả về giá trị field name

    // TODO 2.5: Tạo method:  public int getAge()
    //           Trả về giá trị field age

    // TODO 2.6: Tạo method:  public void setName(String name)
    //           Gán tham số vào field name

    // TODO 2.7: Tạo method:  public void setAge(int age)
    //           Gán tham số vào field age

    /**
     * KHÔNG sửa method toString() này.
     */
    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + "}";
    }
}
