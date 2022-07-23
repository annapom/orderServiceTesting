package AutoEntities.BasicEntities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BasicResponse {

    @JsonIgnore
    private Response rawResponse;

    @JsonIgnore
    private Map<Enum, Response> groupRawResponses = new HashMap<Enum, Response>();

    public BasicResponse(Response rawResponse) {
        this.rawResponse = rawResponse;
    }

    public BasicResponse() {
    }

    public abstract boolean isJsonArray();

    public abstract <Response extends BasicResponse> Response setJsonArray(List jsonArray);

    public Response getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(Response rawResponse) {
        this.rawResponse = rawResponse;
    }

    public Map<Enum, Response> getGroupRawResponses() {
        return groupRawResponses;
    }

    public void setGroupRawResponses(Map<Enum, Response> groupRawResponses) {
        this.groupRawResponses = groupRawResponses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicResponse that = (BasicResponse) o;

        return rawResponse != null ? rawResponse.equals(that.rawResponse) : that.rawResponse == null;

    }

    @Override
    public int hashCode() {
        return rawResponse != null ? rawResponse.hashCode() : 0;
    }
}