import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
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
		return IntStream.of(dice).sum();
	}

	public int yatzy() {
		if (IntStream.of(dice).distinct().count() == 1) {
			return 50;
		}
		return 0;
	}

	public int ones() {
		return sumByValue(1);
	}

	public int twos() {
		return sumByValue(2);
	}

	public int threes() {
		return sumByValue(3);
	}

	public int fours() {
		return sumByValue(4);
	}

	public int fives() {
		return sumByValue(5);
	}

	public int sixes() {
		return sumByValue(6);
	}

	public int pair() {
		return findNOfAKind(2)
				.max()
				.orElse(0) * 2;
	}

	public int twoPairs() {
		var diceValueHavingPair = findNOfAKind(2)
				.boxed().collect(toList());

		if (diceValueHavingPair.size() != 2) {
			return 0;
		}
		return diceValueHavingPair.stream()
				.collect(summingInt(x -> x)) * 2;
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
		return sameNumbers(asList(1, 2, 3, 4, 5)) ? 15 : 0;
	}

	public int largeStraight() {
		return sameNumbers(asList(2, 3, 4, 5, 6)) ? 20 : 0;
	}

	public int fullHouse() {
		var listOfPair = findNOfAKind(2)
				.boxed().collect(toList());
		var listOfThreeOfAKind = findNOfAKind(3)
				.boxed().collect(toList());

		if (listOfPair.size() == 2 && listOfThreeOfAKind.size() == 1) {
			return IntStream.of(dice).sum();
		}

		return 0;
	}

	private int sumByValue(int value) {
		return IntStream.of(dice)
				.filter(die -> die == value)
				.sum();
	}

	private IntStream findNOfAKind(int n) {
		return diceCounts().entrySet().stream()
				.filter(entry -> entry.getValue() >= n)
				.mapToInt(Entry::getKey);
	}

	private Map<Integer, Integer> diceCounts() {
		return stream(dice).boxed()
				.collect(groupingBy(identity(),
						collectingAndThen(counting(), Long::intValue)));
	}

	private boolean sameNumbers(List<Integer> straight) {
		return stream(dice)
				.boxed()
				.sorted(Comparator.naturalOrder())
				.collect(toList())
				.equals(straight);
	}

}
