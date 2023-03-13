package me.s3ns3iw00.jcommands.argument;

import me.s3ns3iw00.jcommands.argument.autocomplete.Autocomplete;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionBuilder;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.ArrayList;
import java.util.List;

/**
 * The autocompletable version of {@link InputArgument}
 */
public abstract class AutocompletableInputArgument extends InputArgument {

    private final List<Autocomplete> autocompletes = new ArrayList<>();

    /**
     * Constructs the argument with the default requirements
     *
     * @param name        the argument's name
     * @param description the argument's description
     * @param type        the type of the input value
     */
    public AutocompletableInputArgument(String name, String description, SlashCommandOptionType type) {
        super(name, description, type);
    }

    /**
     * Runs the default constructor and specifies the result type of the value, that the input will be converted to
     *
     * @param name        the argument's name
     * @param description the argument's description
     * @param type        the type of the value
     * @param resultType  the type of the converted value
     */
    public AutocompletableInputArgument(String name, String description, SlashCommandOptionType type, Class<?> resultType) {
        super(name, description, type, resultType);
    }

    @Override
    public SlashCommandOption getCommandOption() {
        return new SlashCommandOptionBuilder()
                .setName(getName())
                .setDescription(getDescription())
                .setType(getType())
                .setRequired(!isOptional())
                .setAutocompletable(autocompletes.size() > 0)
                .build();
    }

    public List<Autocomplete> getAutocompletes() {
        return autocompletes;
    }

    public void addAutocomplete(Autocomplete autocomplete) {
        getAutocompletes().add(autocomplete);
    }
}
