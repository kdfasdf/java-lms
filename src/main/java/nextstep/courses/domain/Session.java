package nextstep.courses.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class Session {
    private final Long sessionId;
    private final Image image;
    private final SessionType sessionType;
    private final SessionStatus sessionStatus;
    private final List<NsUser> students = new ArrayList<>();
    private final List<Payment> payments = new ArrayList<>();
    private final LocalDateTime startDate = LocalDateTime.now();

    private LocalDateTime endDate;
    private long price;
    private int maxStudents;


    public static Session createSession(Long id, Image image, SessionType sessionType, SessionStatus sessionStatus,
                                        int price, int maxStudents) {
        if (sessionType == SessionType.PAID) {
            return new Session(id, image, sessionType, sessionStatus, price, maxStudents);
        }
        return new Session(id, image, sessionType, sessionStatus);
    }

    private Session(Long sessionId, Image image, SessionType sessionType, SessionStatus sessionStatus, int price,
                    int maxStudents) {
        this.sessionId = sessionId;
        this.image = image;
        this.sessionType = sessionType;
        sessionType.validate(price, maxStudents);
        this.sessionStatus = sessionStatus;
        this.price = price;
        this.maxStudents = maxStudents;
    }

    private Session(Long sessionId, Image image, SessionType sessionType, SessionStatus sessionStatus) {
        this(sessionId, image, sessionType, sessionStatus, 0, Integer.MAX_VALUE);
    }

    public long getPrice() {
        return this.price;
    }

    public int getMaxStudents() {
        return this.maxStudents;
    }

    public void register(NsUser nsUser, long payment) {
        registterStudent(nsUser, payment);
        addPaymentHistory(nsUser, payment);
    }

    private void addPaymentHistory(NsUser nsUser, long payment) {
        payments.add(new Payment("", sessionId, nsUser.getId(), payment));
    }

    public void registterStudent(NsUser nsUser, long payment) {
        checkSessionIsRegistering();
        checkPaymentEqualsPrice(payment);
        checkCurrentNumberOfStudentsMax(getNumberOfStudents());
        students.add(nsUser);
    }

    private void checkCurrentNumberOfStudentsMax(int numberOfStudents) {
        if (numberOfStudents == maxStudents) {
            throw new IllegalArgumentException("수강 정원이 모두 찼습니다.");
        }
    }

    private void checkSessionIsRegistering() {
        if (sessionStatus != SessionStatus.REGISTER) {
            throw new IllegalArgumentException("이 강의는 지금 모집중인 상태가 아닙니다");
        }
    }

    private void checkPaymentEqualsPrice(long payment) {
        if (sessionType == SessionType.PAID && price != payment) {
            throw new IllegalArgumentException("수강료와 가격이 일치하지 않습니다");
        }
    }

    public int getNumberOfStudents() {
        return students.size();
    }

    public int getNumberOfPayments() {
        return payments.size();
    }

}
