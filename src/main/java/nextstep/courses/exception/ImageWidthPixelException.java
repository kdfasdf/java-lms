package nextstep.courses.exception;

public class ImageWidthPixelException extends IllegalArgumentException {
    public ImageWidthPixelException(int widthPixel) {
        super(String.format("현재 이미지 가로 width 픽셀 : %d, 300픽셀 이상이어야 함", widthPixel));
    }
}
