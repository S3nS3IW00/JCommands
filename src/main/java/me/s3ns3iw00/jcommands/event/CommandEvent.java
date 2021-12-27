package me.s3ns3iw00.jcommands.event;

import me.s3ns3iw00.jcommands.Command;
import me.s3ns3iw00.jcommands.CommandResponder;
import org.javacord.api.entity.user.User;

/**
 * The base class of every event that contains the minimum information that is provided with events
 * <p>
 * Contains ta {@link CommandResponder} to be able to send response
 */
public abstract class CommandEvent {

    private final Command command;
    private final User sender;
    private final CommandResponder responder;

    public CommandEvent(Command command, User sender, CommandResponder responder) {
        this.command = command;
        this.sender = sender;
        this.responder = responder;
    }

    public Command getCommand() {
        return command;
    }

    public User getSender() {
        return sender;
    }

    public CommandResponder getResponder() {
        return responder;
    }

}
