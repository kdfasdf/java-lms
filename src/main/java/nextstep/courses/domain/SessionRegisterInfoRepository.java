package nextstep.courses.domain;

public interface SessionRegisterInfoRepository {
    int save(SessionRegisterInfo sessionRegisterInfo);

    SessionRegisterInfo findById(Long Id);
}
