import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.IntStream;

public class Yatzy {

	private final int[] dice;

	public Yatzy(int d1, int d2, int d3, int d4, int d5) {
		dice = new int[] { d1, d2, d3, d4, d5 };
	}

	public int chance() {
		return sumDice();
	}

	public int yatzy() {
		if (IntStream.of(dice).distinct().count() == 1) {
			return 50;
		}
		return 0;
	}

	public int ones() {
		return sumOf(1);
	}

	public int twos() {
		return sumOf(2);
	}

	public int threes() {
		return sumOf(3);
	}

	public int fours() {
		return sumOf(4);
	}

	public int fives() {
		return sumOf(5);
	}

	public int sixes() {
		return sumOf(6);
	}

	public int pair() {
		return findNOfAKind(2)
				.max()
				.orElse(0) * 2;
	}

	public int twoPairs() {
		var listOfPair = findNOfAKind(2)
				.boxed().collect(toList());

		if (listOfPair.size() == 2) {
			return listOfPair.stream()
					.mapToInt(Integer::intValue)
					.sum() * 2;
		}

		return 0;
	}

	public int threeOfAKind() {
		return findNOfAKind(3)
				.sum() * 3;
	}

	public int fourOfAKind() {
		return findNOfAKind(4)
				.sum() * 4;
	}

	public int smallStraight() {
		if (sameNumbers(asList(1, 2, 3, 4, 5))) {
			return 15;
		}
		return 0;
	}

	public int largeStraight() {
		if (sameNumbers(asList(2, 3, 4, 5, 6))) {
			return 20;
		}
		return 0;
	}

	public int fullHouse() {
		var listOfPair = findNOfAKind(2)
				.boxed().collect(toList());
		var listOfThreeOfAKind = findNOfAKind(3)
				.boxed().collect(toList());

		if (listOfPair.size() == 2 && listOfThreeOfAKind.size() == 1) {
			return sumDice();
		}

		return 0;
	}

	private int sumDice() {
		return IntStream.of(dice).sum();
	}

	private int sumOf(int value) {
		return IntStream.of(dice)
				.filter(die -> die == value)
				.sum();
	}

	private IntStream findNOfAKind(int n) {
		return diceCounts().entrySet().stream()
				.filter(entry -> entry.getValue() >= n)
				.mapToInt(Entry::getKey);
	}

	private Map<Integer, Long> diceCounts() {
		return stream(dice).boxed()
				.collect(groupingBy(identity(), counting()));
	}

	private boolean sameNumbers(List<Integer> straight) {
		return stream(dice)
				.boxed()
				.sorted(Comparator.naturalOrder())
				.collect(toList())
				.equals(straight);
	}

}
