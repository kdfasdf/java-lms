package nextstep.courses.infrastructure;

import nextstep.courses.domain.ImageSize;
import nextstep.courses.domain.ImageSizeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class JdbcImageSizeRepositoryTest {
    private final ImageSize imageSize = new ImageSize(0L, 100);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ImageSizeRepository imageSizeRepository;

    @BeforeEach
    void setUp() {
        imageSizeRepository = new JdbcImageSizeRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("이미지 사이즈 저장 테스트")
    void saveTest() {
        Assertions.assertThat(imageSizeRepository.save(imageSize)).isEqualTo(1);
    }

    @Test
    @DisplayName("이미지 id로 이미지 사이즈 조회 테스트")
    void findByIdTest() {
        imageSizeRepository.save(imageSize);
        Assertions.assertThat(imageSizeRepository.findById(imageSize.getImageId())).isEqualTo(imageSize);
    }
}
