package nextstep.courses.domain;

import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;

public class SessionRegisterInfoTest {
    Image image = new Image(ImageSizeTest.standardImageSize, ImageType.JPG, ImageWidthHeightTest.standardImageWidthHeight);

    @Test
    @DisplayName("유료 강의가 학생 수가 0명 이하면 예외가 발생한다")
    void 유료_강의_학생_수가_0명_이하일_시_예외_발생() {
        Assertions.assertThatThrownBy(() ->
                        Session.createSession(0L,image,SessionType.PAID,SessionStatus.READY, 1L, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유료 강의의 최대 수강인원은 0명 이하일 수 없습니다");

    }

    @Test
    @DisplayName("모집중인 강의에 수강신청을 할 수 있는지 검증한다")
    void 모집중인_강의_수강신청_테스트() {
        Session session = Session.createSession(0L,image,SessionType.PAID, SessionStatus.REGISTER, 100L, 1);

        session.register(NsUserTest.JAVAJIGI,100L);

        Assertions.assertThat(session.getNumberOfStudents()).isEqualTo(1);
    }

    @ParameterizedTest
    @EnumSource(mode = Mode.EXCLUDE, names = {"REGISTER"})
    @DisplayName("모집 중인 강의를 수강신청하면 예외가 발생한다")
    void 모집중이지_않은_강의_수강신청_불가(SessionStatus status) {
        Session session = Session.createSession(0L,image,SessionType.PAID, status, 200L, 1);

        Assertions.assertThatThrownBy(
                        () -> session.registerStudent(NsUserTest.JAVAJIGI,200L)
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 강의는 지금 모집중인 상태가 아닙니다");
    }

    @Test
    @DisplayName("결제정보가 잘 저장되는지 확인한다")
    void 결제정보_저장_확인() {
        Session session = Session.createSession(0L,image,SessionType.PAID, SessionStatus.REGISTER, 200L, 1);

        session.register(NsUserTest.JAVAJIGI,200L);

        Assertions.assertThat(session.getNumberOfPayments()).isEqualTo(1);
    }

}
