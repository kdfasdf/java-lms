package nextstep.courses.domain;

import nextstep.courses.exception.ImageSizeException;

public class ImageSize {
    private static final int IMAGE_SIZE_MAX = 1024 * 1024;
    private static final int IMAGE_SIZE_MIN = 1;

    private final int imageSize;

    public ImageSize(int imageSize) {
        validImageSize(imageSize);
        validSizeIsNegative(imageSize);
        this.imageSize = imageSize;
    }

    private void validSizeIsNegative(int imageSize) {
        if (imageSize < IMAGE_SIZE_MIN) {
            throw new IllegalArgumentException("이미지의 크기는 " + IMAGE_SIZE_MIN + "바이트 이상이어야 합니다.");
        }
    }

    private void validImageSize(int imageSize) {
        if (imageSize > IMAGE_SIZE_MAX) {
            throw new ImageSizeException(imageSize);
        }
    }
}
