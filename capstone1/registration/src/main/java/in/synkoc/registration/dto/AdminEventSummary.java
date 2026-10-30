package in.synkoc.registration.dto;

import java.time.LocalDate;

public record AdminEventSummary(Long id, String title, LocalDate date, long registeredCount, int capacity) {
}
