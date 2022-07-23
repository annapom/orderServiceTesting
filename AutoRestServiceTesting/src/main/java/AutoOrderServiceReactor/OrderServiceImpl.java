package AutoOrderServiceReactor;

import AutoDevKit.RestClient;
import AutoDevKit.util.ComposedRequest;
import AutoEntities.BasicEntities.BasicRestException;
import AutoEntities.Constants;
import AutoEntities.BookingTicketRequest;
import AutoEntities.GetListOfSeatsResponse;
import AutoOrderServiceReactor.General.RestService;
import com.google.gson.Gson;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class OrderServiceImpl extends RestClient implements OrderService {
    protected static String baseURI;
    protected  static RestClient restClient = new RestClient();
    protected  static RestService restService = new RestService();

    @Override
    public Response createBookingTicket(long userId, String requestedSection, int requestedRowInTheSection, int requestedSeatInTheRow) throws IOException, BasicRestException {
        String postBookingTicketURI = baseURI.concat(Constants.BookingTickets.URI_BOOKING_TICKETS);

        ComposedRequest composedRequest = new ComposedRequest();
        composedRequest.setMediaType(MediaType.APPLICATION_JSON_TYPE);

        BookingTicketRequest createBookingTicketRequest= new BookingTicketRequest(userId, requestedSection, requestedRowInTheSection, requestedSeatInTheRow);
        Response response = restClient.create(postBookingTicketURI, createBookingTicketRequest.getJsonBodyInString(),composedRequest, composedRequest.getMediaType());

        return restService.responseValidator(response);
    }


    @Override
    public GetListOfSeatsResponse getListOfSeatsPerUser(long userId) throws BasicRestException {
        String getListOfSeatsPerUserURI = baseURI.concat(String.format(Constants.BookingTickets.URI_BOOKING_TICKETS,userId));

        ComposedRequest composedRequest = new ComposedRequest();
        composedRequest.setMediaType(MediaType.APPLICATION_JSON_TYPE);

        Response response = restClient.get(getListOfSeatsPerUserURI, composedRequest.getHeaders(), composedRequest.getCookies());
        response = restService.responseValidator(response);

        return new Gson().fromJson(response.readEntity(String.class), GetListOfSeatsResponse.class);

    }

    @Override
    public Response deleteBooking(long userId, String requestedSection, int requestedRowInTheSection, int requestedSeatInTheRow) throws IOException, BasicRestException {
        String deleteBookingURI = baseURI.concat(Constants.BookingTickets.URI_BOOKING_TICKETS);

        ComposedRequest composedRequest = new ComposedRequest();
        composedRequest.setMediaType(MediaType.APPLICATION_JSON_TYPE);

        BookingTicketRequest deleteBookingTicketRequest= new BookingTicketRequest(userId, requestedSection, requestedRowInTheSection, requestedSeatInTheRow);
        Response response = restClient.delete(deleteBookingURI, deleteBookingTicketRequest.getJsonBodyInString(), composedRequest, deleteBookingTicketRequest.getMediaType());

        return restService.responseValidator(response);
    }
}

