package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SessionTest {

    Image image = new Image(ImageSizeTest.standardImageSize, ImageType.JPG, ImageWidthHeightTest.standardImageWidthHeight);

    @Test
    @DisplayName("유료 강의가 잘 생성되는지 확인한다")
    void 유료_강의_생성() {
        Assertions.assertThatCode(() ->
                Session.createSession(0L,image,SessionType.PAID, 1, 1));

    }

    @Test
    @DisplayName("유료 강의가 0원(무료이면) 이하 이면 예외가 발생한다")
    void 유료_강의_가격이_0원_이하일_시_예외_발생() {
        Assertions.assertThatThrownBy(() ->
                Session.createSession(0L,image,SessionType.PAID, 0, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유료강의의 강의는 1원 이상이어야 합니다.");

    }
    @Test
    @DisplayName("유료 강의가 0원(무료이면) 이하 이면 예외가 발생한다")
    void 유료_강의_학생_수가_0명_이하일_시_예외_발생() {
        Assertions.assertThatThrownBy(() ->
                Session.createSession(0L,image,SessionType.PAID, 1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유료 강의의 최대 수강인원은 0명 이하일 수 없습니다");

    }

    @Test
    @DisplayName("무료 강의가 잘 생성되는지 확인한다")
    void 무료_강의_생성() {
        Assertions.assertThatCode(() ->
                Session.createSession(0L,image,SessionType.FREE,0,0));
    }

    @Test
    @DisplayName("무료 강의의 가격이 0인지 확인한다")
    void 무료_강의_가격_0원_인지_확인() {
        Assertions.assertThat(
                Session.createSession(0L,image,SessionType.FREE,0,0).getPrice())
                .isEqualTo(0);
    }

    @Test
    @DisplayName("무료 강의의 수한생 제한이 없는지 (Integer.MAX_VALUE)인지 확인")
    void 무료_강의_수강생_제한_확인() {
        Assertions.assertThat(
                        Session.createSession(0L,image,SessionType.FREE,0,0).getMaxStudents())
                .isEqualTo(Integer.MAX_VALUE);
    }

}
