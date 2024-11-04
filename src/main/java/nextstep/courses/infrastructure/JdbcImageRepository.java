package nextstep.courses.infrastructure;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
        String sql = "insert into image (id,image_type,session_id) values(?, ?, ?);"
                + "insert into image_size (id, image_size) values (?, ?);"
                +"insert into image_width_height(id, image_width, image_height) values (?, ?, ?);";
        return jdbcTemplate.update(sql, image.getId(), image.getImageType().name(), image.getSessionId()
        , image.getId(), image.getImageSize().getImageSize()
        ,image.getId(),  image.getImageWidthHeight().getWidth(), image.getImageWidthHeight().getHeight());
    }

    @Override
    public Optional<Image> findById(Long id) {
        String sql = "select i.id as image_id, i.image_type, i.session_id, "
                + "s.id as size_id, s.image_size,"
                + "wh.id as width_height_id, wh.image_width, wh.image_height "
                + "from image i left join  image_size s on i.id = s.id"
                + "  left join image_width_height wh on i.id = wh.id"
                + " where i.id = ?";
        RowMapper<Image> rowMapper = (rs, rowNum) -> new Image(
                rs.getLong("image_id"),
                rs.getLong("session_id"),
                new ImageSize(rs.getLong("image_id"),rs.getInt("image_size")),
                ImageType.valueOf(rs.getString("image_type")),
                new ImageWidthHeight(rs.getLong("width_height_id"),rs.getInt("image_width"),rs.getInt("image_height")));
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
