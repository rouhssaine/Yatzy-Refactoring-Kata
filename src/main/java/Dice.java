import java.util.Comparator;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;

public record Dice(List<Integer> dice) {

    public Dice {
        checkArgument(nonNull(dice), "dice list cannot be null");
        checkArgument(dice.size() == 5, "you must have five dice");
    }

    public boolean isAllTheSame() {
        return this.dice.stream()
                .distinct().count() == 1;
    }

    public int sum() {
        return this.dice.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int sumOf(int value) {
        return this.dice.stream()
                .mapToInt(Integer::intValue)
                .filter(die -> die == value)
                .sum();
    }

    public boolean sameNumbers(List<Integer> straight) {
        return this.dice.stream()
                .sorted(Comparator.naturalOrder())
                .toList()
                .equals(straight);
    }
}
