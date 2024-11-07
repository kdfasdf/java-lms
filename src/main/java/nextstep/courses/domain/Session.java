package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import nextstep.users.domain.NsUser;

public class Session {
    private final Long sessionId;
    private final List<Image> image = new ArrayList<>();
    private final SessionDuration sessionDuration;
    private final SessionInfo sessionInfo;
    private final SessionRegisterInfo sessionRegisterInfo;

    public static Session createPaidSession(Long id, Image image, SessionType sessionType, SessionStatus sessionStatus,
                                            Long price, Integer maxStudentCount, SessionDuration sessionDuration,
                                            SessionRegisteringStatus sessionRegisteringStatus) {
        return new Session(id, image, sessionType, sessionStatus, price, maxStudentCount, sessionDuration, sessionRegisteringStatus);
    }

    public static Session createFreeSession(Long id, Image image, SessionType sessionType, SessionStatus sessionStatus,
                                            SessionDuration sessionDuration, SessionRegisteringStatus sessionRegisteringStatus) {
        return new Session(id, image, sessionType, sessionStatus, sessionDuration, sessionRegisteringStatus);
    }

    private Session(Long sessionId, Image image, SessionType sessionType, SessionStatus sessionStatus, Long price,
                    Integer maxStudents, SessionDuration sessionDuration, SessionRegisteringStatus sessionRegisteringStatus) {
        this.sessionId = sessionId;
        this.image.add(image);
        sessionType.validate(price, maxStudents);
        this.sessionInfo = new SessionInfo(sessionId, sessionType, price, maxStudents);
        this.sessionRegisterInfo = new SessionRegisterInfo(sessionId,sessionStatus, Students.from(), Payments.from(), sessionRegisteringStatus);
        this.sessionDuration = sessionDuration;
    }

    private Session(Long sessionId, Image image, SessionType sessionType, SessionStatus sessionStatus,
                    SessionDuration sessionDuration, SessionRegisteringStatus sessionRegisteringStatus) {
        this(sessionId, image, sessionType, sessionStatus, 0L, Integer.MAX_VALUE, sessionDuration, sessionRegisteringStatus);
    }

    public void register(NsUser nsUser, long payment,SelectStatus selectStatus) {
        registerStudent(nsUser, payment,selectStatus);
        addPaymentHistory(nsUser.getId(), payment, sessionId);
    }


    public void registerStudent(NsUser nsUser, long payment, SelectStatus selectStatus) {
        sessionRegisterInfo.checkSessionIsOpen();
        sessionInfo.checkPaymentEqualsPrice(payment);
        sessionInfo.checkCurrentNumberOfStudentsIsMax(getNumberOfStudents());
        sessionRegisterInfo.addStudentBySelectedStatus(nsUser, selectStatus);
    }

    private void addPaymentHistory(Long userId, long payment, Long sessionId) {
        sessionRegisterInfo.addPaymentHistory(userId, payment, sessionId);
    }

    public int getNumberOfStudents() {
        return sessionRegisterInfo.getNumberOfStudents();
    }

    public long getPrice() {
        return sessionInfo.getPrice();
    }

    public int getNumberOfPayments() {
        return sessionRegisterInfo.getNumberOfPayments();
    }

    public Long getSessionId() {
        return sessionId;
    }

    public List<Image> getImage() {
        return image;
    }

    public SessionDuration getSessionDuration() {
        return sessionDuration;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public SessionRegisterInfo getSessionRegisterInfo() {
        return sessionRegisterInfo;
    }

    public boolean isRegistering() {
        return sessionRegisterInfo.isRegistering();
    }

    public void addStudentBySelectedStatus(NsUser nsUser, SelectStatus selectStatus) {
        sessionRegisterInfo.addStudentBySelectedStatus(nsUser, selectStatus);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Session)) {
            return false;
        }

        Session session = (Session) o;
        return Objects.equals(sessionId, session.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sessionId);
    }

}
