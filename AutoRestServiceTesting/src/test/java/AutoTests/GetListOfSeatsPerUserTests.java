package AutoTests;

import AutoDevKit.util.HttpStatus;
import AutoEntities.BasicEntities.BasicRestException;
import AutoEntities.GetListOfSeatsResponse;
import AutoOrderServiceReactor.OrderService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetListOfSeatsPerUserTests {

    public static OrderService orderService;

    @Before
    public void initTest()  {}

    @Test
    public void getListOfSeatsPerUserSuccessTest() throws BasicRestException {
        long userId = 1276381276;

        GetListOfSeatsResponse listOfSeatsPerUser = orderService.getListOfSeatsPerUser(userId);
        Assert.assertEquals("Fail to get list of seats per user according to user ID. Response status is incorrect.", HttpStatus.OK.value(), listOfSeatsPerUser.getRawResponse().getStatus());

        System.out.println(String.format("List of Seats for user %d",userId));
        for(int i=0; i<listOfSeatsPerUser.getGroupRawResponses().size();i++){
            System.out.println(String.format("seat %d",listOfSeatsPerUser.getGroupRawResponses().get(i)));
        }
    }

    @Test
    public void getListOfSeatsForNonExistingUserFailTest() throws BasicRestException {
        long userId = 0;

        GetListOfSeatsResponse listOfSeatsPerUser = orderService.getListOfSeatsPerUser(userId);
        Assert.assertEquals("Get list of seats for none existing user. Response status is incorrect.", HttpStatus.NOT_FOUND.value(), listOfSeatsPerUser.getRawResponse().getStatus());

        System.out.println(String.format("List of Seats for user %d",userId));
        for(int i=0; i<listOfSeatsPerUser.getGroupRawResponses().size();i++){
            System.out.println(String.format("seat %d",listOfSeatsPerUser.getGroupRawResponses().get(i)));
        }
    }

    @After
    public void cleanTestData()  {}

}
