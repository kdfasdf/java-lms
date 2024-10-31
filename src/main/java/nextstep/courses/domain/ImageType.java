package nextstep.courses.domain;

import java.util.Arrays;

public enum ImageType {
    GIF,
    JPG,
    JPEG,
    PNG,
    SVG;

    public static ImageType from(String imageType) {
        validImageTypeIsNull(imageType);
        return Arrays.stream(values())
                .filter(type -> type.toString().equalsIgnoreCase(imageType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 이미지 타입"));
    }

    private static void validImageTypeIsNull(String imageType) {
        if (imageType == null) {
            throw new IllegalArgumentException("imageType 오류 (null)");
        }
    }

}
