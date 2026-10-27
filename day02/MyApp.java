public class MyApp {
    public static void main(String[] args) {
        String appName = "CampusEvents";
        String tagline = "Your campus, one event at a time.";
        int pageCount = 5;

        System.out.println(appName);
        System.out.println(tagline);
        System.out.printf("Pages: %d%n", pageCount);
        System.out.println("1. Home — View upcoming college events and announcements.");
        System.out.println("2. Event Details — Check event dates, venues, and rules.");
        System.out.println("3. Register — Register for college events.");
        System.out.println("4. My Registrations — View events the student has registered for.");
        System.out.println("5. Admin Dashboard — Create events and manage registrations.");
    }
}