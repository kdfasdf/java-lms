package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import nextstep.users.domain.NsUser;

public class Students {
    private List<NsUser> students = List.of();

    public void addStudent(NsUser nsUser) {
        List<NsUser> students2 = new ArrayList<>(students);
        students2.add(nsUser);
        students = students2.stream()
                .collect(Collectors.toUnmodifiableList());
    }

    public int getNumberOfStudents() {
        return students.size();
    }
}
