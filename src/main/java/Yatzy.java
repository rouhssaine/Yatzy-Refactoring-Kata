import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

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
		List<Integer> diceValueHavingPair = findNOfAKind(2)
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
		int[] tallies;
		tallies = new int[6];
		tallies[dice[0] - 1] += 1;
		tallies[dice[1] - 1] += 1;
		tallies[dice[2] - 1] += 1;
		tallies[dice[3] - 1] += 1;
		tallies[dice[4] - 1] += 1;
		if (tallies[0] == 1 &&
				tallies[1] == 1 &&
				tallies[2] == 1 &&
				tallies[3] == 1 &&
				tallies[4] == 1)
			return 15;
		return 0;
	}

	public int largeStraight() {
		int[] tallies;
		tallies = new int[6];
		tallies[dice[0] - 1] += 1;
		tallies[dice[1] - 1] += 1;
		tallies[dice[2] - 1] += 1;
		tallies[dice[3] - 1] += 1;
		tallies[dice[4] - 1] += 1;
		if (tallies[1] == 1 &&
				tallies[2] == 1 &&
				tallies[3] == 1 &&
				tallies[4] == 1
				&& tallies[5] == 1)
			return 20;
		return 0;
	}

	public int fullHouse() {
		int[] tallies;
		boolean _2 = false;
		int i;
		int _2_at = 0;
		boolean _3 = false;
		int _3_at = 0;

		tallies = new int[6];
		tallies[dice[0] - 1] += 1;
		tallies[dice[1] - 1] += 1;
		tallies[dice[2] - 1] += 1;
		tallies[dice[3] - 1] += 1;
		tallies[dice[4] - 1] += 1;

		for (i = 0; i != 6; i += 1)
			if (tallies[i] == 2) {
				_2 = true;
				_2_at = i + 1;
			}

		for (i = 0; i != 6; i += 1)
			if (tallies[i] == 3) {
				_3 = true;
				_3_at = i + 1;
			}

		if (_2 && _3)
			return _2_at * 2 + _3_at * 3;
		else
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
}
