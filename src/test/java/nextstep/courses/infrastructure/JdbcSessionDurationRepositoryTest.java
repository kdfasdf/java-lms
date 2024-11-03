package nextstep.courses.infrastructure;

import java.time.LocalDateTime;
import nextstep.courses.domain.SessionDuration;
import nextstep.courses.domain.SessionDurationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class JdbcSessionDurationRepositoryTest {
    private final SessionDuration sessionDuration = new SessionDuration(0L, LocalDateTime.now(),LocalDateTime.now().plusMinutes(1));

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionDurationRepository sessionDurationRepository;

    @BeforeEach
    void setUp() {
        sessionDurationRepository = new JdbcSessionDurationRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("session_duration 저장 테스트")
    void saveTest() {
        Assertions.assertThat(sessionDurationRepository.save(sessionDuration)).isEqualTo(1);
    }

    @Test
    @DisplayName("session_duration 테이블 id 조회 테스트")
    void findByIdTest() {
        sessionDurationRepository.save(sessionDuration);
        Assertions.assertThat(sessionDurationRepository.findById(sessionDuration.getSessionId())).isEqualTo(sessionDuration);
    }
}
