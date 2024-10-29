package nextstep.courses.domain;

import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import org.junit.jupiter.params.provider.ValueSource;

public class SessionTest {

    Image image = new Image(ImageSizeTest.standardImageSize, ImageType.JPG, ImageWidthHeightTest.standardImageWidthHeight);

    @Test
    @DisplayName("유료 강의가 잘 생성되는지 확인한다")
    void 유료_강의_생성() {
        Assertions.assertThatCode(() ->
                Session.createSession(0L,image,SessionType.PAID, SessionStatus.READY, 1, 1));

    }

    @Test
    @DisplayName("유료 강의가 0원(무료이면) 이하 이면 예외가 발생한다")
    void 유료_강의_가격이_0원_이하일_시_예외_발생() {
        Assertions.assertThatThrownBy(() ->
                Session.createSession(0L,image,SessionType.PAID,SessionStatus.READY, 0, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유료강의의 강의는 1원 이상이어야 합니다.");

    }
    @Test
    @DisplayName("유료 강의가 0원(무료이면) 이하 이면 예외가 발생한다")
    void 유료_강의_학생_수가_0명_이하일_시_예외_발생() {
        Assertions.assertThatThrownBy(() ->
                Session.createSession(0L,image,SessionType.PAID,SessionStatus.READY, 1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유료 강의의 최대 수강인원은 0명 이하일 수 없습니다");

    }

    @Test
    @DisplayName("무료 강의가 잘 생성되는지 확인한다")
    void 무료_강의_생성() {
        Assertions.assertThatCode(() ->
                Session.createSession(0L,image,SessionType.FREE,SessionStatus.READY,0,0));
    }

    @Test
    @DisplayName("무료 강의의 가격이 0인지 확인한다")
    void 무료_강의_가격_0원_인지_확인() {
        Assertions.assertThat(
                Session.createSession(0L,image,SessionType.FREE,SessionStatus.READY,0,0).getPrice())
                .isEqualTo(0);
    }

    @Test
    @DisplayName("무료 강의의 수한생 제한이 없는지 (Integer.MAX_VALUE)인지 확인")
    void 무료_강의_수강생_제한_확인() {
        Assertions.assertThat(
                        Session.createSession(0L,image,SessionType.FREE,SessionStatus.READY,0,0).getMaxStudents())
                .isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    @DisplayName("모집중인 강의에 수강신청을 할 수 있는지 검증한다")
    void 모집중인_강의_수강신청_테스트() {
        Session session = Session.createSession(0L,image,SessionType.PAID, SessionStatus.REGISTER, 100, 1);

        session.registterStudent(NsUserTest.JAVAJIGI,100);

        Assertions.assertThat(session.getNumberOfStudents()).isEqualTo(1);
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 300})
    @DisplayName("수강료가 일치하지 않을 경우 강의신청을 할 수 없다")
    void 수강료_일치하지_않을_경우_예외_발생(int testCase) {
        Session session = Session.createSession(0L,image,SessionType.PAID, SessionStatus.REGISTER, 200, 1);


        Assertions.assertThatThrownBy(
                () -> session.registterStudent(NsUserTest.JAVAJIGI,testCase)
        ).isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("수강료와 가격이 일치하지 않습니다");

    }

    @ParameterizedTest
    @EnumSource(mode = Mode.EXCLUDE, names = {"REGISTER"})
    @DisplayName("모집 중인 강의를 수강신청하면 예외가 발생한다")
    void 모집중이지_않은_강의_수강신청_불가(SessionStatus status) {
        Session session = Session.createSession(0L,image,SessionType.PAID, status, 200, 1);

        Assertions.assertThatThrownBy(
                () -> session.registterStudent(NsUserTest.JAVAJIGI,200)
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 강의는 지금 모집중인 상태가 아닙니다");
    }

    @Test
    @DisplayName("유료강의 현재 수강정원이 최대인원일 시 예외 발생")
    void 유료강의_수강인원_최대일_시_예외_발생() {
        Session session = Session.createSession(0L,image,SessionType.PAID, SessionStatus.REGISTER, 200, 1);

        session.registterStudent(NsUserTest.JAVAJIGI,200);

        Assertions.assertThatThrownBy(
                        () -> session.registterStudent(NsUserTest.JAVAJIGI,200)
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수강 정원이 모두 찼습니다.");
    }

    @Test
    @DisplayName("무료강의 수강인원 제한 없음(Integer.MAX_VALUE)")
    void 무료강의_수강인원_제한_없음() {
        Session session = Session.createSession(0L,image,SessionType.FREE, SessionStatus.REGISTER, 200, 0);

        session.registterStudent(NsUserTest.JAVAJIGI,200);

        Assertions.assertThatCode(
                () -> session.registterStudent(NsUserTest.JAVAJIGI,200)
                );
    }

    @Test
    @DisplayName("결제정보가 잘 저장되는지 확인한다")
    void 결제정보_저장_확인() {
        Session session = Session.createSession(0L,image,SessionType.PAID, SessionStatus.REGISTER, 200, 1);

        session.register(NsUserTest.JAVAJIGI,200);

        Assertions.assertThat(
                session.getNumberOfPayments()
                ).isEqualTo(1);
    }

}
