package nextstep.courses.domain;

import java.util.Objects;

public class Student {
    private final String nsUserId;
    private SelectStatus selectedStatus = SelectStatus.UNSELECTED;
    private final Long sessionId;


    public static Student selectedStudent(String studentId, Long sessionId) {
        return new Student(studentId,SelectStatus.SELECTED, sessionId);
    }

    public static Student unSelectedStudent(String studentId, Long sessionId) {
        return new Student(studentId, sessionId);
    }

    private Student(String nsUserId, SelectStatus selectedStatus, Long sessionId) {
        this.nsUserId = nsUserId;
        this.selectedStatus = selectedStatus;
        this.sessionId = sessionId;
    }


    private Student(String nsUserId, Long sessionId) {
        this.nsUserId = nsUserId;
        this.sessionId = sessionId;
    }

    public boolean isSelected() {
        return selectedStatus.isSelected();
    }

    public String getNsUserId() {
        return nsUserId;
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
        return Objects.equals(nsUserId, student.nsUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nsUserId);
    }
}
