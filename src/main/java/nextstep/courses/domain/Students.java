package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;
import nextstep.users.domain.NsUser;

public class Students {
    private final List<NsUser> students;

    public static Students from() {
        List<NsUser> students = new ArrayList<>();
        return new Students(students);
    }

    private Students(List<NsUser> students) {
        this.students = new ArrayList<>(students);
    }

    public void addStudent(NsUser nsUser) {
        checkUserAlreadyRegisterSession(nsUser);
        students.add(nsUser);
    }

    private void checkUserAlreadyRegisterSession(NsUser nsUser) {
        if (students.contains(nsUser)) {
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
