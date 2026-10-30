package in.synkoc.registration.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import in.synkoc.registration.model.Event;
import jakarta.persistence.LockModeType;

public interface EventRepository extends JpaRepository<Event, Long> {

	List<Event> findByDateGreaterThanEqualOrderByDateAsc(LocalDate date);

	List<Event> findAllByOrderByDateAsc();

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select event from Event event where event.id = :id")
	Optional<Event> findByIdForUpdate(@Param("id") Long id);
}
