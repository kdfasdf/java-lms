package nextstep.courses.domain;

import java.util.Objects;
import nextstep.courses.exception.ImageSizeException;

public class ImageSize {
    private static final int IMAGE_SIZE_MAX = 1024 * 1024;
    private static final int IMAGE_SIZE_MIN = 1;

    private final Long imageId;
    private final int imageSize;

    public ImageSize(Long imageId, int imageSize) {
        validImageSize(imageSize);
        validSizeIsNegative(imageSize);
        this.imageId = imageId;
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

    public Long getImageId() {
        return imageId;
    }

    public int getImageSize() {
        return imageSize;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageSize)) {
            return false;
        }

        ImageSize imageSize = (ImageSize) o;
        return Objects.equals(imageId, imageSize.imageId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(imageId);
    }
}
