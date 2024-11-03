package nextstep.courses.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class SessionDuration {

    private final Long sessionId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public SessionDuration(Long sessionId, LocalDateTime startDate, LocalDateTime endDate) {
        checkStartDateAfterEndDate(startDate, endDate);
        this.sessionId = sessionId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void checkStartDateAfterEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작날짜가 종료일보다 늦을 수 없습니다");
        }
    }

    public Long getSessionId() {
        return sessionId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SessionDuration)) {
            return false;
        }

        SessionDuration that = (SessionDuration) o;
        return Objects.equals(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sessionId);
    }
}
