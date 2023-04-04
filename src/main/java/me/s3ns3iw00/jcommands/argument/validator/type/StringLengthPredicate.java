package me.s3ns3iw00.jcommands.argument.validator.type;

import me.s3ns3iw00.jcommands.argument.validator.ArgumentPredicate;

import java.util.function.Predicate;

/**
 * An {@link ArgumentPredicate} that can be used on arguments with {@link String} input type
 * <br>
 * It validates the length of the input with an inclusive range
 */
public class StringLengthPredicate implements ArgumentPredicate<String> {

    private final int min;
    private final int max;

    public StringLengthPredicate(int min, int max) {
        this.min = min;
        this.max = max;

        if (min < 0) {
            throw new IllegalArgumentException("The minimum length must be equal or greater than zero");
        }
    }

    @Override
    public Predicate<String> getPredicate() {
        return value -> value.length() < min || value.length() > max;
    }

    /**
     * Constructs the validation with only a minimum length
     *
     * @param min the minimum length
     * @return the {@link StringLengthPredicate}
     */
    public static StringLengthPredicate shorterThan(int min) {
        return new StringLengthPredicate(min, Integer.MAX_VALUE);
    }

    /**
     * Constructs the validation with only a maximum length
     *
     * @param max the maximum length
     * @return the {@link StringLengthPredicate}
     */
    public static StringLengthPredicate longerThan(int max) {
        return new StringLengthPredicate(0, max);
    }

    /**
     * Constructs the validation with a range
     *
     * @param min the minimum length
     * @param max the maximum length
     * @return the {@link StringLengthPredicate}
     */
    public static StringLengthPredicate notInRage(int min, int max) {
        return new StringLengthPredicate(min, max);
    }
}