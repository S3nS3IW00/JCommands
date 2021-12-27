package me.s3ns3iw00.jcommands.event.type;

import me.s3ns3iw00.jcommands.Command;
import me.s3ns3iw00.jcommands.CommandResponder;
import me.s3ns3iw00.jcommands.argument.Argument;
import me.s3ns3iw00.jcommands.event.CommandEvent;
import org.javacord.api.entity.user.User;

/**
 * An event that is provided by {@link me.s3ns3iw00.jcommands.event.listener.ArgumentMismatchEventListener}
 * <p>
 * Contains the {@link Argument} that was not valid
 */
public class ArgumentMismatchEvent extends CommandEvent {

    private final Argument argument;

    public ArgumentMismatchEvent(Command command, User sender, CommandResponder responder, Argument argument) {
        super(command, sender, responder);
        this.argument = argument;
    }

    public Argument getArgument() {
        return argument;
    }

}
