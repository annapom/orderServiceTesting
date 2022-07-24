package AutoTests;

import AutoDevKit.util.HttpStatus;
import AutoEntities.BasicEntities.BasicRestException;
import AutoOrderServiceReactor.OrderService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;

public class DeleteOrderServiceTests {

    public static OrderService orderService;

    @Before
    public void initTest()  {}

    @Test
    public void deleteBookingSuccessTest() throws IOException, BasicRestException {
        long userId = 1276381276;
        String requestedSection="A";
        int requestedRowInSection=1;
        int requestedSeatInTheRow=50;

        Response deleteBookingResponse = orderService.deleteBooking(userId, requestedSection, requestedRowInSection, requestedSeatInTheRow);
        Assert.assertEquals("Fail to delete booking per valid ticket data. Response status is incorrect. ", HttpStatus.CREATED.value(), deleteBookingResponse.getStatus());
    }

    @Test
    public void deleteBookingtWrongInputSectionFailTest() throws IOException, BasicRestException {
        long userId = 1276381276;
        String requestedSection="O";  //wrong input
        int requestedRowInSection=1;
        int requestedSeatInTheRow=50;

        Response bookingTicketResponse = orderService.deleteBooking(userId, requestedSection, requestedRowInSection, requestedSeatInTheRow);
        Assert.assertEquals("Booking was deleted for wrong section input data. Response status is incorrect.", HttpStatus.BAD_REQUEST.value(), bookingTicketResponse.getStatus());
    }

    @After
    public void cleanTestData()  {}
}
