package me.s3ns3iw00.jcommands.event.type;

import me.s3ns3iw00.jcommands.Command;
import me.s3ns3iw00.jcommands.CommandResponder;
import me.s3ns3iw00.jcommands.argument.ArgumentResult;
import me.s3ns3iw00.jcommands.event.CommandEvent;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

import java.util.Optional;

/**
 * An event that is provided by {@link me.s3ns3iw00.jcommands.event.listener.CommandActionEventListener}
 * <p>
 * Contains the {@link ChannelCategory} where the command was used and
 * the {@link ArgumentResult[]} that is the result of the arguments
 */
public class CommandActionEvent extends CommandEvent {

    private final TextChannel channel;
    private final ArgumentResult[] arguments;

    public CommandActionEvent(Command command, User sender, CommandResponder responder, TextChannel channel, ArgumentResult[] arguments) {
        super(command, sender, responder);
        this.channel = channel;
        this.arguments = arguments;
    }

    /**
     * Gets the channel where the action has been happened
     *
     * @return an {@link Optional#empty()} when the action happened in private message,
     * otherwise an {@link Optional#of(Object)} with the channel
     */
    public Optional<TextChannel> getChannel() {
        return Optional.ofNullable(channel);
    }

    public ArgumentResult[] getArguments() {
        return arguments;
    }

}
