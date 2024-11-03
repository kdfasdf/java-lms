package nextstep.courses.domain;

import java.util.Objects;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class SessionRegisterInfo {
    private final SessionStatus sessionStatus;
    private final Long sessionId;
    private final Students students;
    private final Payments payments;

    public SessionRegisterInfo(Long sessionId,SessionStatus sessionStatus, Students students, Payments payments) {
        this.sessionId = sessionId;
        this.sessionStatus = sessionStatus;
        this.students = students;
        this.payments = payments;
    }

    public void checkSessionIsRegistering() {
        if (sessionStatus != SessionStatus.REGISTER) {
            throw new IllegalArgumentException("이 강의는 지금 모집중인 상태가 아닙니다");
        }
    }

    public void addStudent(NsUser nsUser) {
        checkUserAlreadyRegisterSession(nsUser);
        students.addStudent(nsUser.getUserId());
    }

    private void checkUserAlreadyRegisterSession(NsUser nsUser) {
        if (students.getContainResult(nsUser)) {
            throw new IllegalArgumentException("이미 수업에 등록한 학생입니다");
        }
    }

    public void addPaymentHistory(long userId, long payment, Long sessionId) {
        Payment pay = new Payment("", userId, payment, sessionId);
        checkPaymentIsDuplicate(pay);
        payments.addPaymentHistory(pay);
    }

    private void checkPaymentIsDuplicate(Payment pay) {
        if (payments.checkPaymentIsStored(pay)) {
            throw new IllegalArgumentException("이미 저장된 결제정보 입니다");
        }
    }

    public int getNumberOfStudents() {
        return students.getNumberOfStudents();
    }

    public int getNumberOfPayments() {
        return payments.getNumberOfPayments();
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public Long getSessionId() {
        return sessionId;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SessionRegisterInfo)) {
            return false;
        }

        SessionRegisterInfo that = (SessionRegisterInfo) o;
        return sessionStatus == that.sessionStatus && Objects.equals(sessionId, that.sessionId)
                && Objects.equals(payments, that.payments);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(sessionStatus);
        result = 31 * result + Objects.hashCode(sessionId);
        result = 31 * result + Objects.hashCode(payments);
        return result;
    }
}
