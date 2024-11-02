package nextstep.courses.domain;

public interface ImageSizeRepository {
    int save(ImageSize imageSize);

    ImageSize findById(Long id);
}
