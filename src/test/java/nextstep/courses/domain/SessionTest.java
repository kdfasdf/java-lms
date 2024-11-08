package nextstep.courses.domain;

import java.time.LocalDateTime;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SessionTest {
    private static final Image image = new Image(1L , 0L,
            new ImageSize(0L, 100), ImageType.JPEG, new ImageWidthHeight(0L,600, 400));


    private final SessionDuration sessionDuration = new SessionDuration(0L, LocalDateTime.now(),LocalDateTime.now().plusMinutes(1));

    private final SessionInfo sessionInfo = new SessionInfo(0L, SessionType.PAID, 1000L, 100);

    private final SessionRegisterInfo sessionRegisterInfo = new SessionRegisterInfo(
            0L, SessionStatus.REGISTER, Students.from(), Payments.from(), SessionRegisteringStatus.OPEN
    );


    @Test
    @DisplayName("선발되지 않은 학생을 수강 취소 할 수 있는지 확인한다")
    void 선발되지_않은_학생_수강_취소() {
        Session session = Session.createPaidSession(0L, image, SessionType.PAID, SessionStatus.REGISTER, 1000L, 100,sessionDuration, SessionRegisteringStatus.OPEN);

        session.addStudentBySelectedStatus(NsUserTest.JAVAJIGI,SelectStatus.UNSELECTED);
        session.addStudentBySelectedStatus(NsUserTest.SANJIGI, SelectStatus.SELECTED);

        session.getSessionRegisterInfo().removeUnselectedStudents();

        Assertions.assertThat(session.getNumberOfStudents()).isEqualTo(1);
    }

}
