package nextstep.courses.domain;

import java.util.Objects;

public class Student {
    private final String userId;
    private SelectStatus selectedStatus = SelectStatus.UNSELECTED;


    public static Student selectedStudent(String studentId) {
        return new Student(studentId,SelectStatus.SELECTED);
    }

    public static Student unSelectedStudent(String studentId) {
        return new Student(studentId);
    }

    private Student(String userId, SelectStatus selectedStatus) {
        this.userId = userId;
        this.selectedStatus = selectedStatus;
    }


    private Student(String userId) {
        this.userId = userId;
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
