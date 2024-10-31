package nextstep.courses.domain;

import nextstep.courses.exception.ImageHeightPixelException;
import nextstep.courses.exception.ImageWidthPixelException;

public class ImageWidthHeight {
    public static final int MIN_WIDTH = 300;
    public static final int MIN_HEIGHT = 200;
    public static final double WIDTH_HEIGHT_RATIO = 1.5;

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
        if ((double) width / height != WIDTH_HEIGHT_RATIO) {
            throw new IllegalArgumentException("width 와 height의 비율이 3:2 이어야 합니다");
        }
    }

}
