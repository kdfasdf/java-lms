package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import nextstep.payments.domain.Payment;

public class Payments {

    private List<Payment> payments;

    public static Payments from() {
        List<Payment> payments = new ArrayList<>();
        return new Payments(payments);
    }
    public static Payments from(List<Payment> payments) {
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

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payments)) {
            return false;
        }

        Payments payments1 = (Payments) o;
        return Objects.equals(payments, payments1.payments);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(payments);
    }
}
