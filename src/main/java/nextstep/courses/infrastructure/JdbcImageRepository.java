package nextstep.courses.infrastructure;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import nextstep.courses.domain.Image;
import nextstep.courses.domain.ImageRepository;
import nextstep.courses.domain.ImageSize;
import nextstep.courses.domain.ImageType;
import nextstep.courses.domain.ImageWidthHeight;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("imageRepository")
public class JdbcImageRepository implements ImageRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Image image) {
        String sql = "insert into image (id,image_type,session_id,image_size,image_width, image_height) values(?, ?, ?, ?, ?, ?);";
        return jdbcTemplate.update(sql, image.getId(), image.getImageType().name(), image.getSessionId()
        , image.getImageSize().getImageSize()
        , image.getImageWidthHeight().getWidth(), image.getImageWidthHeight().getHeight());
    }

    @Override
    public Optional<List<Image>> findById(Long id) {
        String sql = "select * from image where session_id = ?";
        RowMapper<Image> rowMapper = (rs, rowNum) -> new Image(
                rs.getLong("id"),
                rs.getLong("session_id"),
                new ImageSize(rs.getLong("id"),rs.getInt("image_size")),
                ImageType.valueOf(rs.getString("image_type")),
                new ImageWidthHeight(rs.getLong("id"),rs.getInt("image_width"),rs.getInt("image_height")));
        List<Image> images = jdbcTemplate.query(sql, rowMapper, id);
        return Optional.of(images);
    }

    public Optional<ImageSize> findByIdImageSize(Long id) {
        String sql = "select * from image where id = ?";
        RowMapper<ImageSize> rowMapper = (rs, rowNum) ->
                new ImageSize(rs.getLong("id"),rs.getInt("image_size"));
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    public Optional<ImageWidthHeight> findByIdImageWidthHeight(Long id) {
        String sql = "select id,image_width,image_height from image where id = ?";
        RowMapper<ImageWidthHeight> rowMapper = (rs, rowNum) ->
                new ImageWidthHeight(rs.getLong("id"),rs.getInt("image_width"),rs.getInt("image_height"));
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
