package fi.mikko.strength.api;

import static fi.mikko.strength.api.ApiConstants.DEFAULT_PAGE_NUMBER;
import static fi.mikko.strength.api.ApiConstants.DEFAULT_PAGE_SIZE;

import fi.mikko.strength.feature.lift.BasicLiftDto;
import fi.mikko.strength.feature.lift.LiftDto;
import fi.mikko.strength.feature.lift.LiftService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/lifts")
public class LiftController {

  private final LiftService liftService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<LiftDto> create(@RequestBody @Validated BasicLiftDto dto) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(liftService.create(dto));
  }

  @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<LiftDto> read(@PathVariable long id) {
    return ResponseEntity.ok(liftService.read(id));
  }

  @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<LiftDto> update(
      @PathVariable long id,
      @RequestBody @Validated LiftDto dto) {
    dto.setId(id);
    return ResponseEntity.ok(liftService.update(dto));
  }

  @DeleteMapping(value = "{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> delete(@PathVariable long id) {
    liftService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<LiftDto>> list(
      @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = true) int pageNumber,
      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = true) int pageSize,
      @RequestParam(required = false) String sortField,
      @RequestParam(defaultValue = "ASC", required = false) Sort.Direction direction) {

    if (sortField != null) {
      return ResponseEntity.ok(liftService.list(
          PageRequest.of(pageNumber, pageSize, direction, sortField)));
    } else {
      return ResponseEntity.ok(liftService.list(PageRequest.of(pageNumber, pageSize)));
    }
  }
}
