package nextstep.courses.domain;

import java.time.LocalDateTime;

public class SessionDuration {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public SessionDuration(LocalDateTime startDate, LocalDateTime endDate) {
        checkStartDateAfterEndDate(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void checkStartDateAfterEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작날짜가 종료일보다 늦을 수 없습니다");
        }
    }


}
