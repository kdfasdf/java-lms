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

@Repository("ImageRepository")
public class JdbcImageRepository implements ImageRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Image image) {
        String sql = "insert into image (id,image_type,session_id) values(?, ?, ?);"
                + "insert into image_size (session_id,id, image_size) values (?, ?, ?);"
                +"insert into image_width_height(id, session_id, image_width, image_height) values (?, ?, ?, ?);";
        return jdbcTemplate.update(sql, image.getId(), image.getImageType().name(), image.getSessionId()
        ,image.getSessionId(), image.getId(), image.getImageSize().getImageSize()
        ,image.getId(), image.getSessionId(), image.getImageWidthHeight().getWidth(), image.getImageWidthHeight().getHeight());
    }

    @Override
    public Optional<Image> findById(Long id) {
        String sql = "SELECT\n"
                + "        i.id AS image_id,\n"
                + "                i.image_type,\n"
                + "                i.session_id,\n"
                + "                s.id AS size_id,\n"
                + "        s.image_size,\n"
                + "                wh.id AS width_height_id,\n"
                + "        wh.image_width,\n"
                + "                wh.image_height\n"
                + "        FROM\n"
                + "        image i\n"
                + "        LEFT JOIN\n"
                + "        image_size s ON i.id = s.id\n"
                + "        LEFT JOIN\n"
                + "        image_width_height wh ON i.id = wh.id"
                + " WHERE i.id = ?";
        RowMapper<Image> rowMapper = (rs, rowNum) -> new Image(
                rs.getLong(1),
                rs.getLong(3),
                new ImageSize(rs.getLong(3),rs.getLong(1),rs.getInt(4)),
                ImageType.valueOf(rs.getString(2)),
                new ImageWidthHeight(rs.getLong(1),rs.getLong(3),rs.getInt(7),rs.getInt(8)));
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
