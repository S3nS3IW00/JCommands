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

import me.s3ns3iw00.jcommands.argument.Argument;

import java.util.*;

/**
 * A class that represents a command
 *
 * @author S3nS3IW00
 */
public class Command {

    /**
     * These guys are nice
     */
    private final String name, description;
    private final LinkedList<Argument> arguments = new LinkedList<>();
    private Optional<CommandAction> action = Optional.empty();

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Adds an argument to the command
     *
     * @param argument the argument
     */
    public void addArgument(Argument argument) {
        this.arguments.add(argument);
    }

    /**
     * Sets the action listener of the command
     *
     * @param action is the listener object
     */
    public void setAction(CommandAction action) {
        this.action = Optional.of(action);
    }

    /**
     * @return the command's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the command's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the list of the arguments
     */
    public LinkedList<Argument> getArguments() {
        return arguments;
    }

    /**
     * @return the command action instance
     */
    Optional<CommandAction> getAction() {
        return action;
    }

}
