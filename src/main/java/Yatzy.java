import java.util.Map;
import java.util.Map.Entry;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;

import static java.util.List.of;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Yatzy {

    public static final int YATZY = 50;
    public static final int NULL = 0;
    private final Dice dice;

    public Yatzy(Dice dice) {
        this.dice = dice;
    }

    public int chance() {
        return dice.sum();
    }

    public int yatzy() {
        return dice.isAllTheSame() ? YATZY : NULL;
    }

    public int ones() {
        return dice.sumOf(1);
    }

    public int twos() {
        return dice.sumOf(2);
    }

    public int threes() {
        return dice.sumOf(3);
    }

    public int fours() {
        return dice.sumOf(4);
    }

    public int fives() {
        return dice.sumOf(5);
    }

    public int sixes() {
        return dice.sumOf(6);
    }

    public int pair() {
        return 2 * findNOfAKind(2)
                .max()
                .orElse(NULL);
    }

    public int twoPairs() {
        var hasTwoPairs = findNOfAKind(2).count() == 2;
        IntSupplier computeScores = () -> findNOfAKind(2).sum() * 2;

        return hasTwoPairs ? computeScores.getAsInt() : NULL;
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
        return dice.sameNumbers(of(1, 2, 3, 4, 5)) ? 15 : NULL;
    }

    public int largeStraight() {
        return dice.sameNumbers(of(2, 3, 4, 5, 6)) ? 20 : NULL;
    }

    public int fullHouse() {
        var hasTwoPair = findNOfAKind(2).count() == 2;
        var hasThreeOfAKind = findNOfAKind(3).count() == 1;
        var hasFullHouse = hasTwoPair && hasThreeOfAKind;

        return hasFullHouse ? dice.sum() : NULL;
    }

    private IntStream findNOfAKind(int n) {
        return diceCounts().entrySet().stream()
                .filter(entry -> entry.getValue() >= n)
                .mapToInt(Entry::getKey);
    }

    private Map<Integer, Long> diceCounts() {
        return dice.dice().stream()
                .collect(groupingBy(identity(), counting()));
    }

}
