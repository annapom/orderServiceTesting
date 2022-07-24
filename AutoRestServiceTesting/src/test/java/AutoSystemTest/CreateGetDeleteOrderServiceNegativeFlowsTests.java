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
 * Flow:
 * 1. Book 3 tickets for random userX
 * 2. Try to Book the same ticket for another userY
 *      Expected Results: the booking for userY will fail
 */
public class CreateGetDeleteOrderServiceNegativeFlowsTests {

    public static OrderService orderService;
    private static final int ROWS_IN_SECTION = 100;
    private static final int SEATS_IN_ROW = 100;
    private static final int NUM_OF_SECTIONS = 6;
    private static char[] SECTIONS = {'A', 'B', 'C', 'D', 'E', 'F'};

    //Test Data
    private static int numTicketsRequestedByTheUser = 3; //predefined
    private static long userXidForTestUser;              //random selected
    private static long userYidForTestUser;              //random selected
    private static int randomSection;                   //random selected
    private static int randomRowInSection;              //random selected
    private static int randomSeatInTheRow;              //random selected

    @Before
    public void initTest() {

        Random randomNum = new Random();
        userXidForTestUser = randomNum.nextInt(NUM_OF_SECTIONS * ROWS_IN_SECTION * SEATS_IN_ROW);
        userYidForTestUser = randomNum.nextInt(NUM_OF_SECTIONS * ROWS_IN_SECTION * SEATS_IN_ROW);
        randomSection = randomNum.nextInt(NUM_OF_SECTIONS);
        randomRowInSection = randomNum.nextInt(ROWS_IN_SECTION);
        randomSeatInTheRow = randomNum.nextInt(SEATS_IN_ROW);
    }

    @Test
    public void createGetDeleteBookingNegativeFlowTest() throws BasicRestException, IOException {

        // Book 3 tickets for random userX
        for (int i = 0; i < numTicketsRequestedByTheUser; i++) {
            BookingDetails bookingDetails = new BookingDetails(userXidForTestUser, Character.toString(SECTIONS[randomSection]), randomRowInSection, randomSeatInTheRow + i);
            Response bookingTicketResponse = orderService.createBookingTicket(bookingDetails.getUserId(), bookingDetails.getRequestedSection(), bookingDetails.getRequestedRowInSection(), bookingDetails.getRequestedSeatInTheRow());
            Assert.assertEquals("Fail to create booking per ticket data. Response status is incorrect.", HttpStatus.CREATED.value(), bookingTicketResponse.getStatus());
        }

        //Try to Book the same ticket for another userY
        BookingDetails bookingDetailsUserY = new BookingDetails(userYidForTestUser, Character.toString(SECTIONS[randomSection]), randomRowInSection, randomSeatInTheRow);

        try{
            orderService.createBookingTicket(bookingDetailsUserY.getUserId(), bookingDetailsUserY.getRequestedSection(), bookingDetailsUserY.getRequestedRowInSection(), bookingDetailsUserY.getRequestedSeatInTheRow());
            Assert.fail("Booking seat succeeded for seat that already booked by another user");
        }
        catch (BasicRestException e){
            System.out.println(String.format("Can't book seat, that already booked by another user %s",e.getMessage()));
        }

    }

    @After
    public void cleanTestData() throws IOException, BasicRestException {

        //Delete reserved tickets for userX
        for (int i = 0; i < numTicketsRequestedByTheUser; i++) {
            orderService.deleteBooking(userXidForTestUser, Character.toString(SECTIONS[randomSection]), randomRowInSection, randomSeatInTheRow + i);
        }
    }
}
