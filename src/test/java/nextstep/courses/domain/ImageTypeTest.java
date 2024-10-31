package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ImageTypeTest {
    @ParameterizedTest
    @ValueSource(strings = {"GIF", "JPG", "JPEG", "PNG", "SVG"})
    @DisplayName("지원하는 이미지 타입들을 검사한다")
    void 지원_이미지_타입_검사(String testCase) {
        Assertions.assertThatCode(() -> ImageType.from(testCase));
    }

    @Test
    @DisplayName("지원하지 않는 이미지 타입이면 예외가 발생한다")
    void 미지원_이미지_타입_검사() {
        Assertions.assertThatThrownBy(() -> ImageType.from("invalid ImageType"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지원하지 않는 이미지 타입");
    }
}
