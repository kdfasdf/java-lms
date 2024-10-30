package nextstep.courses.domain;

import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SessionInfoTest {
    Image image = new Image(ImageSizeTest.standardImageSize, ImageType.JPG, ImageWidthHeightTest.standardImageWidthHeight);

    @Test
    @DisplayName("유료 강의가 잘 생성되는지 확인한다")
    void 유료_강의_생성() {
        Assertions.assertThatCode(() ->
                Session.createSession(0L,image,SessionType.PAID, SessionStatus.READY, 1L, 1));

    }

    @Test
    @DisplayName("무료 강의가 잘 생성되는지 확인한다")
    void 무료_강의_생성() {
        Assertions.assertThatCode(() ->
                Session.createSession(0L,image,SessionType.FREE,SessionStatus.READY,0L,0));
    }

    @Test
    @DisplayName("무료 강의의 가격이 0인지 확인한다")
    void 무료_강의_가격_0원_인지_확인() {
        Assertions.assertThat(
                        Session.createSession(0L,image,SessionType.FREE,SessionStatus.READY,0L,0).getPrice())
                .isEqualTo(0);
    }

    @Test
    @DisplayName("유료 강의가 0원(무료이면) 이하 이면 예외가 발생한다")
    void 유료_강의_가격이_0원_이하일_시_예외_발생() {
        Assertions.assertThatThrownBy(() ->
                        Session.createSession(0L,image,SessionType.PAID,SessionStatus.READY, 0L, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유료강의의 강의는 1원 이상이어야 합니다.");

    }

    @Test
    @DisplayName("무료 강의의 수한생 제한이 없는지 (Integer.MAX_VALUE)인지 확인")
    void 무료_강의_수강생_제한_확인() {
        Assertions.assertThat(
                        Session.createSession(0L,image,SessionType.FREE,SessionStatus.READY,0L,0).getMaxStudents())
                .isEqualTo(Integer.MAX_VALUE);
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 300})
    @DisplayName("수강료가 일치하지 않을 경우 강의신청을 할 수 없다")
    void 수강료_일치하지_않을_경우_예외_발생(int testCase) {
        Session session = Session.createSession(0L,image,SessionType.PAID, SessionStatus.REGISTER, 200L, 1);


        Assertions.assertThatThrownBy(
                        () -> session.register(NsUserTest.JAVAJIGI,testCase)
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수강료와 가격이 일치하지 않습니다");

    }

    @Test
    @DisplayName("유료강의 현재 수강정원이 최대인원일 시 예외 발생")
    void 유료강의_수강인원_최대일_시_예외_발생() {
        Session session = Session.createSession(0L,image,SessionType.PAID, SessionStatus.REGISTER, 200L, 1);

        session.registerStudent(NsUserTest.JAVAJIGI,200L);

        Assertions.assertThatThrownBy(
                        () -> session.register(NsUserTest.JAVAJIGI,200)
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수강 정원이 모두 찼습니다.");
    }

    @Test
    @DisplayName("무료강의 수강인원 제한 없음(Integer.MAX_VALUE)")
    void 무료강의_수강인원_제한_없음() {
        Session session = Session.createSession(0L,image,SessionType.FREE, SessionStatus.REGISTER, 200L, 0);

        session.registerStudent(NsUserTest.JAVAJIGI,200L);

        Assertions.assertThatCode(
                () -> session.register(NsUserTest.JAVAJIGI,200)
        );
    }


}
