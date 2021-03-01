package varma.demo.jwtdemo.exception.domain;  

public class EmailExistException extends Exception {
    public EmailExistException(String message) {
        super(message);
    }
}
