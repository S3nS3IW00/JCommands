package me.s3ns3iw00.jcommands.argument.concatenation.type;

import me.s3ns3iw00.jcommands.argument.ArgumentResult;
import me.s3ns3iw00.jcommands.argument.concatenation.Concatenator;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents a type of {@link Concatenator} that concatenates the arguments' result to a {@link String} separated with a delimiter
 */
public class StringConcatenator extends Concatenator {

    private final String delimiter;

    public StringConcatenator(String delimiter) {
        super(String.class);
        this.delimiter = delimiter;
    }

    /**
     * Concatenates arguments into {@link String} separated with the specified delimiter
     *
     * @param results that need to be concatenated
     * @return the concatenated String
     */
    @Override
    public Object concatenate(ArgumentResult... results) {
        return Arrays.stream(results).map(result -> result.get().toString()).collect(Collectors.joining(delimiter));
    }

}
