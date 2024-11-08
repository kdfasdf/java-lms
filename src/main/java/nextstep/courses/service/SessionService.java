package nextstep.courses.service;

import javax.annotation.Resource;
import nextstep.courses.domain.SelectStatus;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.exception.NotFoundException;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("sessionService")
public class SessionService {
    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;

    @Transactional
    public void register(NsUser loginUser, Long sessionId, SelectStatus selectStatus) throws NotFoundException {
        Session session = sessionRepository.findById(sessionId);
        registerStudentWhenSessionIsOpen(session, loginUser, selectStatus);
    }

    private static void registerStudentWhenSessionIsOpen(Session session, NsUser loginUser, SelectStatus selectStatus) {
        if(session.isRegistering()) {
            session.addStudentBySelectedStatus(loginUser,selectStatus);
        }
    }
}
