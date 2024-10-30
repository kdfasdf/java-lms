package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class Payments {

    private List<Payment> payments = List.of();

    public void addPaymentHistory(NsUser nsUser, long payment, Long sessionId) {
        List<Payment> payments2 = new ArrayList<>(payments);
        payments2.add(new Payment("", sessionId, nsUser.getId(), payment));
        payments = payments2.stream()
                .collect(Collectors.toUnmodifiableList());
    }

    public int getNumberOfPayments() {
        return payments.size();
    }

}
