package AutoEntities.BasicEntities;

public class BasicRestException  extends Exception{
    private int statusCode;
    private String reasonPhrase;
    private MessageError messageError;

    public BasicRestException(String message) {
        super(message);
    }

    public BasicRestException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BasicRestException(int statusCode, String reasonPhrase, MessageError messageError) {
        super(String.format("\nStatus code: %d \nReason phrase: %s \nCxMessage Code: %s \nCxMessage Details: %s", statusCode, reasonPhrase, messageError.getMessageCode(), messageError.getMessageDetails()));
        this.reasonPhrase = reasonPhrase;
        this.statusCode = statusCode;
        this.messageError = messageError;
    }

    public BasicRestException(int statusCode, String reasonPhrase, String message) {
        super(String.format("\nStatus code: %d \nReason phrase: %s \nMessage: %s", statusCode, reasonPhrase, message));
        this.reasonPhrase = reasonPhrase;
        this.statusCode = statusCode;
    }

}
