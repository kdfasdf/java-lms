package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;
import nextstep.payments.domain.Payment;

public class Payments {

    private List<Payment> payments;

    public static Payments from() {
        List<Payment> payments = new ArrayList<>();
        return new Payments(payments);
    }

    private Payments(List<Payment> payments) {
        this.payments = payments;
    }

    public void addPaymentHistory(Payment payment) {
        payments.add(payment);
    }

    public int getNumberOfPayments() {
        return payments.size();
    }

    public boolean checkPaymentIsStored(Payment pay) {
        return payments.contains(pay);
    }
}
