package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;
import nextstep.users.domain.NsUser;

public class Session {
    private final Long id;
    private final Image image;
    private final SessionType sessionType;
    private final List<NsUser> Students = new ArrayList<>();
    int price;
    int maxStudents;

    public static Session createSession(Long id, Image image, SessionType sessionType, int price, int maxStudents) {
        if(sessionType == SessionType.PAID) {
            return new Session(id, image, sessionType, price, maxStudents);
        }
        return new Session(id, image, sessionType);
    }

    private Session(Long id, Image image, SessionType sessionType, int price, int maxStudents) {
        this.id = id;
        this.image = image;
        this.sessionType = sessionType;
        sessionType.validate(price,maxStudents);
        this.price = price;
        this.maxStudents = maxStudents;
    }

    private Session(Long id, Image image, SessionType sessionType) {
        this(id, image, sessionType, 0, Integer.MAX_VALUE);
    }

    int getPrice() {
        return this.price;
    }

    int getMaxStudents() {
        return this.maxStudents;
    }

}
