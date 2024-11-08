package nextstep.courses.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import nextstep.courses.domain.Image;
import nextstep.courses.domain.ImageSize;
import nextstep.courses.domain.ImageType;
import nextstep.courses.domain.ImageWidthHeight;
import nextstep.courses.domain.SelectStatus;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionDuration;
import nextstep.courses.domain.SessionRegisteringStatus;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.domain.SessionType;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {
    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private SessionService sessionService;

    private Session session;

    @BeforeEach
    public void setUp() throws Exception {
        Image image = new Image(1L , 0L,
                new ImageSize(0L, 100), ImageType.JPEG, new ImageWidthHeight(0L,600, 400));


        SessionDuration sessionDuration = new SessionDuration(0L, LocalDateTime.now(),LocalDateTime.now().plusMinutes(1));

        session =Session.createPaidSession(0L, image, SessionType.PAID,SessionStatus.REGISTER,10000L,100,sessionDuration,SessionRegisteringStatus.OPEN);
        sessionRepository.save(session);
    }

    @Test
    public void register_성공() throws Exception {
        when(sessionRepository.findById(session.getSessionId())).thenReturn(session);

        assertThatCode(
                () ->
                sessionService.register(NsUserTest.SANJIGI, session.getSessionId(), SelectStatus.SELECTED));
    }
}
