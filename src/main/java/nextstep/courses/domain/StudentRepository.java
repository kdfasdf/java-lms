package nextstep.courses.domain;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    int save(SessionRegisterInfo sessionRegisterInfo, Student student);

    Optional<List<Student>> findById(Long Id);
}
