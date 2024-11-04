package nextstep.courses.domain;

import nextstep.courses.exception.ImageHeightPixelException;
import nextstep.courses.exception.ImageWidthPixelException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImageWidthHeightTest {

    public static final ImageWidthHeight standardImageWidthHeight = new ImageWidthHeight(0L,300,200);

    @Test
    @DisplayName("width는 300 이상이어야 함")
    void 너비_300_이상() {
        Assertions.assertThatCode(() -> new ImageWidthHeight(0L,300,200))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("width가 300 미만일 경우 예외 발생")
    void 너비_300_미만_예외_발생() {
        Assertions.assertThatThrownBy(
                () -> new ImageWidthHeight(0L,299, 200))
                .isInstanceOf(ImageWidthPixelException.class);
    }

    @Test
    @DisplayName("height는 200 이상이어야 함")
    void 높이_200_이상() {
        Assertions.assertThatCode(() -> new ImageWidthHeight(0L,300,200))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("height가 200 미만일 경우 예외 발생")
    void 높이_200_미만_예외_발생() {
        Assertions.assertThatThrownBy(
                () -> new ImageWidthHeight(0L,300, 199))
                .isInstanceOf(ImageHeightPixelException.class);
    }

    @Test
    @DisplayName("width, height 비율 3:2가 아닌 경우 예외 발생")
    void 너비_높이_비율_3대_2가_아닌경우_예외_발생() {
        Assertions.assertThatThrownBy(
                () -> new ImageWidthHeight(0L,400, 200))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("width 와 height의 비율이 3:2 이어야 합니다");
    }
}
