import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Stock {
	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			int count = Integer.parseInt(scanner.nextLine().trim());
			Map<String, Integer> stock = new LinkedHashMap<>();
			for (int i = 0; i < count; i++) {
				String[] item = scanner.nextLine().trim().split("\\s+", 2);
				if (item.length != 2) {
					throw new IllegalArgumentException("Each line must contain an item and quantity.");
				}
				int quantity = Integer.parseInt(item[1]);
				stock.put(item[0], stock.getOrDefault(item[0], 0) + quantity);
			}

			stock.forEach((item, quantity) -> System.out.println(item + ": " + quantity));
			boolean hasLowStock = false;
			System.out.println("Low-stock items:");
			for (Map.Entry<String, Integer> item : stock.entrySet()) {
				if (item.getValue() < 5) {
					System.out.println(item.getKey() + ": " + item.getValue());
					hasLowStock = true;
				}
			}
			if (!hasLowStock) {
				System.out.println("No low-stock items.");
			}
			System.out.println("Distinct items: " + stock.size());
		}
	}
}
