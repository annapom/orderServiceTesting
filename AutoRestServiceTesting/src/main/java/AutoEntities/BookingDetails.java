package AutoEntities;

public class BookingDetails {

    private long userId;
    private String requestedSection;
    private int requestedRowInSection;
    private int requestedSeatInTheRow;

    public BookingDetails(long userId, String requestedSection, int requestedRowInSection, int requestedSeatInTheRow) {
        this.userId = userId;
        this.requestedSection = requestedSection;
        this.requestedRowInSection = requestedRowInSection;
        this.requestedSeatInTheRow = requestedSeatInTheRow;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRequestedSection() {
        return requestedSection;
    }

    public void setRequestedSection(String requestedSection) {
        this.requestedSection = requestedSection;
    }

    public int getRequestedRowInSection() {
        return requestedRowInSection;
    }

    public void setRequestedRowInSection(int requestedRowInSection) {
        this.requestedRowInSection = requestedRowInSection;
    }

    public int getRequestedSeatInTheRow() {
        return requestedSeatInTheRow;
    }

    public void setRequestedSeatInTheRow(int requestedSeatInTheRow) {
        this.requestedSeatInTheRow = requestedSeatInTheRow;
    }
}
