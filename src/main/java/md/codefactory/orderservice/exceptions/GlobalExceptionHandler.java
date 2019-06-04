package md.codefactory.orderservice.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String errorMessage = "errorMessage";

    @ExceptionHandler({NotEnoughRightsException.class})
    public ResponseEntity<Map<String, String>> foodNotFound(NotEnoughRightsException e) {
        Map<String, String> response = new HashMap<>();
        response.put(errorMessage, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({OrderNotFoundException.class})
    public ResponseEntity<Map<String, String>> foodNotFound(OrderNotFoundException e) {
        Map<String, String> response = new HashMap<>();
        response.put(errorMessage, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> error = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage));

        return new ResponseEntity<>(error, headers, status);
    }

}
