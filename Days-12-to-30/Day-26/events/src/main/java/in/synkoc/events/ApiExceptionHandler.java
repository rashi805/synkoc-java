package in.synkoc.events;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(EventNotFoundException.class)
	public ResponseEntity<ProblemDetail> eventNotFound(EventNotFoundException exception) {
		return problem(HttpStatus.NOT_FOUND, "Event not found", exception.getMessage());
	}

	@ExceptionHandler(DuplicateEventException.class)
	public ResponseEntity<ProblemDetail> duplicateEvent(DuplicateEventException exception) {
		return problem(HttpStatus.CONFLICT, "Duplicate event", exception.getMessage());
	}

	@ExceptionHandler(InvalidSearchException.class)
	public ResponseEntity<ProblemDetail> invalidSearch(InvalidSearchException exception) {
		return problem(HttpStatus.BAD_REQUEST, "Invalid search", exception.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ProblemDetail> validation(MethodArgumentNotValidException exception) {
		ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
				"One or more fields are invalid.");
		detail.setTitle("Validation failed");
		Map<String, String> errors = new LinkedHashMap<>();
		exception.getBindingResult().getFieldErrors().forEach(error ->
				errors.putIfAbsent(error.getField(), error.getDefaultMessage()));
		detail.setProperty("errors", errors);
		return response(HttpStatus.BAD_REQUEST, detail);
	}

	@ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class,
			MissingServletRequestParameterException.class})
	public ResponseEntity<ProblemDetail> malformedRequest(Exception exception) {
		return problem(HttpStatus.BAD_REQUEST, "Invalid request", "The request contains invalid or missing values.");
	}

	private ResponseEntity<ProblemDetail> problem(HttpStatus status, String title, String detail) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
		problem.setTitle(title);
		return response(status, problem);
	}

	private ResponseEntity<ProblemDetail> response(HttpStatus status, ProblemDetail problem) {
		return ResponseEntity.status(status).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(problem);
	}
}
