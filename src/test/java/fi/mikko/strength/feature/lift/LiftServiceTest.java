package fi.mikko.strength.feature.lift;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import fi.mikko.strength.config.ModelMapperConfig;
import fi.mikko.strength.exception.NotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LiftServiceTest {

  private static final long LIFT_ID = 1001l;
  private static final String LIFT_NAME = "Deadlift";

  private LiftService service;
  private LiftMapper mapper;
  @Mock
  private LiftRepository repository;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    final ModelMapperConfig config = new ModelMapperConfig();
    mapper = new LiftMapper(config.modelMapper());
    service = new LiftServiceImpl(repository, mapper);
  }

  @Test
  public void createLift() {
    final Lift lift = Lift.builder()
        .id(LIFT_ID)
        .name(LIFT_NAME)
        .build();
    when(repository.save(any(Lift.class))).thenReturn(lift);

    final BasicLiftDto dto = new BasicLiftDto(LIFT_NAME);
    final LiftDto createdLift = service.create(dto);
    assertEquals(LIFT_ID, createdLift.getId());
    assertEquals(LIFT_NAME, createdLift.getName());
  }

  @Test
  public void readLift() {
    final Lift lift = Lift.builder()
        .id(LIFT_ID)
        .name(LIFT_NAME)
        .build();
    when(repository.findById(eq(LIFT_ID))).thenReturn(Optional.of(lift));

    final LiftDto dto = service.read(LIFT_ID);
    assertEquals(LIFT_ID, dto.getId());
    assertEquals(LIFT_NAME, dto.getName());
  }

  @Test
  public void readingNonExistingLiftThrowsNotFoundException() {
    when(repository.findById(any())).thenReturn(Optional.ofNullable(null));

    final Exception exception = assertThrows(NotFoundException.class, () -> {
      service.read(LIFT_ID);
    });

    assertTrue(exception.getMessage().contains(Long.toString(LIFT_ID)));
  }

  @Test
  public void updateLift() {
    final Lift lift = Lift.builder()
        .id(LIFT_ID)
        .name(LIFT_NAME)
        .build();
    when(repository.save(any(Lift.class))).thenReturn(lift);
    when(repository.existsById(eq(LIFT_ID))).thenReturn(true);

    final LiftDto dto = LiftDto.builder()
        .id(LIFT_ID)
        .name(LIFT_NAME)
        .build();
    final LiftDto updatedLift = service.update(dto);
    assertEquals(LIFT_ID, updatedLift.getId());
    assertEquals(LIFT_NAME, updatedLift.getName());
  }

  @Test
  public void updatingNonExistingLiftThrowsNotFoundException() {
    when(repository.existsById(eq(LIFT_ID))).thenReturn(false);
    final LiftDto dto = LiftDto.builder()
        .id(LIFT_ID)
        .name(LIFT_NAME)
        .build();

    final Exception exception = assertThrows(NotFoundException.class, () -> {
      service.update(dto);
    });

    assertTrue(exception.getMessage().contains(Long.toString(LIFT_ID)));
  }
}
