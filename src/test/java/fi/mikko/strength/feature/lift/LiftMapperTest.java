package fi.mikko.strength.feature.lift;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fi.mikko.strength.config.ModelMapperConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LiftMapperTest {

  private static final long LIFT_ID = 1001l;
  private static final String LIFT_NAME = "Deadlift";

  private LiftMapper mapper;

  @BeforeEach
  public void setup() {
    ModelMapperConfig config = new ModelMapperConfig();
    this.mapper = new LiftMapper(config.modelMapper());
  }

  @Test
  public void mapModelToDto() {
    final Lift lift = Lift.builder()
        .id(LIFT_ID)
        .name(LIFT_NAME)
        .build();
    final LiftDto dto = mapper.map(lift);
    assertEquals(LIFT_ID, dto.getId());
    assertEquals(LIFT_NAME, dto.getName());
  }

  @Test
  public void mapDtoToModel() {
    final LiftDto dto = LiftDto.builder()
        .id(LIFT_ID)
        .name(LIFT_NAME)
        .build();
    final Lift lift = mapper.map(dto);
    assertEquals(LIFT_ID, lift.getId());
    assertEquals(LIFT_NAME, lift.getName());
  }

}
