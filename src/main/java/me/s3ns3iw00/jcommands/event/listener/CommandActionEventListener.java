package me.s3ns3iw00.jcommands.event.listener;

import me.s3ns3iw00.jcommands.event.type.CommandActionEvent;

/**
 * A listener that gets triggered when on every usage of a command
 */
public interface CommandActionEventListener {

    void onAction(CommandActionEvent event);

}
