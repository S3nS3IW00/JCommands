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
import me.s3ns3iw00.jcommands.argument.InputArgument;
import me.s3ns3iw00.jcommands.listener.CommandActionListener;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;

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
    private Optional<CommandActionListener> action = Optional.empty();

    public Command(String name, String description) {
        this.name = name;
        this.description = description;

        if (!name.matches("^[\\w-]{1,32}$")) {
            throw new IllegalArgumentException("Name must match the following regex: ^[\\w-]{1,32}$");
        }
    }

    /**
     * Adds an argument to the command
     *
     * @param argument the argument
     */
    public void addArgument(Argument argument) {
        if (arguments.size() > 0 && (arguments.getLast() instanceof InputArgument) && ((InputArgument) arguments.getLast()).isOptional()) {
            throw new IllegalStateException("Cannot add argument after an optional argument!");
        }

        this.arguments.add(argument);
    }

    /**
     * Adds arguments to the command
     *
     * @param arguments a list of argument
     */
    public void addArgument(Argument... arguments) {
        Arrays.stream(arguments).forEach(this::addArgument);
    }

    /**
     * Sets the action listener of the command
     *
     * @param action is the listener object
     */
    public void setAction(CommandActionListener action) {
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
    Optional<CommandActionListener> getAction() {
        return action;
    }

}
