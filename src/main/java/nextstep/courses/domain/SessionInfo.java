package nextstep.courses.domain;

import java.util.Objects;

public class SessionInfo {
    private final SessionType sessionType;
    private final Long price;
    private final Integer maxStudents;
    private final Long sessionId;

    public SessionInfo(Long sessionId,SessionType sessionType, Long price, Integer maxStudents) {
        this.sessionType = sessionType;
        this.price = price;
        this.maxStudents = maxStudents;
        this.sessionId = sessionId;
    }

    public void checkPaymentEqualsPrice(long payment) {
        if (sessionType.isPaid() && price != payment) {
            throw new IllegalArgumentException("수강료와 가격이 일치하지 않습니다");
        }
    }

    public void checkCurrentNumberOfStudentsIsMax(int numberOfStudents) {
        if (numberOfStudents == maxStudents) {
            throw new IllegalArgumentException("수강 정원이 모두 찼습니다.");
        }
    }

    public Long getPrice() {
        return price;
    }

    public Integer getMaxStudents() {
        return maxStudents;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public Long getSessionId() {
        return sessionId;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SessionInfo)) {
            return false;
        }

        SessionInfo that = (SessionInfo) o;
        return sessionType == that.sessionType && Objects.equals(price, that.price) && Objects.equals(
                maxStudents, that.maxStudents) && Objects.equals(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(sessionType);
        result = 31 * result + Objects.hashCode(price);
        result = 31 * result + Objects.hashCode(maxStudents);
        result = 31 * result + Objects.hashCode(sessionId);
        return result;
    }
}
