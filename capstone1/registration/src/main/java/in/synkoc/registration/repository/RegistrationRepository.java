package in.synkoc.registration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.synkoc.registration.model.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

	boolean existsByEvent_IdAndEmailIgnoreCase(Long eventId, String email);

	long countByEvent_Id(Long eventId);

	List<Registration> findByEvent_IdOrderByIdAsc(Long eventId);
}
