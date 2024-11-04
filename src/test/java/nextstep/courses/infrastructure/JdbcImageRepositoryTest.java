package nextstep.courses.infrastructure;

import nextstep.courses.domain.Image;
import nextstep.courses.domain.ImageRepository;
import nextstep.courses.domain.ImageSize;
import nextstep.courses.domain.ImageType;
import nextstep.courses.domain.ImageWidthHeight;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class JdbcImageRepositoryTest {
    private static final Image image = new Image(1L , 0L,
            new ImageSize(0L, 100), ImageType.JPEG, new ImageWidthHeight(0L,600, 400));

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ImageRepository imageRepository;

    @BeforeEach
    void setUp() {
        imageRepository = new JdbcImageRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("이미지 저장 테스트")
    void saveTest() {
        Assertions.assertThat(imageRepository.save(image)).isEqualTo(1);
    }

    @Test
    @DisplayName("id로 이미지 조회 테스트")
    void findByIdTest() {
        imageRepository.save(image);
        Assertions.assertThat(imageRepository.findById(image.getId()).get()).isEqualTo(image);
    }


}
