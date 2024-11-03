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

@Repository("SessionRepositoryInfoRepository")
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
        RowMapper<String> studentMapper = (rs, rowNum) -> rs.getString(1);
        RowMapper<Payment> paymentsMapper = (rs, rowNum) ->
                new Payment(rs.getString(1), rs.getLong(2), rs.getLong(3), rs.getLong(4));
        List<String> students = jdbcTemplate.query(studentSql, studentMapper, Id);
        List<Payment> payments = jdbcTemplate.query(paymentSql, paymentsMapper, Id);
        RowMapper<SessionRegisterInfo> rowMapper =(rs, rowNum) -> new SessionRegisterInfo(
                rs.getLong(1), SessionStatus.valueOf(rs.getString(2)), Students.from(students), Payments.from(payments));
        return jdbcTemplate.queryForObject(sql, rowMapper, Id);
    }
}
