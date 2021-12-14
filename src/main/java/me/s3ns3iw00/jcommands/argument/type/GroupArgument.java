package me.s3ns3iw00.jcommands.argument.type;

import me.s3ns3iw00.jcommands.argument.Argument;
import me.s3ns3iw00.jcommands.argument.SubArgument;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Represents an argument with {@code SUB_COMMAND_GROUP} type that only can contain {@link ConstantArgument}
 * This is for grouping {@code SUB_COMMAND} options because {@code SUB_COMMANDS} cannot be nested
 */
public class GroupArgument extends SubArgument {

    public GroupArgument(String name, String description) {
        super(name, description, SlashCommandOptionType.SUB_COMMAND_GROUP);
    }

    @Override
    public Object getValue() {
        return getName();
    }

    @Override
    public Class<?> getResultType() {
        return String.class;
    }

    @Override
    public SlashCommandOption getCommandOption() {
        return SlashCommandOption.createWithOptions(getType(), getName(), getDescription(), getArguments().stream().map(Argument::getCommandOption).collect(Collectors.toList()));
    }

    /**
     * Adds a {@link ConstantArgument} to the argument
     *
     * @param argument the argument
     */
    public void addArgument(ConstantArgument argument) {
        getArguments().add(argument);
    }

}
