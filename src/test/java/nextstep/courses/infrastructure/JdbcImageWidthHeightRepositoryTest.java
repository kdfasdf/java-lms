package nextstep.courses.infrastructure;


import nextstep.courses.domain.ImageWidthHeight;
import nextstep.courses.domain.ImageWidthHeightRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class JdbcImageWidthHeightRepositoryTest {
    private final ImageWidthHeight imageWidthHeight = new ImageWidthHeight(0L,600,400);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ImageWidthHeightRepository imageWidthHeightRepository;

    @BeforeEach
    void setUp() {
       imageWidthHeightRepository = new JdbcImageWidthHeightRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("이미지 너비높이 저장 테스트")
    void saveTest() {
        Assertions.assertThat(imageWidthHeightRepository.save(imageWidthHeight)).isEqualTo(1);
    }

    @Test
    @DisplayName("이미지 id로 이미지 너비높이 테이블 조회 테스트")
    void findByIdTest() {
        imageWidthHeightRepository.save(imageWidthHeight);
        Assertions.assertThat(imageWidthHeightRepository.findById(imageWidthHeight.getId())).isEqualTo(imageWidthHeight);
    }

}
