package in.synkoc.registration.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.synkoc.registration.exception.EventNotFoundException;

@RestControllerAdvice(basePackageClasses = EventApiController.class)
public class RestExceptionHandler {

	@ExceptionHandler(EventNotFoundException.class)
	public ResponseEntity<ProblemDetail> handleEventNotFound(EventNotFoundException exception) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
		problem.setTitle("Event not found");
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.contentType(MediaType.APPLICATION_PROBLEM_JSON)
				.body(problem);
	}
}
