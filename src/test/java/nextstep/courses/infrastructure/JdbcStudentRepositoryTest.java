package nextstep.courses.infrastructure;

import nextstep.courses.domain.Payments;
import nextstep.courses.domain.SessionRegisterInfo;
import nextstep.courses.domain.SessionRegisteringStatus;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.domain.Student;
import nextstep.courses.domain.StudentRepository;
import nextstep.courses.domain.Students;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class JdbcStudentRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository = new JdbcStudentRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("강의 학생 등록 테스트")
    void 강의_학생_등록_테스트() {
        SessionRegisterInfo sessionRegisterInfo = new SessionRegisterInfo(
                0L, SessionStatus.REGISTER, Students.from(), Payments.from(), SessionRegisteringStatus.OPEN
        );

        studentRepository.save(sessionRegisterInfo,Student.selectedStudent("0", 0L));
        studentRepository.save(sessionRegisterInfo, Student.selectedStudent("1", 0L));

        Assertions.assertThat(studentRepository.findById(0L).size()).isEqualTo(2);
    }

    @Test
    @DisplayName("선발되지 않은 학생 삭제 테스트")
    void 선발되지_않은_학생_삭제() {
        JdbcStudentRepository studentRepository = new JdbcStudentRepository(jdbcTemplate);

        SessionRegisterInfo sessionRegisterInfo = new SessionRegisterInfo(
                0L, SessionStatus.REGISTER, Students.from(), Payments.from(), SessionRegisteringStatus.OPEN
        );

        studentRepository.save(sessionRegisterInfo,Student.selectedStudent("0", 0L));
        studentRepository.save(sessionRegisterInfo, Student.unSelectedStudent("1", 0L));

        studentRepository.deleteUnselectedStudent(0L);

        Assertions.assertThat(studentRepository.findById(0L).size()).isEqualTo(1);
    }

}
