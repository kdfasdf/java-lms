package nextstep.courses.domain;

public enum ImageType {
    GIF,
    JPG,
    JPEG,
    PNG,
    SVG;

    public static ImageType typeCheck(String imageType) {
        validImageTypeIsNull(imageType);
        try {
            return ImageType.valueOf(imageType);
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("지원하지 않는 이미지 타입");
        }
    }

    private static void validImageTypeIsNull(String imageType) {
        if(imageType == null) {
            throw new IllegalArgumentException("imageType 오류 (null)");
        }
    }

}
