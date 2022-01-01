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
import me.s3ns3iw00.jcommands.event.listener.BadCategoryEventListener;
import me.s3ns3iw00.jcommands.event.listener.BadChannelEventListener;
import me.s3ns3iw00.jcommands.limitation.type.CategoryLimitation;
import me.s3ns3iw00.jcommands.limitation.type.ChannelLimitation;
import me.s3ns3iw00.jcommands.limitation.type.RoleLimitation;
import me.s3ns3iw00.jcommands.limitation.type.UserLimitation;
import me.s3ns3iw00.jcommands.listener.CommandActionListener;
import me.s3ns3iw00.jcommands.type.ServerCommand;

import java.util.Arrays;

/**
 * Useful class that makes {@code ServerCommand} creations more comfortable
 *
 * @author S3nS3IW00
 */
public class ServerCommandBuilder extends CommandBuilder<ServerCommandBuilder> {

    private final ServerCommand command;

    public ServerCommandBuilder(String name, String description) {
        command = new ServerCommand(name, description);
    }

    /**
     * Calls {@link ServerCommand#addUserLimitation(UserLimitation)}
     *
     * @return this class
     */
    public ServerCommandBuilder userLimitations(UserLimitation... limitations) {
        Arrays.stream(limitations).forEach(command::addUserLimitation);
        return this;
    }

    /**
     * Calls {@link ServerCommand#addChannelLimitation(ChannelLimitation)}
     *
     * @return this class
     */
    public ServerCommandBuilder channelLimitations(ChannelLimitation... limitations) {
        Arrays.stream(limitations).forEach(command::addChannelLimitation);
        return this;
    }

    /**
     * Calls {@link ServerCommand#addCategoryLimitation(CategoryLimitation)}
     *
     * @return this class
     */
    public ServerCommandBuilder categoryLimitations(CategoryLimitation... limitations) {
        Arrays.stream(limitations).forEach(command::addCategoryLimitation);
        return this;
    }

    /**
     * Calls {@link ServerCommand#addRoleLimitation(RoleLimitation)}
     *
     * @return this class
     */
    public ServerCommandBuilder roleLimitations(RoleLimitation... limitations) {
        Arrays.stream(limitations).forEach(command::addRoleLimitation);
        return this;
    }

    /**
     * Calls {@link ServerCommand#setAction(CommandActionListener) setAction}
     *
     * @return this class
     * @deprecated because of the new event system
     */
    @Deprecated
    public ServerCommandBuilder action(CommandActionListener action) {
        command.setAction(action);
        return this;
    }

    /**
     * Calls {@link ServerCommand#setOnBadCategory(BadCategoryEventListener)}
     *
     * @return this class
     */
    public ServerCommandBuilder onBadCategory(BadCategoryEventListener listener) {
        command.setOnBadCategory(listener);
        return this;
    }

    /**
     * Calls {@link ServerCommand#setOnBadChannel(BadChannelEventListener)}
     *
     * @return this class
     */
    public ServerCommandBuilder onBadChannel(BadChannelEventListener listener) {
        command.setOnBadChannel(listener);
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
