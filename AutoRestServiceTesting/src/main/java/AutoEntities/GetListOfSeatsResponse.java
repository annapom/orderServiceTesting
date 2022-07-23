package AutoEntities;

import AutoEntities.BasicEntities.BasicResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties("otherProps")
public class GetListOfSeatsResponse  extends BasicResponse {

    private List<SeatData> seatsDataList;

    public GetListOfSeatsResponse(List<SeatData> seatsDataList) {
        this.seatsDataList = seatsDataList;
    }

    @Override
    public boolean isJsonArray() {
        return false;
    }

    @Override
    public GetListOfSeatsResponse setJsonArray(List jsonArray) {
        List<SeatData> seatsDataList = new ArrayList<>();
        for (Object object : jsonArray) {
            seatsDataList.add((SeatData) object);
        }
        return new GetListOfSeatsResponse(seatsDataList);
    }
}
