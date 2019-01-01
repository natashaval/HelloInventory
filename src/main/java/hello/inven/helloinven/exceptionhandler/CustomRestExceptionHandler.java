package hello.inven.helloinven.exceptionhandler;

import hello.inven.helloinven.response.ResponseError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    // missing request parameter
    @Override
    protected  ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request){
        String error = ex.getParameterName() + "parameter is missing";
        ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // method argument type mismatch (require Integer, then give String)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request){
        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
        ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // send request with unsupported HTTP method (only have GetMapping, but request for DeleteMapping)
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
        ResponseError responseError = new ResponseError(HttpStatus.METHOD_NOT_ALLOWED,
                ex.getLocalizedMessage(), builder.toString());
        return new ResponseEntity<Object>(
                responseError, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

//    @ExceptionHandler({Exception.class})
//    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request){
//        ResponseError responseError = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occured!");
//        return new ResponseEntity<Object>(responseError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    /* ============ EXCEPTION HANDLER FOR SERVICE HELLOINVEN ==========*/
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request){
        ResponseError responseError = new ResponseError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }






























}
