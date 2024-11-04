package nextstep.courses.domain;

import java.util.Objects;
import nextstep.courses.exception.ImageHeightPixelException;
import nextstep.courses.exception.ImageWidthPixelException;

public class ImageWidthHeight {
    private static final int WIDTH_RATIO = 3;
    private static final int HEIGHT_RATIO = 2;
    private static final int MIN_WIDTH = WIDTH_RATIO * 100;
    private static final int MIN_HEIGHT = HEIGHT_RATIO * 100;

    private final Long id;
    private final int width;
    private final int height;

    public ImageWidthHeight(Long id,  int width, int height) {
        validWidthPixel(width);
        validHeightPixel(height);
        validWidthHeightRatio(width, height);
        this.id = id;
        this.width = width;
        this.height = height;
    }

    private void validWidthPixel(int width) {
        if (width < MIN_WIDTH) {
            throw new ImageWidthPixelException(width);
        }
    }

    private void validHeightPixel(int height) {
        if (height < MIN_HEIGHT) {
            throw new ImageHeightPixelException(height);
        }
    }

    private void validWidthHeightRatio(int width, int height) {
        if (WIDTH_RATIO * height != HEIGHT_RATIO * width) {
            throw new IllegalArgumentException("width 와 height의 비율이 3:2 이어야 합니다");
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Long getId() {
        return id;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageWidthHeight)) {
            return false;
        }

        ImageWidthHeight that = (ImageWidthHeight) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
