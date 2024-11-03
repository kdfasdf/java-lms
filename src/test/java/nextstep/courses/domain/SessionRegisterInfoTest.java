package nextstep.courses.domain;

import java.time.LocalDateTime;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;

public class SessionRegisterInfoTest {
    Image image = new Image(0L, 0L, ImageSizeTest.standardImageSize, ImageType.JPG,
            ImageWidthHeightTest.standardImageWidthHeight);
    SessionDuration sessionDuration = new SessionDuration(0L,
            LocalDateTime.of(2024, 10, 28, 0, 0),
            LocalDateTime.of(2024, 11, 1, 0, 0)
    );


    @Test
    @DisplayName("유료 강의가 학생 수가 0명 이하면 예외가 발생한다")
    void 유료_강의_학생_수가_0명_이하일_시_예외_발생() {
        Assertions.assertThatThrownBy(() ->
                        Session.createPaidSession(0L, image, SessionType.PAID, SessionStatus.READY, 1L, 0, sessionDuration))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유료 강의의 최대 수강인원은 0명 이하일 수 없습니다");

    }

    @Test
    @DisplayName("모집중인 강의에 수강신청을 할 수 있는지 검증한다")
    void 모집중인_강의_수강신청_테스트() {
        Session session = Session.createFreeSession(0L, image, SessionType.FREE, SessionStatus.REGISTER,
                sessionDuration);

        session.register(NsUserTest.JAVAJIGI, 100L);

        Assertions.assertThat(session.getNumberOfStudents()).isEqualTo(1);
    }

    @ParameterizedTest
    @EnumSource(mode = Mode.EXCLUDE, names = {"REGISTER"})
    @DisplayName("모집 중인 강의를 수강신청하면 예외가 발생한다")
    void 모집중이지_않은_강의_수강신청_불가(SessionStatus status) {
        Session session = Session.createPaidSession(0L, image, SessionType.PAID, status, 200L, 1, sessionDuration);

        Assertions.assertThatThrownBy(
                        () -> session.registerStudent(NsUserTest.JAVAJIGI, 200L)
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 강의는 지금 모집중인 상태가 아닙니다");
    }

    @Test
    @DisplayName("결제정보가 잘 저장되는지 확인한다")
    void 결제정보_저장_확인() {
        Session session = Session.createPaidSession(0L, image, SessionType.PAID, SessionStatus.REGISTER, 200L, 1,
                sessionDuration);

        session.register(NsUserTest.JAVAJIGI, 200L);

        Assertions.assertThat(session.getNumberOfPayments()).isEqualTo(1);
    }


    @Test
    @DisplayName("수강신청할 시 학생이 정상적으로 등록되는지 확인")
    void 수강등록할_시_학생이_정상적으로_등록되는지_확인() {
        Session session = Session.createPaidSession(0L, image, SessionType.PAID, SessionStatus.REGISTER, 200L, 1,
                sessionDuration);

        session.register(NsUserTest.JAVAJIGI, 200L);

        Assertions.assertThat(session.getNumberOfStudents()).isEqualTo(1);
    }


    @Test
    @DisplayName("이미 수강신청한 학생일 경우 예외 발생")
    void 이미_수강신청_했다면_예외_발생() {
        Session session = Session.createPaidSession(0L, image, SessionType.PAID, SessionStatus.REGISTER, 200L, 2,
                sessionDuration);

        session.register(NsUserTest.JAVAJIGI, 200L);

        Assertions.assertThatThrownBy(
                        () -> session.register(NsUserTest.JAVAJIGI, 200L)
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 수업에 등록한 학생입니다");
    }


}
