package nextstep.courses.exception;

public class ImageHeightPixelException extends IllegalArgumentException {
    private static final int MIN_HEIGHT = 200;
    public ImageHeightPixelException(int heightPixel) {
        super(String.format("현재 이미지 가로 height 픽셀 : %d, " + MIN_HEIGHT + "픽셀 이상이어야 함", heightPixel));
    }
}
