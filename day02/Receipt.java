import java.util.Scanner;

public class Receipt {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String course = scanner.nextLine();
        double fee = scanner.nextDouble();
        double gst = fee * 0.18;
        double total = fee + gst;

        System.out.printf("Course : %s%n", course);
        System.out.printf("Fee    : Rs. %,.2f%n", fee);
        System.out.printf("GST    : Rs. %,.2f%n", gst);
        System.out.printf("Total  : Rs. %,.2f%n", total);
    }
}