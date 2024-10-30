package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

public class Session {
    private final Long sessionId;
    private final Image image;
    private final SessionDuration sessionDuration = new SessionDuration();
    private final SessionInfo sessionInfo;
    private final SessionRegisterInfo sessionRegisterInfo;


    public static Session createSession(Long id, Image image, SessionType sessionType, SessionStatus sessionStatus,
                                        Long price, Integer maxStudentCount) {
        if (sessionType == SessionType.PAID) {
            return new Session(id, image, sessionType, sessionStatus, price, maxStudentCount);
        }
        return new Session(id, image, sessionType, sessionStatus);
    }

    private Session(Long sessionId, Image image, SessionType sessionType, SessionStatus sessionStatus, Long price,
                    Integer maxStudents) {
        this.sessionId = sessionId;
        this.image = image;
        sessionType.validate(price, maxStudents);
        this.sessionInfo = new SessionInfo(sessionType, price, maxStudents);
        this.sessionRegisterInfo = new SessionRegisterInfo(sessionStatus);
    }

    private Session(Long sessionId, Image image, SessionType sessionType, SessionStatus sessionStatus) {
        this(sessionId, image, sessionType, sessionStatus, 0L, Integer.MAX_VALUE);
    }

    public long getPrice() {
        return sessionInfo.getPrice();
    }

    public void register(NsUser nsUser, long payment) {
        registerStudent(nsUser, payment);
        sessionRegisterInfo.addPaymentHistory(nsUser, payment, sessionId);
    }


    public void registerStudent(NsUser nsUser, long payment) {
        sessionRegisterInfo.checkSessionIsRegistering();
        sessionInfo.checkPaymentEqualsPrice(payment);
        sessionInfo.checkCurrentNumberOfStudentsIsMax(getNumberOfStudents());
        sessionRegisterInfo.addStudent(nsUser);
    }

    public int getNumberOfStudents() {
        return sessionRegisterInfo.getNumberOfStudents();
    }

    public int getNumberOfPayments() {
        return sessionRegisterInfo.getNumberOfPayments();
    }

}
