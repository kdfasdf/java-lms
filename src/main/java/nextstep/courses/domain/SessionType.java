package nextstep.courses.domain;

import java.util.function.BiConsumer;

public enum SessionType {
    PAID((price, maxStudent) -> {
        validPaidSessionPriceIsNotZeroOrNegative(price);
        validMaxStudentOfPaidSession(maxStudent);
    }),
    FREE((price, maxStudent) -> {
        validFreeSessionIsFree(price);
        validMaxStudentIsIntegerMax(maxStudent);
    });

    private final BiConsumer<Long, Integer> validator;

    SessionType(BiConsumer<Long, Integer> validateRelatedType) {
        this.validator = validateRelatedType;
    }

    private static void validPaidSessionPriceIsNotZeroOrNegative(Long price) {
        if (price <= SessionType.Constants.FREE) {
            throw new IllegalArgumentException("유료강의의 강의는 1원 이상이어야 합니다.");
        }
    }

    private static void validMaxStudentOfPaidSession(Integer maxStudent) {
        if (maxStudent <= Constants.NO_STUDENT) {
            throw new IllegalArgumentException("유료 강의의 최대 수강인원은 0명 이하일 수 없습니다");
        }
    }

    private static void validFreeSessionIsFree(Long price) {
        if (price != 0L) {
            throw new IllegalArgumentException("무료 강의의 가격은 0원이어야 합니다");
        }
    }

    private static void validMaxStudentIsIntegerMax(Integer maxStudent) {
        if (maxStudent < Integer.MAX_VALUE) {
            throw new IllegalArgumentException("무료강의는 수강생 제한이 없어야 합니다.");
        }
    }

    public void validate(Long price, Integer maxStudentCount) {
        validator.accept(price, maxStudentCount);
    }

    public boolean isPaid() {
        return this == SessionType.PAID;
    }

    class Constants {
        public static final int FREE = 0;
        public static final int NO_STUDENT = 0;
    }

}
