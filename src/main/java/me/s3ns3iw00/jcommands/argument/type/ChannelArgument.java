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
package me.s3ns3iw00.jcommands.argument.type;

import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.interaction.SlashCommandOptionType;

/**
 * An argument that has a regex that only accepts inputs what are channel tags
 *
 * @author S3nS3IW00
 */
public class ChannelArgument extends ValueArgument {

    public ChannelArgument(String name, String description) {
        super(name, description, SlashCommandOptionType.CHANNEL, ServerChannel.class);
    }
}
