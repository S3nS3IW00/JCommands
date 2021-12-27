package me.s3ns3iw00.jcommands.event.type;

import me.s3ns3iw00.jcommands.Command;
import me.s3ns3iw00.jcommands.CommandResponder;
import me.s3ns3iw00.jcommands.event.CommandEvent;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

/**
 * An event that is provided by {@link me.s3ns3iw00.jcommands.event.listener.BadChannelEventListener}
 * <p>
 * Contains the {@link TextChannel} where the command was used
 */
public class BadChannelEvent extends CommandEvent {

    private final TextChannel channel;

    public BadChannelEvent(Command command, User sender, CommandResponder responder, TextChannel channel) {
        super(command, sender, responder);
        this.channel = channel;
    }

    public TextChannel getChannel() {
        return channel;
    }

}
