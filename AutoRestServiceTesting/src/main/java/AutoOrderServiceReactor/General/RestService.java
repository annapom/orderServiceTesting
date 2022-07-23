package AutoOrderServiceReactor.General;


import AutoEntities.BasicEntities.BasicRestException;
import AutoEntities.BasicEntities.MessageError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class RestService {

    protected static final Logger log = LoggerFactory.getLogger(RestService.class);
    protected ObjectMapper mapper = new ObjectMapper();

    public Response responseValidator(Response response) throws BasicRestException {
        int status = response.getStatus();
        if ((status == 200) || (status == 201) || (status == 202) || (status == 204)) {
            return response;
        } else {
            log.error("Http Status code different from HttpStatusCode {200 or 201 or 202}, actual code {" + response.getStatus() + "} ,message {" + response.getStatusInfo().getReasonPhrase() + "}, resource {" + response.toString() + "}");

            String extractCxMessage = response.readEntity(String.class);
            MessageError messageError = new MessageError();
            if (!extractCxMessage.isEmpty()) {
                try {
                    messageError = mapper.readValue(extractCxMessage, MessageError.class);
                } catch (IOException e) {
                    log.error("Failed to wrap JSON response. Error: " + e);
                } finally {
                    messageError.setMessageDetails(extractCxMessage);
                }
            }
            throw new BasicRestException(response.getStatus(), response.getStatusInfo().getReasonPhrase(), messageError);
        }
    }
}
