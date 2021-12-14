package me.s3ns3iw00.jcommands.argument;

import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.LinkedList;

/**
 * Represents a sub argument
 * Classes that extends this class can have arguments
 */
public abstract class SubArgument extends Argument {

    private final LinkedList<Argument> arguments = new LinkedList<>();

    /**
     * Constructs the argument with the default requirements
     *
     * @param name        the argument's name
     * @param description the argument's description
     * @param type        the type of the input value
     */
    public SubArgument(String name, String description, SlashCommandOptionType type) {
        super(name, description, type);
    }

    public LinkedList<Argument> getArguments() {
        return arguments;
    }
}
