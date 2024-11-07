package nextstep.courses.domain;

public enum SessionRegisteringStatus {
    CLOSED,
    OPEN;

    public boolean isClosed() {
        return this == CLOSED;
    }

    public boolean isOpen() {
        return this == OPEN;
    }
}
