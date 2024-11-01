package nextstep.courses.domain;

import java.util.Arrays;

public enum ImageType {
    GIF,
    JPG,
    JPEG,
    PNG,
    SVG;

    public static ImageType from(String imageType) {
        return Arrays.stream(values())
                .filter(type -> type.toString().equalsIgnoreCase(imageType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 이미지 타입"));
    }

}
