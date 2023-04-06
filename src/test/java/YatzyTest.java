import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(ReplaceUnderscores.class)
class YatzyTest {

    @ParameterizedTest(name = "should return scores {1} when the dice read {0}")
    @CsvSource(value = {
            "2,3,4,5,1 -> 15",
            "3,3,4,5,1 -> 16",
            "1,1,3,3,6 -> 14",
            "4,5,5,6,1 -> 21"
    }, delimiterString = " -> ")
    void chance(@ConvertWith(DiceConverter.class) Dice dice,
                Integer score) {
        assertThat(new Yatzy(dice).chance()).isEqualTo(score);
    }

    @ParameterizedTest(name = "should return scores {1} when the dice read {0}")
    @CsvSource(value = {
            "4,4,4,4,4 -> 50",
            "6,6,6,6,6 -> 50",
            "6,6,6,6,3 -> 0",
            "1,1,1,1,1 -> 50",
            "1,1,1,2,1 -> 0"
    }, delimiterString = " -> ")
    void yatzy(@ConvertWith(DiceConverter.class) Dice dice,
               Integer score) {
        assertThat(new Yatzy(dice).yatzy()).isEqualTo(score);
    }

    @ParameterizedTest(name = "should return scores {1} when the dice read {0}")
    @CsvSource(value = {
            "1,2,3,4,5 -> 1",
            "1,2,1,4,5 -> 2",
            "6,2,2,4,5 -> 0",
            "1,2,1,1,1 -> 4",
            "3,3,3,4,5 -> 0"
    }, delimiterString = " -> ")
    void ones(@ConvertWith(DiceConverter.class) Dice dice,
              Integer score) {
        assertThat(new Yatzy(dice).ones()).isEqualTo(score);
    }

    @ParameterizedTest(name = "should return scores {1} when the dice read {0}")
    @CsvSource(value = {
            "1,2,3,2,6 -> 4",
            "2,2,2,2,2 -> 10",
            "2,3,2,5,1 -> 4"
    }, delimiterString = " -> ")
    void twos(@ConvertWith(DiceConverter.class) Dice dice,
              Integer score) {
        assertThat(new Yatzy(dice).twos()).isEqualTo(score);
    }

    @ParameterizedTest(name = "should return scores {1} when the dice read {0}")
    @CsvSource(value = {
            "1,2,3,2,3 -> 6",
            "2,3,3,3,3 -> 12"
    }, delimiterString = " -> ")
    void threes(@ConvertWith(DiceConverter.class) Dice dice,
                Integer score) {
        assertThat(new Yatzy(dice).threes()).isEqualTo(score);
    }

    @ParameterizedTest(name = "should return scores {1} when the dice read {0}")
    @CsvSource(value = {
            "4,4,4,5,5 -> 12",
            "4,4,5,5,5 -> 8",
            "4,5,5,5,5 -> 4",
            "1,1,2,4,4 -> 8"
    }, delimiterString = " -> ")
    void fours(@ConvertWith(DiceConverter.class) Dice dice,
               Integer score) {
        assertThat(new Yatzy(dice).fours()).isEqualTo(score);
    }

    @ParameterizedTest(name = "should return scores {1} when the dice read {0}")
    @CsvSource(value = {
            "4,4,4,5,5 -> 10",
            "4,4,5,5,5 -> 15",
            "4,5,5,5,5 -> 20"
    }, delimiterString = " -> ")
    void fives(@ConvertWith(DiceConverter.class) Dice dice,
               Integer score) {
        assertThat(new Yatzy(dice).fives()).isEqualTo(score);
    }

    @ParameterizedTest(name = "should return scores {1} when the dice read {0}")
    @CsvSource(value = {
            "4,4,4,5,5 -> 0",
            "4,4,6,5,5 -> 6",
            "6,5,6,6,5 -> 18"
    }, delimiterString = " -> ")
    void sixes(@ConvertWith(DiceConverter.class) Dice dice,
               Integer score) {
        assertThat(new Yatzy(dice).sixes()).isEqualTo(score);
    }

