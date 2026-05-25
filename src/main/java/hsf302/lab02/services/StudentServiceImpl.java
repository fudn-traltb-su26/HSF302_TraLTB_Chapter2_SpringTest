package hsf302.lab02.services;

import hsf302.lab02.pojo.Student;

// TODO 3.1: Import org.springframework.stereotype.Service
// TODO 3.1: Thêm annotation @Service vào class này
//           (để Spring IoC Container nhận biết và quản lý bean này)
public class StudentServiceImpl implements StudentService {

    // TODO 3.2: Implement method addStudent(Student student)
    //           In ra:  "Added student: " + student.getName()
    //           Ví dụ output: "Added student: Alice"
    @Override
    public void addStudent(Student student) {
        // TODO 3.2: Viết code ở đây
    }

    // TODO 3.3: Implement method getStudentInfo(Student student)
    //           Trả về:  student.toString()
    //           KHÔNG được trả về null
    @Override
    public String getStudentInfo(Student student) {
        // TODO 3.3: Thay dòng dưới bằng implementation đúng
        return null;
    }
}
