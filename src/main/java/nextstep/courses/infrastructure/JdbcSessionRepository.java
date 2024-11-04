package nextstep.courses.infrastructure;

import java.util.List;
import nextstep.courses.domain.Payments;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionDuration;
import nextstep.courses.domain.SessionInfo;
import nextstep.courses.domain.SessionRegisterInfo;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.domain.SessionType;
import nextstep.courses.domain.Students;
import nextstep.payments.domain.Payment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcTemplate jdbcTemplate;
    public JdbcSessionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Session session) {
        SessionRegisterInfo sessionRegisterInfo = session.getSessionRegisterInfo();
        SessionInfo sessionInfo = session.getSessionInfo();
        SessionDuration sessionDuration = session.getSessionDuration();
        String sql = "insert into session (session_id) values (?);"
                +"insert into session_register_info (session_id,session_status) values (?,?);"
                +"insert into session_info (session_id, session_type, price, max_students) values (?,?,?,?);"
                +"insert into session_duration (session_id, start_date, end_date) values (?,?,?)";
        return jdbcTemplate.update(sql, session.getSessionId()
        ,session.getSessionId(),sessionRegisterInfo.getSessionStatus().name()
        ,session.getSessionId(),sessionInfo.getSessionType().name(),sessionInfo.getPrice(),sessionInfo.getMaxStudents()
        ,session.getSessionId(),sessionDuration.getStartDate(),sessionDuration.getEndDate());
    }

    @Override
    public Session findById(Long id) {
        SessionInfo sessionInfo = findByIdSessionInfo(id);
        SessionRegisterInfo sesssionRegisterInfo = findByIdSessionRegisterInfo(id);
        SessionDuration sessionDuration = findByIdSessionDuration(id);

        String sql = "select * from session where session_id = ?";
        RowMapper<Session> rowMapper = ((rs, rowNum)
                -> Session.createPaidSession(rs.getLong(1)
                , null, sessionInfo.getSessionType(),sesssionRegisterInfo.getSessionStatus()
        ,sessionInfo.getPrice(),  sessionInfo.getMaxStudents(), sessionDuration));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public SessionInfo findByIdSessionInfo(Long id) {
        String sql = "select * from session_info where session_id = ?";
        RowMapper<SessionInfo> rowMapper = ((rs, rowNum)
                -> new SessionInfo(rs.getLong(1)
                , SessionType.valueOf(rs.getString(2))
                , rs.getLong(3)
                ,rs.getInt(4)));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public SessionRegisterInfo findByIdSessionRegisterInfo(Long Id){
        String sql = "select * from session_register_info where session_id = ?;";
        String studentSql = "select user_id from students where session_id = ?;";
        String paymentSql = "select * from payments where session_id = ?;";
        RowMapper<String> studentMapper = (rs, rowNum) -> rs.getString(1);
        RowMapper<Payment> paymentsMapper = (rs, rowNum) ->
                new Payment(rs.getString(1), rs.getLong(2), rs.getLong(3), rs.getLong(4));
        List<String> students = jdbcTemplate.query(studentSql, studentMapper, Id);
        List<Payment> payments = jdbcTemplate.query(paymentSql, paymentsMapper, Id);
        RowMapper<SessionRegisterInfo> rowMapper =(rs, rowNum) -> new SessionRegisterInfo(
                rs.getLong(1), SessionStatus.valueOf(rs.getString(2)), Students.from(students), Payments.from(payments));
        return jdbcTemplate.queryForObject(sql, rowMapper, Id);
    }

    public SessionDuration findByIdSessionDuration(Long id) {
        String sql = "select * from session_duration where session_id = ?";
        RowMapper<SessionDuration> rowMapper = ((rs, rowNum)
                -> new SessionDuration(rs.getLong(1)
                , rs.getTimestamp(2).toLocalDateTime()
                , rs.getTimestamp(3).toLocalDateTime()));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

}
