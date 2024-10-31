package nextstep.courses.exception;

import nextstep.courses.domain.ImageWidthHeight;

public class ImageHeightPixelException extends IllegalArgumentException {
    public ImageHeightPixelException(int heightPixel) {
        super(String.format("현재 이미지 가로 height 픽셀 : %d, " + ImageWidthHeight.MIN_HEIGHT + "픽셀 이상이어야 함", heightPixel));
    }
}
