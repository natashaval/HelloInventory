package hello.inven.helloinven.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//http://www.springboottutorial.com/spring-boot-exception-handling-for-rest-services
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String exception){
        super(exception);
    }
}
