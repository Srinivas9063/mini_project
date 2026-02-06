package applications;
import java.util.Scanner;


class ContactUI {

    static final int WIDTH = 80;

    static final String RESET  = "\u001B[0m";
    static final String RED    = "\u001B[31m";
    static final String GREEN  = "\u001B[32m";
    static final String YELLOW = "\u001B[33m";
    static final String BLUE   = "\u001B[34m";
    static final String CYAN   = "\u001B[36m";
    static final String WHITE  = "\u001B[37m";

    static void center(String text) {
        int len = visibleLength(text);
        int pad = (WIDTH - len) / 2;
        System.out.println(" ".repeat(Math.max(0, pad)) + text);
    }

    static void centerInline(String text) {
        int len = visibleLength(text);
        int pad = (WIDTH - len) / 2;
        System.out.print(" ".repeat(Math.max(0, pad)) + text);
    }

    static int visibleLength(String text) {
        return text.replaceAll("\u001B\\[[;\\d]*m", "").length();
    }

    static void divider() {
        center(CYAN + "════════════════════════════════════════════════════" + RESET);
    }

    static void title(String text) {
        divider();
        center(YELLOW + text + RESET);
        divider();
    }
}

interface ContactOperations {
    void addContact(String name, String number);
    void searchContact(String name);
    void viewAllContacts();
    void deleteContact(String name);
}


class ContactNode {
    String name;
    String number;
    boolean favourite;
    boolean blocked;
    ContactNode next;

    ContactNode(String name, String number) {
        this.name = name;
        this.number = number;
    }
}


class ContactService implements ContactOperations {

    private ContactNode head = null;
    private Scanner sc = new Scanner(System.in);

    private String lastCallName = "None";
    private String lastCallType = "None";
    private int lastCallMinutes = 0;

    private ContactNode emergency = null;

    ContactService() {
        addContact("Ravi", "9876543210");
        addContact("Anita", "9123456780");
        addContact("Kiran", "9988776655");
    }

    private ContactNode findContact(String name) {
        ContactNode t = head;
        while (t != null) {
            if (t.name.equalsIgnoreCase(name)) return t;
            t = t.next;
        }
        return null;
    }

    public void addContact(String name, String number) {
        if (findContact(name) != null) {
            ContactUI.center(ContactUI.RED + "Contact already exists" + ContactUI.RESET);
            return;
        }

        ContactNode n = new ContactNode(name, number);

        if (head == null) head = n;
        else {
            ContactNode t = head;
            while (t.next != null) t = t.next;
            t.next = n;
        }

        ContactUI.center(ContactUI.GREEN + "✔ Contact added successfully" + ContactUI.RESET);
    }

    public void searchContact(String name) {
        ContactNode c = findContact(name);

        if (c == null) {
            ContactUI.center(ContactUI.RED + "Contact not found" + ContactUI.RESET);
            return;
        }

        if (c.blocked) {
            ContactUI.center(ContactUI.RED + "This contact is BLOCKED" + ContactUI.RESET);
            return;
        }

        ContactUI.title("CONTACT DETAILS");
        ContactUI.center("Name   : " + c.name);
        ContactUI.center("Number : " + c.number);

        ContactUI.divider();
        ContactUI.center("1. Call   2. Message   3. Video Call");

        ContactUI.centerInline(ContactUI.YELLOW + "Select Option: " + ContactUI.RESET);
        int op = sc.nextInt(); sc.nextLine();

        ContactUI.centerInline("Enter duration (minutes): ");
        int min = sc.nextInt(); sc.nextLine();

        lastCallType =
                (op == 1) ? "Call" :
                (op == 2) ? "Message" :
                (op == 3) ? "Video Call" : "Invalid";

        if (lastCallType.equals("Invalid")) {
            ContactUI.center(ContactUI.RED + "Invalid option" + ContactUI.RESET);
            return;
        }

        lastCallName = c.name;
        lastCallMinutes = min;

        ContactUI.center(ContactUI.GREEN + lastCallType + " connected..." + ContactUI.RESET);
        ContactUI.center("Duration: " + min + " minutes");
        ContactUI.center(ContactUI.GREEN + lastCallType + " ended" + ContactUI.RESET);
    }

