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
package me.s3ns3iw00.jcommands.event;

import me.s3ns3iw00.jcommands.Command;
import me.s3ns3iw00.jcommands.CommandResponder;
import org.javacord.api.entity.user.User;

/**
 * The base class of every event that contains the minimum information that is provided with events
 * <p>
 * Contains ta {@link CommandResponder} to be able to send response
 */
public abstract class CommandEvent {

    private final Command command;
    private final User sender;
    private final CommandResponder responder;

    public CommandEvent(Command command, User sender, CommandResponder responder) {
        this.command = command;
        this.sender = sender;
        this.responder = responder;
    }

    public Command getCommand() {
        return command;
    }

    public User getSender() {
        return sender;
    }

    public CommandResponder getResponder() {
        return responder;
    }

}
