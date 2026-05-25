package hsf302.lab02.services;

import hsf302.lab02.pojo.Student;

/**
 * StudentService interface — ĐÃ CHO SẴN, KHÔNG ĐƯỢC SỬA FILE NÀY.
 */
public interface StudentService {

    /**
     * Thêm một sinh viên (in thông tin ra console).
     *
     * @param student sinh viên cần thêm
     */
    void addStudent(Student student);

    /**
     * Lấy thông tin sinh viên dưới dạng String.
     *
     * @param student sinh viên cần lấy thông tin
     * @return chuỗi mô tả sinh viên (không được null)
     */
    String getStudentInfo(Student student);
}