    public void viewAllContacts() {
        ContactUI.title("CONTACT LIST");
        ContactNode t = head;

        while (t != null) {
            String fav = t.favourite ? " ★" : "";
            String blk = t.blocked ? " [BLOCKED]" : "";
            ContactUI.center(t.name + fav + blk + " | " + t.number);
            t = t.next;
        }
    }

    public void deleteContact(String name) {
        if (head == null) {
            ContactUI.center("No contacts available");
            return;
        }

        if (head.name.equalsIgnoreCase(name)) {
            head = head.next;
            ContactUI.center("Contact deleted");
            return;
        }

        ContactNode p = head, c = head.next;
        while (c != null) {
            if (c.name.equalsIgnoreCase(name)) {
                p.next = c.next;
                ContactUI.center("Contact deleted");
                return;
            }
            p = c; c = c.next;
        }

        ContactUI.center("Contact not found");
    }

    public void markFavourite(String name) {
        ContactNode c = findContact(name);
        ContactUI.center(c != null ? "Marked as favourite" : "Contact not found");
        if (c != null) c.favourite = true;
    }

    public void blockContact(String name) {
        ContactNode c = findContact(name);
        ContactUI.center(c != null ? "Contact blocked" : "Contact not found");
        if (c != null) c.blocked = true;
    }

    public void viewLastCall() {
        ContactUI.title("LAST CALL");
        ContactUI.center("Name     : " + lastCallName);
        ContactUI.center("Type     : " + lastCallType);
        ContactUI.center("Duration : " + lastCallMinutes + " minutes");
    }

    public void setEmergency(String name) {
        emergency = findContact(name);
        ContactUI.center(emergency != null ? "Emergency contact set" : "Contact not found");
    }

    public void callEmergency() {
        if (emergency == null)
            ContactUI.center("No emergency contact set");
        else {
            ContactUI.center("Calling EMERGENCY: " + emergency.name);
            ContactUI.center("Call connected...");
            ContactUI.center("Call ended");
        }
    }
}


public class ContactApp1 {

    public static void Con(Scanner sc) {
        AddressBookLogo.logo();

        
        ContactService service = new ContactService();

        while (true) {

            ContactUI.title("CONTACT APPLICATION");

            ContactUI.center("1. Add Contact");
            ContactUI.center("2. Search Contact");
            ContactUI.center("3. View All Contacts");
            ContactUI.center("4. Delete Contact");
            ContactUI.center("5. Mark Favourite");
            ContactUI.center("6. Block Contact");
            ContactUI.center("7. View Last Call");
            ContactUI.center("8. Set Emergency");
            ContactUI.center("9. Call Emergency");
            ContactUI.center("10. Exit");

            ContactUI.divider();
            ContactUI.centerInline(ContactUI.YELLOW + "Select Option: " + ContactUI.RESET);
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1 -> {
                    ContactUI.centerInline("Enter name   : ");
                    String n = sc.nextLine();
                    ContactUI.centerInline("Enter number : ");
                    service.addContact(n, sc.nextLine());
                }
                case 2 -> { ContactUI.centerInline("Enter name: "); service.searchContact(sc.nextLine()); }
                case 3 -> service.viewAllContacts();
                case 4 -> { ContactUI.centerInline("Enter name: "); service.deleteContact(sc.nextLine()); }
                case 5 -> { ContactUI.centerInline("Enter name: "); service.markFavourite(sc.nextLine()); }
                case 6 -> { ContactUI.centerInline("Enter name: "); service.blockContact(sc.nextLine()); }
                case 7 -> service.viewLastCall();
                case 8 -> { ContactUI.centerInline("Enter name: "); service.setEmergency(sc.nextLine()); }
                case 9 -> service.callEmergency();
                case 10 -> { ContactUI.center("Exiting Contact App..."); MainApp.call(); }
                default -> ContactUI.center("Invalid choice");
            }
        }
    }
}
