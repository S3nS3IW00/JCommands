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
import me.s3ns3iw00.jcommands.argument.type.RegexArgument;

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
    private final LinkedList<List<Argument>> arguments = new LinkedList<>();
    private Optional<CommandAction> action = Optional.empty();

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Adds a list of argument which at least one of are acceptable at the current index
     *
     * @param arguments the list of the arguments
     */
    public void addArguments(Argument... arguments) {
        this.arguments.add(new ArrayList<>(Arrays.asList(arguments)));
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
     * Prints the valid usage of the command with all the acceptable arguments
     *
     * @return a string of the usage
     */
    public String getUsage() {
        StringBuilder usage = new StringBuilder("/" + getName());
        if (getArguments().size() == 0) {
            return usage.toString();
        }
        usage.append(" ");
        for (int i = 0; i < getArguments().size(); i++) {
            List<Argument> arguments = getArguments().get(i);

            for (int j = 0; j < arguments.size(); j++) {
                Argument argument = arguments.get(j);

                if (argument instanceof RegexArgument) {
                    usage.append("<").append(argument.getName()).append(">");
                } else {
                    usage.append(argument.getName());
                }
                if (j + 1 < arguments.size()) usage.append("|");
            }
            if (i + 1 < getArguments().size()) usage.append(" ");
        }
        return usage.toString();
    }

    /**
     * @return the command's name
     */
    String getName() {
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
    LinkedList<List<Argument>> getArguments() {
        return arguments;
    }

    /**
     * @return the command action instance
     */
    Optional<CommandAction> getAction() {
        return action;
    }

}
