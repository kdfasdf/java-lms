package nextstep.courses.domain;

import java.util.List;
import java.util.Optional;

public interface ImageRepository {
    int save(Image image);

    Optional<List<Image>> findById(Long id);
}
