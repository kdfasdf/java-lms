package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionDuration;
import nextstep.courses.domain.SessionDurationRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("sessionDurationRepository")
public class JdbcSessionDurationRepository implements SessionDurationRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionDurationRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(SessionDuration sessionDuration) {
        String sql = "insert into session_duration (session_id, start_date, end_date) values (?,?,?)";
        return jdbcTemplate.update(sql, sessionDuration.getSessionId(), sessionDuration.getStartDate(), sessionDuration.getEndDate());
    }

    @Override
    public SessionDuration findById(Long id) {
        String sql = "select * from session_duration where session_id = ?";
        RowMapper<SessionDuration> rowMapper = ((rs, rowNum)
        -> new SessionDuration(rs.getLong("session_id")
        , rs.getTimestamp("start_date").toLocalDateTime()
        , rs.getTimestamp("end_date").toLocalDateTime()));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

}
