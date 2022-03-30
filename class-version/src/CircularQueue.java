// Circular queue  implementation.
public class CircularQueue {
    private int size = 8;
    //Setting an array to hold room objects for the waiting list, as an instance of the CircularQueue class.
    private Room[] waitingList = new Room[size];
    private int front;
    private int rear;

    //Constructor of the CircularQueue class.
    public CircularQueue() {
        front = -1;
        rear = -1;

        //Initializing the waiting list.
        for (int x = 0; x < 8; x++ ) {
            waitingList[x] = new Room("e");
        }
    }

    //Check if the waiting list is full.
    public boolean isFull() {
        if (front == 0 && rear == size - 1) {
            return true;
        }
        if (front == rear + 1) {
            return true;
        }
        return false;
    }

    //Check if the waiting list is empty.
    public boolean isEmpty() {
        if (front == -1)
            return true;
        else
            return false;
    }

    //Returns the rear object of the waiting list array to add someone to the waiting list.
    public Room enQueue() {
        if (front == -1)
            front = 0;
        rear = (rear + 1) % size;

        return waitingList[rear];
    }

    //Returns the front object of the waiting list array to add the person in the front of the queue to the empty room.
    public Room deQueue() {
        Room frontPerson;
        frontPerson = waitingList[front];

        //Resetting the front and rear indexes of the waiting list back to their initial value.
        if (front == rear) {
            front = -1;
            rear = -1;
        }
        else {
            //Sets the index to map the next person in the front of the waiting list.
            front = (front + 1) % size;
        }
        return (frontPerson);
    }
}