package me.s3ns3iw00.jcommands.event.listener;

import me.s3ns3iw00.jcommands.event.type.ArgumentMismatchEvent;

/**
 * A listener that gets triggered when an argument is not valid
 */
public interface ArgumentMismatchEventListener {

    void onArgumentMismatch(ArgumentMismatchEvent event);

}
