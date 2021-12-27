package me.s3ns3iw00.jcommands.event.listener;

import me.s3ns3iw00.jcommands.event.type.BadCategoryEvent;

/**
 * A listener that gets triggered when a {@link me.s3ns3iw00.jcommands.limitation.type.CategoryLimitable} command used in wrong category
 */
public interface BadCategoryEventListener {

    void onBadCategory(BadCategoryEvent event);

}
