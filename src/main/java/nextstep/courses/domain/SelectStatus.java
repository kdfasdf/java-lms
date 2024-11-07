package nextstep.courses.domain;

public enum SelectStatus {
    SELECTED,
    UNSELECTED;

    public boolean isSelected() {
        return this == SELECTED;
    }
}
