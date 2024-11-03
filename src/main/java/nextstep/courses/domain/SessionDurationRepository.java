package nextstep.courses.domain;

public interface SessionDurationRepository {
    int save(SessionDuration sessionDuration);

    SessionDuration findById(Long id);
}
