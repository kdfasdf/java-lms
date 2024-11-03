package nextstep.courses.infrastructure;


import nextstep.courses.domain.Payments;
import nextstep.courses.domain.SessionRegisterInfo;
import nextstep.courses.domain.SessionRegisterInfoRepository;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.domain.Students;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class JdbcSessionRegisterInfoRepositoryTest {
    private final SessionRegisterInfo sessionRegisterInfo = new SessionRegisterInfo(
            0L, SessionStatus.REGISTER, Students.from(), Payments.from()
    );

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRegisterInfoRepository sessionRegisterInfoRepository ;

    @BeforeEach
    void setUp() {
        sessionRegisterInfoRepository = new JdbcSessionRegisterInfoRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("수강 등록정보 테이블 생성 테스트")
    void saveTest() {
        Assertions.assertThat(sessionRegisterInfoRepository.save(sessionRegisterInfo)).isEqualTo(1);
    }

    @Test
    @DisplayName("수강 등록정보 테이블 id로 조회하는 테스트")
    void findByIdTest() {
        sessionRegisterInfoRepository.save(sessionRegisterInfo);
        Assertions.assertThat(sessionRegisterInfoRepository.findById(sessionRegisterInfo.getSessionId())).isEqualTo(sessionRegisterInfo);
    }

}
