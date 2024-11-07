package nextstep.courses.domain;

import java.util.Objects;

public class Student {
    private final String userId;
    private SelectStatus selectedStatus = SelectStatus.UNSELECTED;
    private final Long sessionId;


    public static Student selectedStudent(String studentId, Long sessionId) {
        return new Student(studentId,SelectStatus.SELECTED, sessionId);
    }

    public static Student unSelectedStudent(String studentId, Long sessionId) {
        return new Student(studentId, sessionId);
    }

    private Student(String userId, SelectStatus selectedStatus, Long sessionId) {
        this.userId = userId;
        this.selectedStatus = selectedStatus;
        this.sessionId = sessionId;
    }


    private Student(String userId, Long sessionId) {
        this.userId = userId;
        this.sessionId = sessionId;
    }

    public boolean isSelected() {
        return selectedStatus.isSelected();
    }

    public String getUserId() {
        return userId;
    }

    public SelectStatus getSelectedStatus() {
        return selectedStatus;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }

        Student student = (Student) o;
        return Objects.equals(userId, student.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }
}
