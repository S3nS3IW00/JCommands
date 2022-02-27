package me.s3ns3iw00.jcommands.argument.autocomplete;

import me.s3ns3iw00.jcommands.argument.util.ArgumentState;
import me.s3ns3iw00.jcommands.argument.util.Choice;

import java.util.Arrays;
import java.util.List;

/**
 * Represents an autocomplete
 * Returns exactly the same list of {@link Choice}
 */
public class Autocomplete {

    private final List<Choice> choices;

    public Autocomplete(Choice... choices) {
        this.choices = Arrays.asList(choices);
    }

    public List<Choice> getResult(ArgumentState state) {
        return choices;
    }

}
