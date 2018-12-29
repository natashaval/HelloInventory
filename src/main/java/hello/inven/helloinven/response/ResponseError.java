package hello.inven.helloinven.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter @Setter

//https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
// Custom Error Message
public class ResponseError {
    private HttpStatus status;
    private Date timestamp;
    private String message;
    private List<String> errors;

    public ResponseError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.timestamp = new Date();
        this.message = message;
        this.errors = errors;
    }

    public ResponseError(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.timestamp = new Date();
        this.message = message;
        errors = Arrays.asList(error);
    }

}
