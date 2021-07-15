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
package me.s3ns3iw00.jcommands.builder;

import me.s3ns3iw00.jcommands.Command;
import me.s3ns3iw00.jcommands.CommandAction;
import me.s3ns3iw00.jcommands.argument.Argument;
import me.s3ns3iw00.jcommands.type.PrivateCommand;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

/**
 * Useful class that makes {@code PrivateCommand} creations more comfortable
 *
 * @author S3nS3IW00
 */
public class PrivateCommandBuilder implements CommandBuilder {

    private final PrivateCommand command;

    public PrivateCommandBuilder(String name) {
        command = new PrivateCommand(name);
    }

    /**
     * Calls {@link PrivateCommand#addArguments(Argument...) addArguments}
     *
     * @return this class
     */
    public PrivateCommandBuilder arguments(Argument... arguments) {
        command.addArguments(arguments);
        return this;
    }

    /**
     * Calls {@link PrivateCommand#setUsers(boolean, User...) setAllowedUsers}
     *
     * @return this class
     */
    public PrivateCommandBuilder users(boolean allowed, User... users) {
        command.setUsers(allowed, users);
        return this;
    }

    /**
     * Calls {@link PrivateCommand#setRoles(boolean, Role...) setRoles}
     *
     * @return this class
     */
    public PrivateCommandBuilder roles(boolean needAllRole, Role... roles) {
        command.setRoles(needAllRole, roles);
        return this;
    }

    /**
     * Calls {@link PrivateCommand#setRoleSource(Server) setRoleSource}
     *
     * @return this class
     */
    public PrivateCommandBuilder roleSource(Server server) {
        command.setRoleSource(server);
        return this;
    }

    /**
     * Calls {@link PrivateCommand#setAction(CommandAction) setAction}
     *
     * @return this class
     */
    public PrivateCommandBuilder action(CommandAction action) {
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
