package nextstep.qna.domain;

import java.time.LocalDateTime;
import nextstep.users.domain.NsUser;

public class QuestionIdentificationInfo {
    private Long id;
    private NsUser writer;
    private LocalDateTime createdDate = LocalDateTime.now();

    public QuestionIdentificationInfo(Long id, NsUser writer) {
        this.id = id;
        this.writer = writer;
    }

    public Long getId() {
        return new Long(id);
    }

    public NsUser getWriter() {
        return writer;
    }

    boolean isOwner(NsUser nsUser) {
        return writer.equals(nsUser);
    }
}
