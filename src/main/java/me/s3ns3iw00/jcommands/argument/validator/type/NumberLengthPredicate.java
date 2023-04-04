package me.s3ns3iw00.jcommands.argument.validator.type;

import me.s3ns3iw00.jcommands.argument.validator.ArgumentPredicate;

import java.util.function.Predicate;

/**
 * An {@link ArgumentPredicate} that can be used on arguments with {@link Long} input type
 * <br>
 * It validates the input with an inclusive range
 */
public class NumberLengthPredicate implements ArgumentPredicate<Long> {

    private final long min;
    private final long max;

    public NumberLengthPredicate(long min, long max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Predicate<Long> getPredicate() {
        return value -> value < min || value > max;
    }

    /**
     * Constructs the validation with only a minimum
     *
     * @param min the minimum
     * @return the {@link NumberLengthPredicate}
     */
    public static NumberLengthPredicate lessThan(long min) {
        return new NumberLengthPredicate(min, Long.MAX_VALUE);
    }

    /**
     * Constructs the validation with only a maximum
     *
     * @param max the maximum
     * @return the {@link NumberLengthPredicate}
     */
    public static NumberLengthPredicate greaterThan(long max) {
        return new NumberLengthPredicate(Long.MIN_VALUE, max);
    }

    /**
     * Constructs the validation with a range
     *
     * @param min the minimum
     * @param max the maximum
     * @return the {@link NumberLengthPredicate}
     */
    public static NumberLengthPredicate notInRage(long min, long max) {
        return new NumberLengthPredicate(min, max);
    }

}
