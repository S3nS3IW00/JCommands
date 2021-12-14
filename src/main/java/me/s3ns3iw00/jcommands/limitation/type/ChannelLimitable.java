/*
 * Copyright (C) 2021 S3nS3IW00
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Commands that implements this interface can be limited for channels
 */
public interface ChannelLimitable {

    List<ChannelLimitation> channelLimitations = new ArrayList<>();

    default void addChannelLimitation(ChannelLimitation limitation) {
        channelLimitations.add(limitation);
    }

    default List<ChannelLimitation> getChannelLimitations() {
        return Collections.unmodifiableList(channelLimitations);
    }

}