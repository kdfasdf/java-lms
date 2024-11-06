package nextstep.courses.domain;

import java.util.Objects;

public class Student {
    private final String studentId;
    private SelectStatus selectedStatus = SelectStatus.UNSELECTED;

    public static Student SelectedStudent(String studentId) {
        return new Student(studentId,SelectStatus.SELECTED);
    }

    public static Student UnSelectedStudent(String studentId) {
        return new Student(studentId);
    }

    private Student(String studentId, SelectStatus selectedStatus) {
        this.studentId = studentId;
        this.selectedStatus = selectedStatus;
    }


    private Student(String studentId) {
        this.studentId = studentId;
    }

    public boolean isSelected() {
        if(selectedStatus == SelectStatus.SELECTED) {
            return true;
        }
        return false;
    }

    public String getStudentId() {
        return studentId;
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
        return Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(studentId);
    }
}
