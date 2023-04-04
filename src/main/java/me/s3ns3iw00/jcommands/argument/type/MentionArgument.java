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
package me.s3ns3iw00.jcommands.argument.type;

import me.s3ns3iw00.jcommands.argument.InputArgument;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandOptionType;

/**
 * An argument that has a regex that only accepts {@link User} input
 *
 * @author S3nS3IW00
 */
public class MentionArgument<O> extends InputArgument<User, O> {

    public MentionArgument(String name, String description, Class<O> resultType) {
        super(name, description, SlashCommandOptionType.USER, resultType);
    }

}
