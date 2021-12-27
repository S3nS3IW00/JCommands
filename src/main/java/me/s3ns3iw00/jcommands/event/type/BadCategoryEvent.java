package me.s3ns3iw00.jcommands.event.type;

import me.s3ns3iw00.jcommands.Command;
import me.s3ns3iw00.jcommands.CommandResponder;
import me.s3ns3iw00.jcommands.event.CommandEvent;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.user.User;

/**
 * An event that is provided by {@link me.s3ns3iw00.jcommands.event.listener.BadCategoryEventListener}
 * <p>
 * Contains the {@link ChannelCategory} where the command was used
 */
public class BadCategoryEvent extends CommandEvent {

    private final ChannelCategory category;

    public BadCategoryEvent(Command command, User sender, CommandResponder responder, ChannelCategory category) {
        super(command, sender, responder);
        this.category = category;
    }

    public ChannelCategory getCategory() {
        return category;
    }

}
