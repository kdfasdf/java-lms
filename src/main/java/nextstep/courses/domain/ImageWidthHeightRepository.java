package nextstep.courses.domain;

public interface ImageWidthHeightRepository {
    int save(ImageWidthHeight imageWidthHeight);

    ImageWidthHeight findById(Long id);
}
