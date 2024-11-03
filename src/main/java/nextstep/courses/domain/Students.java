package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;
import nextstep.users.domain.NsUser;

public class Students {
    private final List<String> students;

    public static Students from() {
        List<String> students = new ArrayList<>();
        return new Students(students);
    }

    public static Students from(List<String> students) {
        return new Students(students);
    }

    private Students(List<String> students) {
        this.students = new ArrayList<>(students);
    }

    public void addStudent(String userId) {
        checkUserAlreadyRegisterSession(userId);
        students.add(userId);
    }

    private void checkUserAlreadyRegisterSession(String userId) {
        if (students.contains(userId)) {
            throw new IllegalArgumentException("이미 수업에 등록한 학생입니다");
        }
    }

    public int getNumberOfStudents() {
        return students.size();
    }

    public boolean getContainResult(NsUser nsUser) {
        return students.contains(nsUser);
    }
}
