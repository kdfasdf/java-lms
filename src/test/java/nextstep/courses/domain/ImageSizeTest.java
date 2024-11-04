package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ImageSizeTest {

    public static final ImageSize standardImageSize = new ImageSize(0L,1024 * 1024);

    @ParameterizedTest
    @ValueSource(ints = {1024 * 1024, 1})
    @DisplayName("이미지의 크기가 1MB 이하일 때 통과한다")
    void 이미지_크기는_1MB_이하(int testCase) {
        Assertions.assertThatCode(() -> new ImageSize(0L,testCase));
    }

    @Test
    @DisplayName("이미지의 크기가 1MB를 넘어가면 예외가 발생한다")
    void 이미지_1MB_초과_시_예외() {
        Assertions.assertThatThrownBy(() -> new ImageSize(0L,1024 * 1024+1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("현재 이미지 사이즈 1048577 1MB를 초과합니다");
    }

    @Test
    @DisplayName("이미지의 크기가 0이하면 예외가 발생한다")
    void 이미지_크기_0_이하일_시_예외() {
        Assertions.assertThatThrownBy(() -> new ImageSize(0L,0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지의 크기는 1바이트 이상이어야 합니다.");
    }
}
