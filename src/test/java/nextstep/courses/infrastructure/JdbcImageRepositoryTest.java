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

    private static final ImageSize imageSize = new ImageSize(1L, 100);
    private static final ImageWidthHeight imageWidthHeight = new ImageWidthHeight(1L,600, 400);
    private static final Image image = new Image(1L , 0L,
            imageSize, ImageType.JPEG, imageWidthHeight);

    private static final Image image2 = new Image(2L , 0L,
            imageSize, ImageType.JPEG, imageWidthHeight);

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
        Assertions.assertThat(imageRepository.findById(image.getSessionId()).get().get(0)).isEqualTo(image);
    }

    @Test
    @DisplayName("id로 이미지 사이즈조회 테스트")
    void findByIdImageSizeTest() {
        JdbcImageRepository jdbcImageRepository = new JdbcImageRepository(jdbcTemplate);
        jdbcImageRepository.save(image);
        Assertions.assertThat(jdbcImageRepository.findByIdImageSize(image.getId()).get()).isEqualTo(imageSize);
    }

    @Test
    @DisplayName("id로 이미지 너비,높이 조회 테스트")
    void findByIdImageWidthHeightTest() {
        JdbcImageRepository jdbcImageRepository = new JdbcImageRepository(jdbcTemplate);
        jdbcImageRepository.save(image);
        Assertions.assertThat(jdbcImageRepository.findByIdImageWidthHeight(image.getId()).get()).isEqualTo(imageWidthHeight);
    }

    @Test
    @DisplayName("이미지 여러장 저장 테스트")
    void saveImagesTest() {
        imageRepository.save(image);
        imageRepository.save(image2);
        Assertions.assertThat(imageRepository.findById(0L).get().size()).isEqualTo(2);
    }
}
