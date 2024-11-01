package nextstep.courses.domain;

import nextstep.courses.exception.ImageHeightPixelException;
import nextstep.courses.exception.ImageWidthPixelException;

public class ImageWidthHeight {
    private static final int WIDTH_RATIO = 3;
    private static final int HEIGHT_RATIO = 2;
    private static final int MIN_WIDTH = WIDTH_RATIO * 100;
    private static final int MIN_HEIGHT = HEIGHT_RATIO * 100;

    private final int width;
    private final int height;

    public ImageWidthHeight(int width, int height) {
        validWidthPixel(width);
        validHeightPixel(height);
        validWidthHeightRatio(width, height);
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

}
