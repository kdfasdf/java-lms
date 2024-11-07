package nextstep.courses.domain;

public enum SessionRegisteringStatus {
    CLOSED,
    OPEN;

    public boolean isClosed() {
        if(this == CLOSED) {
            return true;
        }
        return false;
    }
}
