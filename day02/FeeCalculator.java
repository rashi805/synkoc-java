import java.util.Scanner;

public class FeeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Course number (1-3):");
        int courseNumber = scanner.nextInt();

        String course;
        double fee;
        if (courseNumber == 1) {
            course = "Java Full Stack";
            fee = 14999;
        } else if (courseNumber == 2) {
            course = "Data Science";
            fee = 12999;
        } else if (courseNumber == 3) {
            course = "Cloud Basics";
            fee = 4999;
        } else {
            System.out.println("Invalid course");
            return;
        }

        System.out.println("Number of months to pay in (1, 3, or 6):");
        int months = scanner.nextInt();
        if (months != 1 && months != 3 && months != 6) {
            System.out.println("Invalid plan");
            return;
        }

        scanner.nextLine();
        System.out.println("Are you a student? (yes/no):");
        String studentAnswer = scanner.nextLine().trim();
        while (!studentAnswer.equalsIgnoreCase("yes") && !studentAnswer.equalsIgnoreCase("no")) {
            System.out.println("Please answer yes or no:");
            studentAnswer = scanner.nextLine().trim();
        }

        double discount = studentAnswer.equalsIgnoreCase("yes") ? fee * 0.10 : 0;
        double discountedFee = fee - discount;
        double surchargeRate = months == 3 ? 0.02 : months == 6 ? 0.05 : 0;
        double surcharge = discountedFee * surchargeRate;
        double gst = (discountedFee + surcharge) * 0.18;
        double total = discountedFee + surcharge + gst;
        double perMonth = total / months;

        System.out.printf("Course : %s%n", course);
        System.out.printf("Fee : Rs. %,.2f%n", fee);
        System.out.printf("Discount : Rs. %,.2f%n", discount);
        System.out.printf("Surcharge : Rs. %,.2f%n", surcharge);
        System.out.printf("GST : Rs. %,.2f%n", gst);
        System.out.printf("Total : Rs. %,.2f%n", total);
        System.out.printf("Per month : Rs. %,.2f%n", perMonth);
    }
}