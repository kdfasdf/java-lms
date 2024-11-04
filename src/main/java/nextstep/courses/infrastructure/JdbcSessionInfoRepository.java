package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionInfo;
import nextstep.courses.domain.SessionInfoRepository;
import nextstep.courses.domain.SessionType;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("sessionInfoRepository")
public class JdbcSessionInfoRepository implements SessionInfoRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionInfoRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(SessionInfo sessionInfo) {
        String sql = "insert into session_info (session_id, session_type, price, max_students) values (?,?,?,?)";
        return jdbcTemplate.update(sql, sessionInfo.getSessionId(), sessionInfo.getSessionType().name(),
                sessionInfo.getPrice(),sessionInfo.getMaxStudents());
    }

    @Override
    public SessionInfo findById(Long id) {
        String sql = "select * from session_info where session_id = ?";
        RowMapper<SessionInfo> rowMapper = ((rs, rowNum)
                -> new SessionInfo(rs.getLong("session_id")
                , SessionType.valueOf(rs.getString("session_type"))
                , rs.getLong("price")
        ,rs.getInt("max_students")));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
}
