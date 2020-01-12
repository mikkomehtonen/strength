package fi.mikko.strength.feature.lift;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
class LiftMapper {

  private final ModelMapper modelMapper;

  public LiftDto map(final Lift lift) {
    return modelMapper.map(lift, LiftDto.class);
  }

  public Lift map(final LiftDto dto) {
    return modelMapper.map(dto, Lift.class);
  }

  public Lift map(final BasicLiftDto dto) {
    return modelMapper.map(dto, Lift.class);
  }
}
