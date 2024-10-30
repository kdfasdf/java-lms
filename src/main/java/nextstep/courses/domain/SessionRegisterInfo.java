package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class SessionRegisterInfo {
    private final SessionStatus sessionStatus;
    private final List<NsUser> students = new ArrayList<>();
    private final List<Payment> payments = new ArrayList<>();
    public SessionRegisterInfo(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
    }


    public void checkSessionIsRegistering() {
        if (sessionStatus != SessionStatus.REGISTER) {
            throw new IllegalArgumentException("이 강의는 지금 모집중인 상태가 아닙니다");
        }
    }

    public void addStudent(NsUser nsUser) {
        students.add(nsUser);
    }

    public void addPaymentHistory(NsUser nsUser, long payment, Long sessionId) {
        payments.add(new Payment("", sessionId, nsUser.getId(), payment));
    }

    public int getNumberOfStudents() {
        return students.size();
    }

    public int getNumberOfPayments() {
        return payments.size();
    }

}
