package fi.mikko.strength.feature.lift;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LiftService {

  LiftDto create(final BasicLiftDto dto);

  LiftDto read(final long id);

  LiftDto update(final LiftDto dto);

  void delete(final long id);

  Page<LiftDto> list(final Pageable pageable);
}
