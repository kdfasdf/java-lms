package nextstep.courses.infrastructure;

import nextstep.courses.domain.ImageWidthHeight;
import nextstep.courses.domain.ImageWidthHeightRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("imageWidthHeightRepository")
public class JdbcImageWidthHeightRepository implements ImageWidthHeightRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcImageWidthHeightRepository(JdbcOperations jdbcOperations) {
        this.jdbcTemplate = jdbcOperations;
    }

    @Override
    public int save(ImageWidthHeight imageWidthHeight) {
        String sql = "insert into image_width_height (id, image_width, image_height) values (?, ?, ?)";
        return jdbcTemplate.update(sql, imageWidthHeight.getId(), imageWidthHeight.getWidth(),
                imageWidthHeight.getHeight());
    }

    @Override
    public ImageWidthHeight findById(Long id) {
        String sql = "select * from image_width_height where id = ?";
        RowMapper<ImageWidthHeight> rowMapper = ((rs, rowNum) ->
                new ImageWidthHeight(rs.getLong("id"),
                        rs.getInt("image_width"),
                        rs.getInt("image_height")));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
}
