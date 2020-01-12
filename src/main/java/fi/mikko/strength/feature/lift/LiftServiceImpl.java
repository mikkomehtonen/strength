package fi.mikko.strength.feature.lift;

import fi.mikko.strength.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
class LiftServiceImpl implements LiftService {

  private static final String NOT_FOUND_ERROR = "Lift not found with id %d";

  private final LiftRepository repository;
  private final LiftMapper mapper;

  @Override
  public LiftDto create(final BasicLiftDto dto) {
    return mapper.map(
        repository.save(
            mapper.map(dto)));
  }

  @Override
  public LiftDto read(final long id) {
    return repository.findById(id)
        .map(mapper::map)
        .orElseThrow(() -> createNotFound(id));
  }

  @Override
  public LiftDto update(final LiftDto dto) {
    if (!repository.existsById(dto.getId())) {
      throwNotFound(dto.getId());
    }
    return mapper.map(repository.save(mapper.map(dto)));
  }

  @Override
  public void delete(final long id) {
    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throwNotFound(id);
    }
  }

  @Override
  public Page<LiftDto> list(final Pageable pageable) {
    return repository.findAll(pageable).map(mapper::map);
  }

  private void throwNotFound(final long id) {
    throw createNotFound(id);
  }

  private NotFoundException createNotFound(final long id) {
    return new NotFoundException(String.format(NOT_FOUND_ERROR, id));
  }
}
