package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

public class SessionRegisterInfo {
    private final SessionStatus sessionStatus;
    private final Students students = new Students();
    private final Payments payments = new Payments();
    public SessionRegisterInfo(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
    }


    public void checkSessionIsRegistering() {
        if (sessionStatus != SessionStatus.REGISTER) {
            throw new IllegalArgumentException("이 강의는 지금 모집중인 상태가 아닙니다");
        }
    }

    public void addStudent(NsUser nsUser) {
        students.addStudent(nsUser);
    }

    public void addPaymentHistory(NsUser nsUser, long payment, Long sessionId) {
        payments.addPaymentHistory(nsUser, payment, sessionId);
    }

    public int getNumberOfStudents() {
        return students.getNumberOfStudents();
    }

    public int getNumberOfPayments() {
        return payments.getNumberOfPayments();
    }

}
