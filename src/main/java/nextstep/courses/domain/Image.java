package nextstep.courses.domain;

import java.util.Objects;

public class Image {
    private final Long id;
    private final Long sessionId;
    private final ImageSize imageSize;
    private final ImageType imageType;
    private final ImageWidthHeight imageWidthHeight;

    public Image(Long id, Long sessionId, ImageSize imageSize, ImageType imageType, ImageWidthHeight imageWidthHeight) {
        this.id = id;
        this.sessionId = sessionId;
        this.imageSize = imageSize;
        this.imageType = imageType;
        this.imageWidthHeight = imageWidthHeight;
    }

    public Long getId() {
        return id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public ImageSize getImageSize() {
        return imageSize;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public ImageWidthHeight getImageWidthHeight() {
        return imageWidthHeight;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Image)) {
            return false;
        }

        Image image = (Image) o;
        return Objects.equals(id, image.id) && Objects.equals(sessionId, image.sessionId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(sessionId);
        return result;
    }
}
