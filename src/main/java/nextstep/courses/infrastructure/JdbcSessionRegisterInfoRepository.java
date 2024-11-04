package nextstep.courses.infrastructure;


import java.util.List;
import nextstep.courses.domain.Payments;
import nextstep.courses.domain.SessionRegisterInfo;
import nextstep.courses.domain.SessionRegisterInfoRepository;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.domain.Students;
import nextstep.payments.domain.Payment;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("sessionRepositoryInfoRepository")
public class JdbcSessionRegisterInfoRepository implements SessionRegisterInfoRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRegisterInfoRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(SessionRegisterInfo sessionRegisterInfo) {
        String sql = "insert into session_register_info (session_id,session_status) values (?,?);";
        return jdbcTemplate.update(sql, sessionRegisterInfo.getSessionId(), sessionRegisterInfo.getSessionStatus().name());
    }

    @Override
    public SessionRegisterInfo findById(Long Id){
        String sql = "select * from session_register_info where session_id = ?;";
        String studentSql = "select user_id from students where session_id = ?;";
        String paymentSql = "select * from payments where session_id = ?;";
        RowMapper<String> studentMapper = (rs, rowNum) -> rs.getString("user_id");
        RowMapper<Payment> paymentsMapper = (rs, rowNum) ->
                new Payment(rs.getString("user_id"), rs.getLong("session_id"),
                        rs.getLong("amount"), rs.getLong("create_at"));
        List<String> students = jdbcTemplate.query(studentSql, studentMapper, Id);
        List<Payment> payments = jdbcTemplate.query(paymentSql, paymentsMapper, Id);
        RowMapper<SessionRegisterInfo> rowMapper =(rs, rowNum) -> new SessionRegisterInfo(
                rs.getLong("session_id"), SessionStatus.valueOf(rs.getString("session_status")),
                Students.from(students), Payments.from(payments));
        return jdbcTemplate.queryForObject(sql, rowMapper, Id);
    }
}
