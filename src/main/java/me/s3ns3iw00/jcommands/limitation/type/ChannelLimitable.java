/*
 * Copyright (C) 2022 S3nS3IW00
 *
 * This file is part of JCommands.
 *
 * JCommands is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser general Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * JCommands is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package me.s3ns3iw00.jcommands.limitation.type;

import me.s3ns3iw00.jcommands.event.listener.BadChannelEventListener;

import java.util.Optional;
import java.util.Set;

/**
 * Commands that implements this interface can be limited for channels
 */
public interface ChannelLimitable {

    default void addChannelLimitation(ChannelLimitation limitation) {
        getChannelLimitations().add(limitation);
    }

    Set<ChannelLimitation> getChannelLimitations();

    void setOnBadChannel(BadChannelEventListener listener);

    Optional<BadChannelEventListener> getBadChannelListener();

}
