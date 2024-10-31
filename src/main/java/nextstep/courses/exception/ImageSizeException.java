package nextstep.courses.exception;

public class ImageSizeException extends IllegalArgumentException {
    public ImageSizeException(int imageSize) {
        super(String.format("현재 이미지 사이즈 %d 1MB를 초과합니다", imageSize));
    }
}
