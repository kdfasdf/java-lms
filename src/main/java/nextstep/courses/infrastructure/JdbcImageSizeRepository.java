package nextstep.courses.infrastructure;

import nextstep.courses.domain.ImageSize;
import nextstep.courses.domain.ImageSizeRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("imageSizeRepository")
public class JdbcImageSizeRepository implements ImageSizeRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcImageSizeRepository(JdbcOperations jdbcOperations) {
        this.jdbcTemplate = jdbcOperations;
    }

    @Override
    public int save(ImageSize imageSize) {
        String sql = "insert into image_size (id, image_size) values (?, ?)";
        return jdbcTemplate.update(sql, imageSize.getImageId(), imageSize.getImageSize());
    }

    @Override
    public ImageSize findById(Long id) {
        String sql = "select * from image_size where id = ?";
        RowMapper<ImageSize>  rowMapper = ((rs, rowNum) ->
                new ImageSize(rs.getLong("id"),
                        rs.getInt("image_size")));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

}
