import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Hotel {
    //Creating a static Scanner object to use inside all the static methods inside Hotel class.
    static Scanner userInput = new Scanner(System.in);
    //Creating a static circularQueue object to use inside all the static methods inside Hotel class.
    static CircularQueue queue = new CircularQueue();
    public static void main(String[] args) {
        //Initializing hotel array to store room objects.
        Room[] hotel = new Room[8];

        //Calling initialize method.
        initialize(hotel);

        //A menu for the user to select each option which will iterate until user enters a valid option or "Q' to quit the program.
        while (true) {
            System.out.println("_______________________________________________");
            String menu = "MENU\n\n" +
                    "A: Add a customer\n" +
                    "V: View all rooms\n" +
                    "E: Display empty rooms\n" +
                    "D: Delete customer from room\n" +
                    "F: Find room from customer name\n" +
                    "S: Store program data into file\n" +
                    "L: Load program data from file\n" +
                    "O: View guests ordered alphabetically by name\n" +
                    "Q: Quit the program\n";

            System.out.println(menu);

            System.out.print("Enter your option out of 'A,V,E,D,F,S,L,O,Q' : ");
            String option = userInput.nextLine().toUpperCase();

            if (option.equals("A")) {
                addCustomer(hotel);
            } else if (option.equals("V")) {
                viewRooms(hotel);
            } else if (option.equals("E")) {
                emptyRooms(hotel);
            } else if (option.equals("D")) {
                deleteCustomer(hotel);
            } else if (option.equals("F")) {
                findCustomer(hotel);
            } else if (option.equals("S")) {
                storeDetails(hotel);
            } else if (option.equals("L")) {
                loadDetails(hotel);
            } else if (option.equals("O")) {
                orderCustomers(hotel);
            } else if (option.equals("Q")) {
                break;
            } else {
                System.out.print("\nPlease enter a valid option.\n");
            }
        }
    }

    //Initializing all the Rooms as empty rooms.
    public static void initialize(Room[] hotelRef) {
        for (int x = 0; x < 8; x++ ) {
            hotelRef[x] = new Room("e");
        }
    }

    //A method which will prompt user asking if they want to return back to menu when they are inside a specific option.
    public static boolean backToMenu(String message) {
        boolean result = false;
        System.out.println("\nIf you want to return back to menu press 'b' " + message);
        String choice = userInput.nextLine();

        if ((choice.toLowerCase()).equals("b")) {
            result = true;
        }
        return result;
    }

    //A method to validate and take the number of guests from the user.
    public static int takeGuestCount() {
        int guestCount;

        while (true) {
            try {
                System.out.print("Enter the number of guests (MIN 1 MAX 6) : ");
                guestCount = userInput.nextInt();
                userInput.nextLine();

                if (guestCount < 1 || guestCount > 6) {
                    System.out.println("Please enter a valid number of guests.\n");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Please enter a valid number of guests.\n");
                userInput.nextLine();
            }
        }
        return guestCount;
    }

    //A method to take the first name and surname from the user.
    public static String takeName(String message) {
        System.out.print("Enter the " + message + " of the paying guest : ");
        String name = userInput.next().toUpperCase();
        userInput.nextLine();

        return name;
    }

    //A method to validate and take the credit card number from the user.
    public static int takeCardNumber() {
        int cardNumber;

        while (true) {
            try {
                System.out.print("Enter the 8 digit credit card number : ");
                cardNumber = userInput.nextInt();
                userInput.nextLine();

                if (String.valueOf(cardNumber).length() < 8) {
                    System.out.println("Card number cannot be less than 8 digits.\n");
                    continue;
                } else if (String.valueOf(cardNumber).length()  > 8) {
                    System.out.println("Card number cannot be more than 8 digits.\n");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Please enter a valid credit card number.\n");
                userInput.nextLine();
            }
        }
        return cardNumber;
    }

    //A method to add a customer in to a Room.
    public static void addCustomer(Room[] hotel) {
        System.out.println("_______________________________________________");
        System.out.println("ADD A CUSTOMER\n");

        while (true) {
            //Initializing a variable to count the number of empty rooms.
            int emptyRooms = 0;

            for (Room name : hotel) {
                if ((name.getGuestName()).equals("e")) {
                    emptyRooms += 1;
                }
            }

            //Checking if all the rooms are full.
            if (emptyRooms == 0) {
                //Displaying a message if both the rooms and the waiting list is full.
                if (queue.isFull()) {
                    System.out.println("Sorry! all the rooms are already full and the waiting list is also full.");
                    while (true) {
                        boolean choice = backToMenu("");
                        if (choice) {
                            break;
                        }
                    }
                    break;
                    //Adding the customer in to the waiting list if the queue is not full.
                } else {
                    System.out.println("There are no empty rooms. You will be added to the waiting list.\n");
                    Room lastPosition = queue.enQueue();

                    System.out.print("Enter the customer's name : ");
                    String roomName = userInput.next().toUpperCase();
                    userInput.nextLine();

                    //Getting and storing information from the user into the waiting list.
                    lastPosition.setGuestName(roomName);
                    lastPosition.setGuestCount(takeGuestCount());
                    lastPosition.getAdditionalDetails().setFirstName(takeName("first name"));
                    lastPosition.getAdditionalDetails().setSurname(takeName("surname"));
                    lastPosition.getAdditionalDetails().setCardNumber(takeCardNumber());

                    //Displaying the entered details by the user.
                    System.out.println("\nCustomer successfully added to the waiting list. You will be added to a room as soon as a room is empty according to the waiting list. " +
                            "\n\nCustomer name : " + lastPosition.getGuestName() +
                            "\nNumber of guests : " + lastPosition.getGuestCount() +
                            "\nPaying guest first name : " + lastPosition.getAdditionalDetails().getFirstName() +
                            "\nPaying guest surname : " + lastPosition.getAdditionalDetails().getSurname() +
                            "\nCredit card number : " + lastPosition.getAdditionalDetails().getCardNumber()
                    );

                    boolean choice = backToMenu("enter anything else to add another customer.");
                    if (choice) {
                        break;
                    }
                }
            } else {
                try {
                    System.out.print("Enter a room number from (0 - 7) : ");
                    int roomNumber = userInput.nextInt();
                    userInput.nextLine();

                    //Checking if the entered room number is valid.
                    if (roomNumber < 0 || roomNumber > 7) {
                        System.out.println("Please enter a valid room number.\n");
                        continue;
                    }

                    //Checking if the entered room is empty.
                    if (!((hotel[roomNumber].getGuestName()).equals("e"))) {
                        System.out.println("Sorry! room " + roomNumber + " is already occupied by " + hotel[roomNumber].getGuestName());
                        System.out.println("Please go back to menu and delete the customer first or choose a different room.");
                        boolean choice = backToMenu("enter anything else to choose a different room.");
                        if (choice) {
                            break;
                        } else {
                            continue;
                        }
                    }

                    System.out.print("Enter the customer's name for room no " + roomNumber + " : ");
                    String roomName = userInput.next().toUpperCase();
                    userInput.nextLine();

                    //Getting and storing additional information from the user.
                    hotel[roomNumber].setGuestName(roomName);
                    hotel[roomNumber].setGuestCount(takeGuestCount());
                    hotel[roomNumber].getAdditionalDetails().setFirstName(takeName("first name"));
                    hotel[roomNumber].getAdditionalDetails().setSurname(takeName("surname"));
                    hotel[roomNumber].getAdditionalDetails().setCardNumber(takeCardNumber());

                    //Displaying the entered details by the user for the room.
                    System.out.println("\nCustomer successfully added to room no " + roomNumber +
                            "\n\nCustomer name : " + hotel[roomNumber].getGuestName() +
                            "\nNumber of guests : " + hotel[roomNumber].getGuestCount() +
                            "\nPaying guest first name : " + hotel[roomNumber].getAdditionalDetails().getFirstName() +
                            "\nPaying guest surname : " + hotel[roomNumber].getAdditionalDetails().getSurname() +
                            "\nCredit card number : " + hotel[roomNumber].getAdditionalDetails().getCardNumber()
                    );

                    boolean choice = backToMenu("enter anything else to add another customer.");
                    if (choice) {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Please enter a valid room number.\n");
                    userInput.nextLine();
                }
            }
        }
    }

    //A method to view the current state of every room inside the hotel.
    public static void viewRooms(Room[] hotel) {
        System.out.println("_______________________________________________");
        System.out.println("VIEW ALL ROOMS\n");

        for (int i = 0; i < hotel.length; i++ ) {
            if ((hotel[i].getGuestName()).equals("e")) {
                System.out.println("Room " + i + " is empty");
            } else {
                System.out.println("Room " + i + " is occupied by " + hotel[i].getGuestName());
            }
        }

        while (true) {
            boolean choice = backToMenu("");
            if (choice) {
                break;
            }
        }
    }

    //A method to view all the empty rooms available inside the hotel.
    public static void emptyRooms(Room[] hotel) {
        System.out.println("_______________________________________________");
        System.out.println("DISPLAY EMPTY ROOMS\n");

        //Initializing a variable to count the number of empty rooms.
        int emptyRooms = 0;

        for (int i = 0; i < hotel.length; i++ ) {
            if ((hotel[i].getGuestName()).equals("e")) {
                System.out.println("Room " + i + " is empty");
                emptyRooms += 1;
            }
        }

        //Displaying a message if all the room are full.
        if (emptyRooms == 0) {
            System.out.println("There are no empty rooms.");
        }

        while (true) {
            boolean choice = backToMenu("");
            if (choice) {
                break;
            }
        }
    }

    //A method to delete a customer in a Room.
    public static void deleteCustomer(Room[] hotel) {
        System.out.println("_______________________________________________");
        System.out.println("DELETE CUSTOMER FROM ROOM\n");

        while (true) {
            boolean wasFull = false;
            int emptyRooms = 0;

            for (Room name : hotel) {
                if ((name.getGuestName()).equals("e")) {
                    emptyRooms += 1;
                }
            }

            if (emptyRooms == 0) {
                wasFull = true;
            }

            try {
                System.out.print("Enter the room number you want to delete the customer from : ");
                int roomNumber = userInput.nextInt();
                userInput.nextLine();

                //Checking if the entered room number is valid.
                if (roomNumber < 0 || roomNumber > 7) {
                    System.out.println("Please enter a valid room number.\n");
                    continue;
                }

                //Checking if the entered room is empty or not.
                if ((hotel[roomNumber].getGuestName()).equals("e")) {
                    System.out.println("There is no customer in room " + roomNumber);
                } else {
                    System.out.println(hotel[roomNumber].getGuestName() + " successfully deleted from room " + roomNumber);
                    hotel[roomNumber].clearRoom();
                }

                //Checking if all the rooms were full.
                if (wasFull) {
                    //Adding the customer into the deleted room from the waiting list if the queue is not empty.
                    if (!(queue.isEmpty())) {
                        //Getting the details of the person who is in the front of the queue.
                        Room frontPerson = queue.deQueue();

                        //Storing the details to the deleted room from the waiting list.
                        hotel[roomNumber].setGuestName(frontPerson.getGuestName());
                        hotel[roomNumber].setGuestCount(frontPerson.getGuestCount());
                        hotel[roomNumber].getAdditionalDetails().setFirstName(frontPerson.getAdditionalDetails().getFirstName());
                        hotel[roomNumber].getAdditionalDetails().setSurname(frontPerson.getAdditionalDetails().getSurname());
                        hotel[roomNumber].getAdditionalDetails().setCardNumber(frontPerson.getAdditionalDetails().getCardNumber());

                        System.out.println("\n" + frontPerson.getGuestName() + " was added to room " + roomNumber + " from the waiting list.");

                        //Clearing the information of the added person from the waiting list.
                        frontPerson.clearRoom();
                    }
                }

                boolean choice = backToMenu("enter anything else to delete another customer.");
                if (choice) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid room number.\n");
                userInput.nextLine();
            }
        }
    }

    //A method to find a room by customer name.
    public static void findCustomer(Room[] hotel) {
        System.out.println("_______________________________________________");
        System.out.println("FIND ROOM FROM CUSTOMER NAME\n");

        while (true) {
            //Initializing a variable to check if the room is found.
            boolean notFound = true;

            System.out.print("Enter the customer's name : ");
            String roomName = userInput.next().toUpperCase();
            userInput.nextLine();

            for (int i = 0; i < hotel.length; i++ ) {
                if ((hotel[i].getGuestName()).equals(roomName)) {
                    System.out.println(roomName + " is in room no " + i);
                    notFound = false;
                }
            }

            //Displaying a message if the room is not found.
            if (notFound) {
                System.out.println("There was no customer by the name '" + roomName + "'");
            }

            boolean choice = backToMenu("enter anything else to find another room.");
            if (choice) {
                break;
            }
        }
    }

    //A method to store the program data into a file as a table.
    public static void storeDetails(Room[] hotel) {
        System.out.println("_______________________________________________");
        System.out.println("STORE PROGRAM DATA INTO FILE\n");

        try {
            FileWriter dataFile = new FileWriter("src/hotelData.txt");
            //Setting the column headings.
            dataFile.write("Room\tCustomer\tGuests\tFName\tSurname\tCCN\n\n");

            for (int i = 0; i < hotel.length; i++ ) {
                //Checking if the room is empty or not.
                if ((hotel[i].getGuestName()).equals("e")) {
                    dataFile.write(  i + "\t\t-\t\t\t-\t\t-\t\t-\t\t-\n");
                } else {
                    dataFile.write( i + "\t\t" + hotel[i]);
                }
            }

            dataFile.close();
            System.out.println("Successfully stored the data in to the file 'hotelData.txt'.");
        } catch (IOException e) {
            System.out.println("An error occurred while storing data.");
        }

        while (true) {
            boolean choice = backToMenu("");
            if (choice) {
                break;
            }
        }
    }

    //A method to load the program data from a file.
    public static void loadDetails(Room[] hotel) {
        System.out.println("_______________________________________________");
        System.out.println("LOAD PROGRAM DATA FROM FILE\n");

        //Initializing a variable to count the number of lines inside the file.
        int lineNumber = 0;

        try {
            File dataFile = new File("src/hotelData.txt");
            Scanner dataReader = new Scanner(dataFile);

            //Allowing user to choose either to view the data inside the file or to override the current data with the data inside the file.
            System.out.println("Do you want to override the current data in the program with the data inside the file?" +
                    "\nPress 'y' if yes enter anything else to just display the data inside the file.");
            String choice = userInput.nextLine().toUpperCase();

            if (choice.equals("Y")) {
                while (dataReader.hasNext()) {
                    //Getting all the words in a row.
                    String room = dataReader.next();
                    String roomName = dataReader.next();
                    String guestCount = dataReader.next();
                    String firstName = dataReader.next();
                    String surname = dataReader.next();
                    String cardNumber = dataReader.next();

                    if (lineNumber > 0) {
                        int roomNumber = Integer.parseInt(room);

                        if (roomName.equals("-")) {
                            //Clearing all the information in that room if the room is empty.
                            hotel[roomNumber].clearRoom();
                        } else {
                            //Setting the information in to the respective room.
                            hotel[roomNumber].setGuestName(roomName);
                            hotel[roomNumber].setGuestCount(Integer.parseInt(guestCount));
                            hotel[roomNumber].getAdditionalDetails().setFirstName(firstName);
                            hotel[roomNumber].getAdditionalDetails().setSurname(surname);
                            hotel[roomNumber].getAdditionalDetails().setCardNumber(Integer.parseInt(cardNumber));
                        }
                    }
                    lineNumber += 1;
                }
            } else {
                while (dataReader.hasNextLine()) {
                    System.out.println(dataReader.nextLine());
                }
            }

            dataReader.close();
            System.out.println("\nSuccessfully loaded data from file 'hotelData.txt'.");
        } catch (IOException e) {
            System.out.println("An error occurred while loading data.");
        }

        while (true) {
            boolean choice = backToMenu("");
            if (choice) {
                break;
            }
        }
    }

    //A method to organize customer names in alphabetical order.
    public static void orderCustomers(Room[] hotel) {
        System.out.println("_______________________________________________");
        System.out.println("VIEW GUESTS ORDERED ALPHABETICALLY BY NAME\n");

        String[] sortedArray = new String[8];
        //Initializing a variable to count the number of empty rooms.
        int emptyRooms = 0;

        //Making a duplicate of the hotel array.
        for (int i = 0; i < hotel.length; i++) {
            if ((hotel[i].getGuestName()).equals("e")) {
                emptyRooms += 1;
            }
            sortedArray[i] = hotel[i].getGuestName();
        }

        //Checking if all the rooms are empty or not.
        if (emptyRooms == 8) {
            System.out.println("There are no guests to sort.");
        } else {
            boolean swapped = true;

            while (swapped) {
                swapped = false;
                for (int j = 0; j < sortedArray.length - 1; j++) {
                    if (sortedArray[j].compareTo(sortedArray[j + 1]) > 0) {
                        String temp = sortedArray[j + 1];
                        sortedArray[j + 1] = sortedArray[j];
                        sortedArray[j] = temp;
                        swapped = true;
                    }
                }
            }

            for (String name : sortedArray) {
                //Checking if the room is not empty.
                if (!(name.equals("e"))) {
                    System.out.print(name);
                    for (int k = 0; k < hotel.length; k++) {
                        if ((hotel[k].getGuestName()).equals(name)) {
                            System.out.println(" (is in room " + k + ")");
                        }
                    }
                }
            }
        }

        while (true) {
            boolean choice = backToMenu("");
            if (choice) {
                break;
            }
        }
    }
}