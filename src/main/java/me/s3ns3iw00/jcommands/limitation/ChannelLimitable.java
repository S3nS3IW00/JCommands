/*
 * Copyright (C) 2020 S3nS3IW00
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
package me.s3ns3iw00.jcommands.limitation;

import org.javacord.api.entity.channel.TextChannel;

import java.util.List;

/**
 * Commands that implements this interface will be able to limited for channels
 */
public interface ChannelLimitable {

    /**
     * Sets the channels where the command will be allowed
     *
     * @param channels the list of the channels
     */
    void setChannels(boolean allowed, TextChannel... channels);

    /**
     * @return the list of the channels
     */
    List<TextChannel> getChannels();

    /**
     * @return true or false depends on if the channel limitation is allowing or disallowing
     */
    boolean isAllowedChannels();

}
