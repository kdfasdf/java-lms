package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Students cancelRegisterWhoUnselected() {
        return new Students(
                students.stream()
                    .filter(Student::isSelected)
                    .collect(Collectors.toList()));
    }



    public void addSelectedStudent(String userId, Long sessionId) {
        checkUserAlreadyRegisterSession(userId);
        students.add(Student.selectedStudent(userId, sessionId));
    }

    public void addUnSelectedStudent(String userId, Long sessionId) {
        checkUserAlreadyRegisterSession(userId);
        students.add(Student.unSelectedStudent(userId, sessionId));
    }

    private void checkUserAlreadyRegisterSession(String userId) {
        if (students.contains(userId)) {
            throw new IllegalArgumentException("이미 수업에 등록한 학생입니다");
        }
    }

    public int getNumberOfStudents() {
        return students.size();
    }

    public boolean getContainResult(String userId, Long sessionId) {
        Student student = Student.selectedStudent(userId, sessionId);
        return students.contains(student);
    }
}
