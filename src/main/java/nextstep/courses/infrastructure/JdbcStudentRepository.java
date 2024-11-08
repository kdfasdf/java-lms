package nextstep.courses.infrastructure;

import java.util.List;
import nextstep.courses.domain.SessionRegisterInfo;
import nextstep.courses.domain.Student;
import nextstep.courses.domain.StudentRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("studentRepository")
public class JdbcStudentRepository implements StudentRepository {
    private JdbcTemplate jdbcTemplate;
    public JdbcStudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(SessionRegisterInfo sessionRegisterInfo, Student student) {
        Long id = sessionRegisterInfo.getSessionId();

        String sql = "insert into students (session_id, user_id, selected_status) "
                + "values (?,?,?);";
        return jdbcTemplate.update(sql, id, student.getNsUserId(),
               student.getSelectedStatus().name());
    }

    @Override
    public List<Student> findById(Long id) {
        List<Student> selected = findByIdSelectedStudent(id);
        List<Student> unSelected = findByIdUnSelectedStudent(id);
        selected.addAll(unSelected);
        return selected;
    }

    public List<Student> findByIdSelectedStudent(Long id) {
        String sql = "select * from students where session_id = ? and selected_status = 'SELECTED'";
        RowMapper<Student> rowMapper = ((rs, rowNum)
                -> Student.selectedStudent(rs.getString("user_id"),rs.getLong("session_id")));
        return jdbcTemplate.query(sql, rowMapper, id);
    }

    public List<Student> findByIdUnSelectedStudent(Long id) {
        String sql = "select * from students where session_id = ? and selected_status = 'UNSELECTED'";
        RowMapper<Student> rowMapper = ((rs, rowNum)
                -> Student.selectedStudent(rs.getString("user_id"),rs.getLong("session_id")));
        return jdbcTemplate.query(sql, rowMapper, id);
    }

    public int deleteUnselectedStudent(Long id) {
        String sql = "delete from students where session_id = ? and selected_status = 'UNSELECTED'";
        return jdbcTemplate.update(sql, id);
    }

}
