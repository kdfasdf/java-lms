package nextstep.courses.infrastructure;

import java.time.LocalDateTime;
import nextstep.courses.domain.Image;
import nextstep.courses.domain.ImageSize;
import nextstep.courses.domain.ImageType;
import nextstep.courses.domain.ImageWidthHeight;
import nextstep.courses.domain.Payments;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionDuration;
import nextstep.courses.domain.SessionInfo;
import nextstep.courses.domain.SessionRegisterInfo;
import nextstep.courses.domain.SessionRegisteringStatus;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.domain.SessionType;
import nextstep.courses.domain.Students;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class JdbcSessionRepositoryTest {
    private static final Image image = new Image(1L , 0L,
            new ImageSize(0L, 100), ImageType.JPEG, new ImageWidthHeight(0L,600, 400));


    private final SessionDuration sessionDuration = new SessionDuration(0L, LocalDateTime.now(),LocalDateTime.now().plusMinutes(1));

    private final SessionInfo sessionInfo = new SessionInfo(0L, SessionType.PAID, 1000L, 100);

    private final SessionRegisterInfo sessionRegisterInfo = new SessionRegisterInfo(
            0L, SessionStatus.REGISTER, Students.from(), Payments.from(), SessionRegisteringStatus.OPEN
    );

    private final Session session = Session.createPaidSession(0L, image, SessionType.PAID, SessionStatus.REGISTER, 1000L, 100,sessionDuration, SessionRegisteringStatus.OPEN);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository ;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("강의 테이블 저장 테스트")
    void saveTest() {
        Assertions.assertThat(sessionRepository.save(session)).isEqualTo(1);
    }

    @Test
    @DisplayName("강의 테이블 id로 조회하는 테스트")
    void findByIdTest() {
        sessionRepository.save(session);
        Assertions.assertThat(sessionRepository.findById(session.getSessionId())).isEqualTo(session);
    }

    @Test
    @DisplayName("id로 강의 기간 조회하는 테스트")
    void findByIdSessionDurationTest() {
        JdbcSessionRepository jdbcSessionRepository = new JdbcSessionRepository(jdbcTemplate);
        jdbcSessionRepository.save(session);
        Assertions.assertThat(jdbcSessionRepository.findByIdSessionDuration(session.getSessionId())).isEqualTo(sessionDuration);
    }

    @Test
    @DisplayName("id로 강의 정보 조회하는 테스트")
    void findByIdSessionInfoTest() {
        JdbcSessionRepository jdbcSessionRepository = new JdbcSessionRepository(jdbcTemplate);
        jdbcSessionRepository.save(session);
        Assertions.assertThat(jdbcSessionRepository.findByIdSessionInfo(session.getSessionId())).isEqualTo(sessionInfo);
    }

    @Test
    @DisplayName("id로 강의 수강 정보 조회하는 테스트")
    void findByIdSessionRegisterInfoTest() {
        JdbcSessionRepository jdbcSessionRepository = new JdbcSessionRepository(jdbcTemplate);
        jdbcSessionRepository.save(session);
        Assertions.assertThat(jdbcSessionRepository.findByIdSessionRegisterInfo(session.getSessionId())).isEqualTo(sessionRegisterInfo);
    }

}
