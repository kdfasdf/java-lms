package nextstep.courses.domain;

public class SessionInfo {
    private final SessionType sessionType;
    private final Long price;
    private final Integer maxStudents;

    public SessionInfo(SessionType sessionType, Long price, Integer maxStudents) {
        this.sessionType = sessionType;
        this.price = price;
        this.maxStudents = maxStudents;
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
}
