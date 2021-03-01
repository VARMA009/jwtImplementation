package varma.demo.jwtdemo.exception.domain;  

public class UsernameExistException extends Exception {
    public UsernameExistException(String message) {
        super(message);
    }
}
