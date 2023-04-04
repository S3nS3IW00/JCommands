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
package me.s3ns3iw00.jcommands.event.type;

import me.s3ns3iw00.jcommands.Command;
import me.s3ns3iw00.jcommands.CommandResponder;
import me.s3ns3iw00.jcommands.argument.Argument;
import me.s3ns3iw00.jcommands.event.CommandEvent;
import org.javacord.api.entity.user.User;

/**
 * An event that is provided by {@link me.s3ns3iw00.jcommands.event.listener.ArgumentMismatchEventListener}
 * <p>
 * Contains the {@link Argument} that was not valid
 */
public class ArgumentMismatchEvent extends CommandEvent {

    private final Argument argument;

    public ArgumentMismatchEvent(Command command, User sender, CommandResponder responder, Argument argument) {
        super(command, sender, responder);
        this.argument = argument;
    }

    public Argument getArgument() {
        return argument;
    }

}
