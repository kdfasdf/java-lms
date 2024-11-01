package nextstep.courses.exception;

public class ImageWidthPixelException extends IllegalArgumentException {
    private static final int widthPixel = 300;
    public ImageWidthPixelException(int widthPixel) {
        super(String.format("현재 이미지 가로 width 픽셀 : %d, "+widthPixel+"픽셀 이상이어야 함", widthPixel));
    }
}
