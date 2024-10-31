package nextstep.payments.domain;

import java.time.LocalDateTime;

public class Payment {
    private String id;

    // 결제한 강의 아이디
    private Long sessionId;

    // 결제한 사용자 아이디
    private Long nsUserId;

    // 결제 금액
    private Long amount;

    private LocalDateTime createdAt;

    public Payment() {
    }

    public Payment(String id, Long sessionId, Long nsUserId, Long amount) {
        this.id = id;
        this.sessionId = sessionId;
        this.nsUserId = nsUserId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }

        Payment payment = (Payment) o;
        return sessionId.equals(payment.sessionId) && nsUserId.equals(payment.nsUserId) && amount.equals(
                payment.amount);
    }

    @Override
    public int hashCode() {
        int result = sessionId.hashCode();
        result = 31 * result + nsUserId.hashCode();
        result = 31 * result + amount.hashCode();
        return result;
    }
}
