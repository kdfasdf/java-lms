package nextstep.courses.domain;

public interface SessionInfoRepository {
    int save(SessionInfo sessionInfo);

    SessionInfo findById(Long id);
}
