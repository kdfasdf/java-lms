package nextstep.courses.domain;

public class Image {
    private final ImageSize imageSize;
    private final ImageType imageType;
    private final ImageWidthHeight imageWidthHeight;

    public Image(ImageSize imageSize, ImageType imageType, ImageWidthHeight imageWidthHeight) {
        this.imageSize = imageSize;
        this.imageType = imageType;
        this.imageWidthHeight = imageWidthHeight;
    }

}
