public class Room {
    private String guestName;
    private int guestCount;
    //Setting a person object as an instance of the Room class.
    private Person additionalDetails = new Person();

    public Room(String guestName) {
        this.guestName = guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public Person getAdditionalDetails() {
        return additionalDetails;
    }

    //Method to clear the details of a room and set it as empty.
    public void clearRoom() {
        setGuestName("e");
        guestCount = 0;
        additionalDetails.setFirstName(null);
        additionalDetails.setSurname(null);
        additionalDetails.setCardNumber(0);
    }

    public String toString() {
        return getGuestName() + "\t\t" + getGuestCount() + "\t\t" + additionalDetails.getFirstName() + "\t" + additionalDetails.getSurname() + "\t" + additionalDetails.getCardNumber() + "\n";
    }
}