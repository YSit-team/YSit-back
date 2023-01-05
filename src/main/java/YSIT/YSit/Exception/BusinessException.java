package YSIT.YSit.Exception;

public class BusinessException extends RuntimeException{
    private String message;
    public BusinessException(String message) {
        super(message);
        this.message = message;
    }
}
