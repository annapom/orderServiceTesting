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

public class CreateOrderServiceTests {


    public static OrderService orderService;

    @Before
    public void initTest()  {}

    @Test
    public void createBookingTicketSuccessTest() throws IOException, BasicRestException {
        long userId = 1276381276;
        String requestedSection="A";
        int requestedRowInSection=1;
        int requestedSeatInTheRow=50;

        Response bookingTicketResponse = orderService.createBookingTicket(userId, requestedSection, requestedRowInSection, requestedSeatInTheRow);
        Assert.assertEquals("Fail to create booking per ticket valid data. Response status is incorrect.", HttpStatus.CREATED.value(), bookingTicketResponse.getStatus());
    }

    @Test
    public void createBookingTicketWrongInputSectionFailTest() throws IOException, BasicRestException {
        long userId = 1276381276;
        String requestedSection="O";  //wrong input
        int requestedRowInSection=1;
        int requestedSeatInTheRow=50;


        Response bookingTicketResponse = orderService.createBookingTicket(userId, requestedSection, requestedRowInSection, requestedSeatInTheRow);
        Assert.assertEquals("Booking created for not valid section. Response status is incorrect.", HttpStatus.BAD_REQUEST.value(), bookingTicketResponse.getStatus());
    }

    @After
    public void cleanTestData()  {}

}
