package me.s3ns3iw00.jcommands.argument.type;

import me.s3ns3iw00.jcommands.argument.Argument;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Represents an argument with {@code SUB_COMMAND_GROUP} type that only can contain {@link ConstantArgument}
 * This is for grouping {@code SUB_COMMAND} options because {@code SUB_COMMANDS} cannot be nested
 */
public class GroupArgument implements Argument {

    private final String name, description;
    private final LinkedList<Argument> arguments = new LinkedList<>();

    public GroupArgument(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Object getValue() {
        return name;
    }

    @Override
    public Class<?> getResultType() {
        return String.class;
    }

    @Override
    public SlashCommandOption getCommandOption() {
        return SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND_GROUP, getName(), getDescription(), getArguments().stream().map(Argument::getCommandOption).collect(Collectors.toList()));
    }

    /**
     * Adds a {@link ConstantArgument} to the argument
     *
     * @param argument the argument
     */
    public void addArgument(ConstantArgument argument) {
        arguments.add(argument);
    }

    public LinkedList<Argument> getArguments() {
        return arguments;
    }

}
