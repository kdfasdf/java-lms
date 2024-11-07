package nextstep.courses.domain;

import java.util.List;

public interface StudentRepository {
    int save(SessionRegisterInfo sessionRegisterInfo, Student student);

    List<Student> findById(Long Id);
}
