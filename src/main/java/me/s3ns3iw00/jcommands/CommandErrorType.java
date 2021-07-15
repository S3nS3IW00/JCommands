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
package me.s3ns3iw00.jcommands;

/**
 * Enum with error type constants
 *
 * @author S3nS3IW00
 */
public enum CommandErrorType {

    /**
     * Occurs when no command is registered at the source with the given name.
     */
    INVALID_COMMAND,

    /**
     * Occurs when one or more of the arguments are missing or not matching the pattern.
     */
    BAD_ARGUMENTS,

    /**
     * Occurs when the sender wants to use the command in a category where it is not allowed.
     */
    BAD_CATEGORY,

    /**
     * Occurs when the sender wants to use the command in a channel where it is not allowed.
     */
    BAD_CHANNEL,

    /**
     * Occurs when the sender is not allowed to use this command or the sender does not have one of or all the roles that need to use the command.
     */
    NO_PERMISSION

}
