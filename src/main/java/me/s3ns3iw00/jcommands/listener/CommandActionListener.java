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
package me.s3ns3iw00.jcommands.listener;

import me.s3ns3iw00.jcommands.CommandResponder;
import me.s3ns3iw00.jcommands.argument.ArgumentResult;
import me.s3ns3iw00.jcommands.event.listener.CommandActionEventListener;
import org.javacord.api.entity.user.User;

/**
 * The action listener
 *
 * @author S3nS3IW00
 * @deprecated because of the new event system
 * use {@link CommandActionEventListener} instead
 */
@Deprecated
public interface CommandActionListener {

    /**
     * A method signature that represents an action
     *
     * @param sender    is the User who sent the message
     * @param args      is the array that contains the converted results
     * @param responder is a class with an immediate or a late response can be sent
     *                  NOTE: The late response need to be sent within 15 minutes, otherwise Discord simply drops it
     */
    void onCommand(User sender, ArgumentResult[] args, CommandResponder responder);

}
