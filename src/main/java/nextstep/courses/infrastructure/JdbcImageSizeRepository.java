package nextstep.courses.infrastructure;

import nextstep.courses.domain.ImageSize;
import nextstep.courses.domain.ImageSizeRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("ImageSizeRepository")
public class JdbcImageSizeRepository implements ImageSizeRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcImageSizeRepository(JdbcOperations jdbcOperations) {
        this.jdbcTemplate = jdbcOperations;
    }

    @Override
    public int save(ImageSize imageSize) {
        String sql = "insert into image_size (session_id,id, image_size) values (?, ?, ?)";
        return jdbcTemplate.update(sql, imageSize.getSessionId(), imageSize.getImageId(), imageSize.getImageSize());
    }

    @Override
    public ImageSize findById(Long id) {
        String sql = "select * from image_size where id = ?";
        RowMapper<ImageSize>  rowMapper = ((rs, rowNum) ->
                new ImageSize(rs.getLong(1),
                        rs.getLong(2),
                        rs.getInt(3)));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

}
