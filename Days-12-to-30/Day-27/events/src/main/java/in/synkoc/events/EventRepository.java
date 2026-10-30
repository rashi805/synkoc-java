package in.synkoc.events;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Long> {

	List<Event> findByCityIgnoreCase(String city);

	List<Event> findBySeatsGreaterThanEqual(int minimumSeats);

	boolean existsByNameIgnoreCase(String name);

	boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

	List<Event> findByNameContainingIgnoreCaseOrderByNameAsc(String name);

	@Query("""
			select new in.synkoc.events.CityStats(event.city, count(event), sum(event.seats))
			from Event event
			group by event.city
			""")
	List<CityStats> findCityStats();
}
