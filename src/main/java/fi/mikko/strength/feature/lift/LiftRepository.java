package fi.mikko.strength.feature.lift;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface LiftRepository extends JpaRepository<Lift, Long> {

}
