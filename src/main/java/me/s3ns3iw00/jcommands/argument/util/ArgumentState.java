package me.s3ns3iw00.jcommands.argument.util;

import me.s3ns3iw00.jcommands.Command;
import me.s3ns3iw00.jcommands.argument.Argument;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents a state of argument
 * It contains the {@link Command}, the {@link Argument} and its value, the {@link TextChannel} if presents and the {@link User}
 */
public class ArgumentState {

    private final Command command;
    private final Argument argument;
    private final TextChannel channel;
    private final User sender;
    private final Object value;

    public ArgumentState(Command command, Argument argument, TextChannel channel, User sender, Object value) {
        this.command = command;
        this.argument = argument;
        this.channel = channel;
        this.sender = sender;
        this.value = value;
    }

    public Command getCommand() {
        return command;
    }

    public Argument getArgument() {
        return argument;
    }

    public Optional<TextChannel> getChannel() {
        return Optional.ofNullable(channel);
    }

    public User getSender() {
        return sender;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArgumentState that = (ArgumentState) o;
        return Objects.equals(command, that.command) && Objects.equals(argument, that.argument) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, argument, value);
    }
}
