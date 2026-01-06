import java.sql.*;
import java.util.Scanner;

class Room {
    int roomNo;
    String type;
    double price;
    boolean booked;

    Room(int roomNo, String type, double price) {
        this.roomNo = roomNo;
        this.type = type;
        this.price = price;
        this.booked = false;
    }
}

class Customer {
    String name;
    int days;
    Room room;

    Customer(String name, int days, Room room) {
        this.name = name;
        this.days = days;
        this.room = room;
    }

    double getBill() {
        return days * room.price;
    }
}

class Hotel {
    Room[] rooms = new Room[3];
    Customer customer;

    Hotel() {
        rooms[0] = new Room(101, "Single", 1000);
        rooms[1] = new Room(102, "Double", 1800);
        rooms[2] = new Room(103, "Deluxe", 2500);
    }

    void showRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room r : rooms) {
            if (!r.booked) {
                System.out.println(r.roomNo + " " + r.type + " ₹" + r.price);
            }
        }
    }

    void bookRoom(String name, int roomNo, int days) {
    for (Room r : rooms) {
        if (r.roomNo == roomNo && !r.booked) {
            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO booking VALUES (?,?,?,?)"
                );

                ps.setInt(1, roomNo);
                ps.setString(2, name);
                ps.setInt(3, days);
                ps.setDouble(4, r.price);

                ps.executeUpdate();
                con.close();

                r.booked = true;
                customer = new Customer(name, days, r);

                System.out.println("Room Booked & Stored in DB!");
                return;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    System.out.println("Room not available!");
}


    void checkout() {
        if (customer == null) {
            System.out.println("No active booking!");
            return;
        }

        System.out.println("\nCustomer Name: " + customer.name);
        System.out.println("Room No: " + customer.room.roomNo);
        System.out.println("Total Bill: ₹" + customer.getBill());

        customer.room.booked = false;
        customer = null;

        System.out.println("Checkout Successful!");
    }
}

public class HRS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel hotel = new Hotel();

        while (true) {
            System.out.println("\n1. View Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Checkout");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    hotel.showRooms();
                    break;

                case 2:
                    System.out.print("Enter Name: ");
                    sc.nextLine();
                    String name = sc.nextLine();
                    System.out.print("Enter Room No: ");
                    int rno = sc.nextInt();
                    System.out.print("Enter Days: ");
                    int days = sc.nextInt();
                    hotel.bookRoom(name, rno, days);
                    break;

                case 3:
                    hotel.checkout();
                    break;

                case 4:
                    System.out.println("Thank you!");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}

