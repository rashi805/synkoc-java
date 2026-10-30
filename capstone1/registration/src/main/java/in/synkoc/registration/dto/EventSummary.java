package in.synkoc.registration.dto;

import java.time.LocalDate;

public record EventSummary(Long id, String title, LocalDate date, String venue, int capacity, long seatsLeft) {
}
