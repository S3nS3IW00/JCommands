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

import me.s3ns3iw00.jcommands.CommandErrorType;
import me.s3ns3iw00.jcommands.CommandResponder;

/**
 * The error listener
 *
 * @author S3nS3IW00
 */
public interface CommandErrorListener {

    /**
     * A method signature that represents an error
     *
     * @param type        of the error
     * @param responder the responder to send respond
     */
    void onError(CommandErrorType type, CommandResponder responder);

}
