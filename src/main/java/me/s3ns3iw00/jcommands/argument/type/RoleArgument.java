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
import org.javacord.api.entity.permission.Role;
import org.javacord.api.interaction.SlashCommandOptionType;

/**
 * An argument that only accepts role as input and returns a {@link Role}
 */
public class RoleArgument<O> extends InputArgument<Role, O> {

    public RoleArgument(String name, String description, Class<O> resultType) {
        super(name, description, SlashCommandOptionType.ROLE, resultType);
    }

}
