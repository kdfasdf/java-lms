package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionInfo;
import nextstep.courses.domain.SessionInfoRepository;
import nextstep.courses.domain.SessionType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class JdbcSessionInfoRepositoryTest {
    private final SessionInfo sessionInfo = new SessionInfo(0L, SessionType.PAID, 1000L, 100);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionInfoRepository sessionInfoRepository;

    @BeforeEach
    void setUp() {
        sessionInfoRepository = new JdbcSessionInfoRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("session_info 저장 테스트")
    void saveTest() {
        Assertions.assertThat(sessionInfoRepository.save(sessionInfo)).isEqualTo(1);
    }

    @Test
    @DisplayName("session_info 테이블 id 조회 테스트")
    void findByIdTest() {
        sessionInfoRepository.save(sessionInfo);
        Assertions.assertThat(sessionInfoRepository.findById(sessionInfo.getSessionId())).isEqualTo(sessionInfo);
    }
}
