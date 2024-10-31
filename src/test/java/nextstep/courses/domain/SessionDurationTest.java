package nextstep.courses.domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SessionDurationTest {
    @Test
    @DisplayName("시작일이 종료일 보다 늦을 시 예외를 발생시키는지 확인한다")
    void 시작날짜_종료날짜보다_늦으면_예외_발생() {
        Assertions.assertThatThrownBy(
                () -> new SessionDuration(
                        LocalDateTime.of(2024, 11, 1, 0, 0),
                        LocalDateTime.of(2024, 10, 28, 0, 0)
                )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작날짜가 종료일보다 늦을 수 없습니다");
    }
}
