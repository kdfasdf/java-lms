package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Question {

    private String title;

    private String contents;

    private Answers answers = new Answers(new ArrayList<>());

    private boolean deleted = false;

    private LocalDateTime updatedDate;

    private QuestionIdentificationInfo questionIdentificationInfo;

    public Question() {
    }

    public Question(NsUser writer, String title, String contents) {
        this(0L, writer, title, contents);
    }

    public Question(Long id, NsUser writer, String title, String contents) {
        this.title = title;
        this.contents = contents;
        this.questionIdentificationInfo = new QuestionIdentificationInfo(id, writer);
    }

    public Long getId() {
        return questionIdentificationInfo.getId();
    }

    public String getTitle() {
        return title;
    }

    public Question setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContents() {
        return contents;
    }

    public Question setContents(String contents) {
        this.contents = contents;
        return this;
    }

    public NsUser getWriter() {
        return questionIdentificationInfo.getWriter();
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        answers.add(answer);
    }

    public boolean isOwner(NsUser loginUser) {
        return questionIdentificationInfo.isOwner(loginUser);
    }

    public Question setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Answers getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        return "Question [id=" + getId() + ", title=" + title + ", contents=" + contents + ", writer=" + this.getWriter() + "]";
    }

    public void validIfUserCanDeletePost(NsUser nsUser) throws CannotDeleteException {
        if (!this.isOwner(nsUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
    }

    public List<DeleteHistory> delete(NsUser nsUser) throws CannotDeleteException {
        validIfUserCanDeletePost(nsUser);
        return answers.delete(nsUser, saveQuestionDeleteHistory());
    }

    public List<DeleteHistory> saveQuestionDeleteHistory() {
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        this.setDeleted(true);
        deleteHistories.add(
                new DeleteHistory(ContentType.QUESTION, this.getId(), this.getWriter(), LocalDateTime.now()));
        return deleteHistories;
    }

}
