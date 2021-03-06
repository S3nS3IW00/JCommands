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
package me.s3ns3iw00.jcommands.builder;

import me.s3ns3iw00.jcommands.Command;
import me.s3ns3iw00.jcommands.CommandAction;
import me.s3ns3iw00.jcommands.argument.Argument;
import me.s3ns3iw00.jcommands.type.ServerCommand;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;

/**
 * Useful class that makes {@code ServerCommand} creations more comfortable
 *
 * @author S3nS3IW00
 */
public class ServerCommandBuilder implements CommandBuilder {

    private ServerCommand command;

    public ServerCommandBuilder(String name) {
        command = new ServerCommand(name);
    }

    /**
     * Calls {@link ServerCommand#addArguments(Argument...) addArguments}
     *
     * @return this class
     */
    public ServerCommandBuilder arguments(Argument... arguments) {
        command.addArguments(arguments);
        return this;
    }

    /**
     * Calls {@link ServerCommand#setUsers(boolean, User...) setAllowedUsers}
     *
     * @return this class
     */
    public ServerCommandBuilder users(boolean allowed, User... users) {
        command.setUsers(allowed, users);
        return this;
    }

    /**
     * Calls {@link ServerCommand#setChannels(boolean, TextChannel...) setAllowedChannels}
     *
     * @return this class
     */
    public ServerCommandBuilder channels(boolean allowed, TextChannel... channels) {
        command.setChannels(allowed, channels);
        return this;
    }

    /**
     * Calls {@link ServerCommand#setCategories(boolean, ChannelCategory...) setAllowedCategories}
     *
     * @return this class
     */
    public ServerCommandBuilder categories(boolean allowed, ChannelCategory... categories) {
        command.setCategories(allowed, categories);
        return this;
    }

    /**
     * Calls {@link ServerCommand#setRoles(boolean, Role...) setRoles}
     *
     * @return this class
     */
    public ServerCommandBuilder roles(boolean needAllRole, Role... roles) {
        command.setRoles(needAllRole, roles);
        return this;
    }

    /**
     * Calls {@link ServerCommand#setAction(CommandAction) setAction}
     *
     * @return this class
     */
    public ServerCommandBuilder action(CommandAction action) {
        command.setAction(action);
        return this;
    }

    /**
     * @return the command
     */
    @Override
    public Command getCommand() {
        return command;
    }
}
