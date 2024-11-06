package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;

public class Students {
    private final List<Student> students;

    public static Students from() {
        List<Student> students = new ArrayList<>();
        return new Students(students);
    }

    public static Students from(List<Student> students) {
        return new Students(students);
    }

    private Students(List<Student> students) {
        this.students = new ArrayList<>(students);
    }

    @Deprecated
    public void addStudent(String userId) {
        checkUserAlreadyRegisterSession(userId);
        students.add(Student.UnSelectedStudent(userId));
    }

    public void addSelectedStudent(String userId) {
        checkUserAlreadyRegisterSession(userId);
        students.add(Student.SelectedStudent(userId));
    }

    public void addUnSelectedStudent(String userId) {
        checkUserAlreadyRegisterSession(userId);
        students.add(Student.UnSelectedStudent(userId));
    }

    private void checkUserAlreadyRegisterSession(String userId) {
        if (students.contains(userId)) {
            throw new IllegalArgumentException("이미 수업에 등록한 학생입니다");
        }
    }

    public int getNumberOfStudents() {
        return students.size();
    }

    public boolean getContainResult(String userId) {
        Student student = Student.SelectedStudent(userId);
        return students.contains(student);
    }
}
