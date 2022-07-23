package AutoOrderServiceReactor;

import AutoEntities.BasicEntities.BasicRestException;
import AutoEntities.GetListOfSeatsResponse;

import javax.ws.rs.core.Response;
import java.io.IOException;

public interface OrderService {

    /**
     *  Create New Booking per ticket input data
     * @param userId  - User identifier
     * @param requestedSection  -  Requested section
     * @param requestedRowInTheSection - Requested row in the section
     * @param requestedSeatInTheRow - Requested seat in the row
     * @return -  success or failure of the booking
     */
    Response createBookingTicket(long userId, String requestedSection, int requestedRowInTheSection, int requestedSeatInTheRow) throws IOException, BasicRestException;

    /**
     *  Get List of seats for user
     * @param userId  - User identifier
     * @return - list of seats per user
     */
    GetListOfSeatsResponse getListOfSeatsPerUser(long userId) throws BasicRestException;

    /**
     *  Delete Existing Booking per ticket data
     * @param userId  - User identifier
     * @param requestedSection  -  Requested section
     * @param requestedRowInTheSection  - Requested row in the section
     * @param requestedSeatInTheRow  - Requested seat in the row
     * @return -  success or failure of deleting booking
     */
    Response deleteBooking(long userId, String requestedSection, int requestedRowInTheSection, int requestedSeatInTheRow) throws IOException, BasicRestException;
}
