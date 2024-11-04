package nextstep.courses.domain;

import java.util.Optional;

public interface ImageRepository {
    int save(Image image);

    Optional<Image> findById(Long id);
}
