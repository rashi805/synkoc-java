package in.synkoc.events;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final List<Event> events = new ArrayList<>(List.of(
            new Event(1, "Hackathon", 120),
            new Event(2, "Robotics Workshop", 40),
            new Event(3, "Cultural Night", 300)
    ));

    private long nextId = 4;

    // Get all events or filter by minimum seats
    @GetMapping
    public List<Event> all(@RequestParam(required = false) Integer minSeats) {
        if (minSeats == null) {
            return events;
        }

        return events.stream()
                .filter(event -> event.seats() >= minSeats)
                .toList();
    }

    // Get one event by ID
    @GetMapping("/{id}")
    public ResponseEntity<Event> one(@PathVariable long id) {
        for (Event event : events) {
            if (event.id() == id) {
                return ResponseEntity.ok(event);
            }
        }

        return ResponseEntity.notFound().build();
    }

    // Create a new event with validation
    @PostMapping
    public ResponseEntity<?> create(@RequestBody EventRequest req) {

        if (req.name() == null || req.name().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Name is required"));
        }

        if (req.seats() == null || req.seats() < 1) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Seats must be at least 1"));
        }

        Event event = new Event(nextId++, req.name(), req.seats());
        events.add(event);

        return ResponseEntity.created(
                URI.create("/api/events/" + event.id())
        ).body(event);
    }

    // Delete an event by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        Iterator<Event> iterator = events.iterator();

        while (iterator.hasNext()) {
            Event event = iterator.next();

            if (event.id() == id) {
                iterator.remove();
                return ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.notFound().build();
    }
}