package me.s3ns3iw00.jcommands.argument.autocomplete.type;

import me.s3ns3iw00.jcommands.argument.autocomplete.Autocomplete;
import me.s3ns3iw00.jcommands.argument.util.ArgumentState;
import me.s3ns3iw00.jcommands.argument.util.Choice;

import java.util.List;

/**
 * A type of {@link Autocomplete} that returns a list of {@link Choice} based on conditions
 */
public class ConditionalAutocomplete extends Autocomplete {

    private final ConditionResult conditionResult;

    public ConditionalAutocomplete(ConditionResult conditionResult) {
        this.conditionResult = conditionResult;
    }

    @Override
    public List<Choice> getResult(ArgumentState state) {
        return conditionResult.getChoices(state);
    }

    /**
     * Functional interface that returns the list of {@link Choice} based on the {@link ArgumentState}
     */
    public interface ConditionResult {

        List<Choice> getChoices(ArgumentState state);

    }


}