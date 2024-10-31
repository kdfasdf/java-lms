package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

public class Session {
    private final Long sessionId;
    private final Image image;
    private final SessionDuration sessionDuration;
    private final SessionInfo sessionInfo;
    private final SessionRegisterInfo sessionRegisterInfo;

    public static Session createPaidSession(Long id, Image image, SessionType sessionType, SessionStatus sessionStatus,
                                            Long price, Integer maxStudentCount, SessionDuration sessionDuration) {
        return new Session(id, image, sessionType, sessionStatus, price, maxStudentCount, sessionDuration);
    }

    public static Session createFreeSession(Long id, Image image, SessionType sessionType, SessionStatus sessionStatus,
                                            SessionDuration sessionDuration) {
        return new Session(id, image, sessionType, sessionStatus, sessionDuration);
    }

    private Session(Long sessionId, Image image, SessionType sessionType, SessionStatus sessionStatus, Long price,
                    Integer maxStudents, SessionDuration sessionDuration) {
        this.sessionId = sessionId;
        this.image = image;
        sessionType.validate(price, maxStudents);
        this.sessionInfo = new SessionInfo(sessionType, price, maxStudents);
        this.sessionRegisterInfo = new SessionRegisterInfo(sessionStatus, Students.from(), Payments.from());
        this.sessionDuration = sessionDuration;
    }

    private Session(Long sessionId, Image image, SessionType sessionType, SessionStatus sessionStatus,
                    SessionDuration sessionDuration) {
        this(sessionId, image, sessionType, sessionStatus, 0L, Integer.MAX_VALUE, sessionDuration);
    }

    public long getPrice() {
        return sessionInfo.getPrice();
    }

    public void register(NsUser nsUser, long payment) {
        registerStudent(nsUser, payment);
        addPaymentHistory(nsUser.getId(), payment, sessionId);
    }


    public void registerStudent(NsUser nsUser, long payment) {
        sessionRegisterInfo.checkSessionIsRegistering();
        sessionInfo.checkPaymentEqualsPrice(payment);
        sessionInfo.checkCurrentNumberOfStudentsIsMax(getNumberOfStudents());
        sessionRegisterInfo.addStudent(nsUser);
    }

    private void addPaymentHistory(Long userId, long payment, Long sessionId) {
        sessionRegisterInfo.addPaymentHistory(userId, payment, sessionId);
    }

    public int getNumberOfStudents() {
        return sessionRegisterInfo.getNumberOfStudents();
    }

    public int getNumberOfPayments() {
        return sessionRegisterInfo.getNumberOfPayments();
    }

}
