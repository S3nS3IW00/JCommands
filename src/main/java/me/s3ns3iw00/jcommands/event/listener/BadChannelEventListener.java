package me.s3ns3iw00.jcommands.event.listener;

import me.s3ns3iw00.jcommands.event.type.BadChannelEvent;

/**
 * A listener that gets triggered when a {@link me.s3ns3iw00.jcommands.limitation.type.ChannelLimitable} command used in wrong channel
 */
public interface BadChannelEventListener {

    void onBadChannel(BadChannelEvent event);

}
