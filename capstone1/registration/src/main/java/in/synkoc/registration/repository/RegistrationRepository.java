package in.synkoc.registration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.synkoc.registration.model.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

	boolean existsByEventIdAndEmailIgnoreCase(Long eventId, String email);

	long countByEventId(Long eventId);

	List<Registration> findByEventIdOrderByIdAsc(Long eventId);
}
