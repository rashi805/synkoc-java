package in.synkoc.events;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EventExceptionsTest {

	@Test
	void eventNotFoundExceptionHasCompleteMessage() {
		assertThat(new EventNotFoundException(42L).getMessage()).isEqualTo("Event 42 was not found.");
	}

	@Test
	void duplicateEventExceptionHasCompleteMessage() {
		assertThat(new DuplicateEventException("Spring Meetup").getMessage())
				.isEqualTo("An event named \"Spring Meetup\" already exists.");
	}
}
