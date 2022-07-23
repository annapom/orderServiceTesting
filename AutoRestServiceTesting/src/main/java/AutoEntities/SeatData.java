package AutoEntities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties({"otherProps"})
public class SeatData {

    @JsonProperty("requestedSection")
    private long requestedSection;

    @JsonProperty("requestedRowInSection")
    private String requestedRowInSection;

    @JsonProperty("requestedSeatInTheRow")
    private boolean requestedSeatInTheRow;

    private Map<String, Object> otherProps = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> any() {
        return otherProps;
    }

    @JsonAnySetter
    public void set(String name, Object value) {
        otherProps.put(name, value);
    }

    public SeatData(long requestedSection, String requestedRowInSection, boolean requestedSeatInTheRow) {
        this.requestedSection = requestedSection;
        this.requestedRowInSection = requestedRowInSection;
        this.requestedSeatInTheRow = requestedSeatInTheRow;
    }
}
