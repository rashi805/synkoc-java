public class MarksSummary {
	public record Summary(int lowest, int highest, double average) {
	}

	private MarksSummary() {
	}

	public static Summary summarise(int... marks) {
		if (marks == null || marks.length == 0) {
			throw new IllegalArgumentException("At least one mark is required.");
		}
		int lowest = marks[0];
		int highest = marks[0];
		long total = 0;
		for (int mark : marks) {
			lowest = Math.min(lowest, mark);
			highest = Math.max(highest, mark);
			total += mark;
		}
		return new Summary(lowest, highest, (double) total / marks.length);
	}
}
