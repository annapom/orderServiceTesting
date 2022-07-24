package AutoSystemTest;

import AutoDevKit.util.HttpStatus;
import AutoEntities.BasicEntities.BasicRestException;
import AutoEntities.BookingDetails;
import AutoEntities.GetListOfSeatsResponse;
import AutoOrderServiceReactor.OrderService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Random;

/**
 * Success Flow:
 *  1. Book 3 tickets for random userX
 *  2. Check how many tickets belong to userX
 *      Expected Results: three tickets belong to userX
 *  3. Delete one of the tickets for userX
 *  4. Check which tickets belong to userX
 *      Expected Results: two tickets remains for userX
 */
public class CreateGetDeleteOrderServiceSuccessFlowsTests {

    public static OrderService orderService;
    private static final int ROWS_IN_SECTION = 100;
    private static final int SEATS_IN_ROW = 100;
    private static final int NUM_OF_SECTIONS = 6;
    private static char[] SECTIONS = {'A', 'B', 'C', 'D', 'E', 'F'};

    //Test Data
    private static int numTicketsRequestedByTheUser = 3; //predefined
    private static long userIdForTestUser;              //random selected
    private static int randomSection;                   //random selected
    private static int randomRowInSection;              //random selected
    private static int randomSeatInTheRow;              //random selected

    @Before
    public void initTest() {

        Random randomNum = new Random();
        userIdForTestUser = randomNum.nextInt(NUM_OF_SECTIONS * ROWS_IN_SECTION * SEATS_IN_ROW);
        randomSection = randomNum.nextInt(NUM_OF_SECTIONS);
        randomRowInSection = randomNum.nextInt(ROWS_IN_SECTION);
        randomSeatInTheRow = randomNum.nextInt(SEATS_IN_ROW);
    }

    @Test
    public void createGetDeleteBookingSuccessFlowTest() throws IOException, BasicRestException {

        // Book 3 tickets for random userX
        for (int i = 0; i < numTicketsRequestedByTheUser; i++) {
            BookingDetails bookingDetails = new BookingDetails(userIdForTestUser, Character.toString(SECTIONS[randomSection]), randomRowInSection, randomSeatInTheRow + i);
            Response bookingTicketResponse = orderService.createBookingTicket(bookingDetails.getUserId(), bookingDetails.getRequestedSection(), bookingDetails.getRequestedRowInSection(), bookingDetails.getRequestedSeatInTheRow());
            Assert.assertEquals("Fail to create booking per ticket data. Response status is incorrect.", HttpStatus.CREATED.value(), bookingTicketResponse.getStatus());
        }

        //Check how many tickets belong to userX  => Expected Results: three tickets belong to userX
        GetListOfSeatsResponse listOfSeatsPerUser = orderService.getListOfSeatsPerUser(userIdForTestUser);
        Assert.assertEquals("Fail to get list of seats per user according to user ID. Response status is incorrect.", HttpStatus.OK.value(), listOfSeatsPerUser.getRawResponse().getStatus());

        Assert.assertEquals(String.format("Number of ticket for user %d is not as expected", userIdForTestUser), numTicketsRequestedByTheUser, listOfSeatsPerUser.getGroupRawResponses().size());

        //Delete one of the tickets for userX
        Response deleteBookingResponse = orderService.deleteBooking(userIdForTestUser, Character.toString(SECTIONS[randomSection]), randomRowInSection, randomSeatInTheRow);
        Assert.assertEquals("Fail to delete booking per valid ticket data. Response status is incorrect. ", HttpStatus.CREATED.value(), deleteBookingResponse.getStatus());

        //Check how many tickets belong to userX => Expected Results: two tickets remains for userX
        Assert.assertEquals(String.format("Number of ticket for user %d is not as expected", userIdForTestUser), numTicketsRequestedByTheUser - 1, listOfSeatsPerUser.getGroupRawResponses().size());
    }

    @After
    public void cleanTestData() throws IOException, BasicRestException {

        //Delete remains tickets for userX
        for (int i = 0; i < numTicketsRequestedByTheUser - 1; i++) {
            orderService.deleteBooking(userIdForTestUser, Character.toString(SECTIONS[randomSection]), randomRowInSection, randomSeatInTheRow + 1 + i);
        }
    }
}