    @ParameterizedTest(name = "should return scores {1} when the dice read {0}")
    @CsvSource(value = {
            "3,4,3,5,6 -> 6",
            "5,3,3,3,5 -> 10",
            "5,3,6,6,5 -> 12",
            "3,3,3,4,4 -> 8",
            "1,1,6,2,6 -> 12",
            "3,3,3,4,1 -> 6",
            "3,3,3,3,1 -> 6"
    }, delimiterString = " -> ")
    void pair(@ConvertWith(DiceConverter.class) Dice dice,
              Integer score) {
        assertThat(new Yatzy(dice).pair()).isEqualTo(score);
    }

    @ParameterizedTest(name = "should return scores {1} when the dice read {0}")
    @CsvSource(value = {
            "3,3,5,4,5 -> 16",
            "3,3,5,5,5 -> 16",
            "1,1,2,3,3 -> 8",
            "1,1,2,3,4 -> 0",
            "1,1,2,2,2 -> 6"
    }, delimiterString = " -> ")
    void two_pairs(@ConvertWith(DiceConverter.class) Dice dice,
                   Integer score) {
        assertThat(new Yatzy(dice).twoPairs()).isEqualTo(score);
    }

    @ParameterizedTest(name = "should return scores {1} when the dice read {0}")
    @CsvSource(value = {
            "3,3,3,4,5 -> 9",
            "5,3,5,4,5 -> 15",
            "3,3,3,3,5 -> 9",
            "3,3,3,3,3 -> 9",
            "3,3,4,5,6 -> 0",
            "3,3,3,3,1 -> 9"
    }, delimiterString = " -> ")
    void three_of_a_kind(@ConvertWith(DiceConverter.class) Dice dice,
                         Integer score) {
        assertThat(new Yatzy(dice).threeOfAKind()).isEqualTo(score);
    }

    @ParameterizedTest(name = "should return scores {1} when the dice read {0}")
    @CsvSource(value = {
            "3,3,3,3,5 -> 12",
            "5,5,5,4,5 -> 20",
            "2,2,2,2,5 -> 8",
            "2,2,2,5,5 -> 0",
            "2,2,2,2,2 -> 8"
    }, delimiterString = " -> ")
    void four_of_a_kind(@ConvertWith(DiceConverter.class) Dice dice,
                        Integer score) {
        assertThat(new Yatzy(dice).fourOfAKind()).isEqualTo(score);
    }

    @ParameterizedTest(name = "should return scores {1} when the dice read {0}")
    @CsvSource(value = {
            "1,2,3,4,5 -> 15",
            "2,3,4,5,1 -> 15",
            "1,2,2,4,5 -> 0"
    }, delimiterString = " -> ")
    void small_straight(@ConvertWith(DiceConverter.class) Dice dice,
                        Integer score) {
        assertThat(new Yatzy(dice).smallStraight()).isEqualTo(score);
    }

    @ParameterizedTest(name = "should return scores {1} when the dice read {0}")
    @CsvSource(value = {
            "6,2,3,4,5 -> 20",
            "2,3,4,5,6 -> 20",
            "1,2,2,4,5 -> 0"
    }, delimiterString = " -> ")
    void large_straight(@ConvertWith(DiceConverter.class) Dice dice,
                        Integer score) {
        assertThat(new Yatzy(dice).largeStraight()).isEqualTo(score);
    }

    @ParameterizedTest(name = "should return scores {1} when the dice read {0}")
    @CsvSource(value = {
            "6,2,2,2,6 -> 18",
            "2,3,4,5,6 -> 0",
            "1,1,2,2,2 -> 8",
            "2,2,3,3,4 -> 0",
            "4,4,4,4,4 -> 0"
    }, delimiterString = " -> ")
    void full_house(@ConvertWith(DiceConverter.class) Dice dice,
                    Integer score) {
        assertThat(new Yatzy(dice).fullHouse()).isEqualTo(score);
    }

    private static class DiceConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            if (source instanceof String s) {
                return new Dice(getPlayerScores(s));
            }
            throw new IllegalArgumentException("Conversion not possible.");
        }

        private static List<Integer> getPlayerScores(String s) {
            return Stream.of(s.split(","))
                    .map(Integer::valueOf)
                    .toList();
        }
    }

}
