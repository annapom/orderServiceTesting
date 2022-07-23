package AutoEntities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

public class BookingTicketRequest {

    private class JsonDto{
        long userId;
        String requestedSection;
        int requestedRowInTheSection;
        int requestedSeatInTheRow;

        public JsonDto(long userId, String requestedSection, int requestedRowInTheSection, int requestedSeatInTheRow){
            this.userId=userId;
            this.requestedSection = requestedSection;
            this.requestedRowInTheSection = requestedRowInTheSection;
            this.requestedSeatInTheRow = requestedSeatInTheRow;
        }

    }

    private MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
    private String jsonBodyInString;

    public BookingTicketRequest(){

    }

    public String getJsonBodyInString() {
        return jsonBodyInString;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public BookingTicketRequest(long userId, String requestedSection, int requestedRowInTheSection, int requestedSeatInTheRow) throws IOException {
        JsonDto jsonDataType = new JsonDto(userId, requestedSection, requestedRowInTheSection, requestedSeatInTheRow);
        createJson(jsonDataType);
    }

    private void createJson(JsonDto jsonDto) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonBodyInString = mapper.writeValueAsString(jsonDto);
        } catch (JsonProcessingException e) {
            throw new IOException("Error creating JSON object from  java DTO", e);
        }
    }


}
