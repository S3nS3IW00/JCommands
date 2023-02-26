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
package me.s3ns3iw00.jcommands;

import me.s3ns3iw00.jcommands.argument.Argument;
import me.s3ns3iw00.jcommands.argument.InputArgument;
import me.s3ns3iw00.jcommands.argument.concatenation.Concatenator;
import me.s3ns3iw00.jcommands.event.listener.ArgumentMismatchEventListener;
import me.s3ns3iw00.jcommands.event.listener.CommandActionEventListener;
import org.javacord.api.entity.permission.PermissionType;

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
    private final Map<Concatenator, LinkedList<Argument>> concatenators = new LinkedHashMap<>();

    private final Set<PermissionType> defaultPermissions = new HashSet<>();

    private boolean onlyForAdministrators = false;

    private CommandActionEventListener actionListener;
    private ArgumentMismatchEventListener argumentMismatchListener;

    /**
     * Default constructor
     *
     * @param name        the name of the command
     *                    Its length must between 1 and 32
     *                    Can contain only:
     *                    - word characters
     *                    - numbers
     *                    - '-' characters
     * @param description the description of the command
     *                    Its length must between 1 and 100
     */
    public Command(String name, String description) {
        this.name = name;
        this.description = description;

        if (!name.matches("^[\\w-]{1,32}$")) {
            throw new IllegalArgumentException("Name can contain only word characters, numbers or '-' characters, and its length must between 1 and 32");
        }

        if (description.length() < 1 || description.length() > 100) {
            throw new IllegalArgumentException("Description's length must between 1 and 100");
        }
    }

    /**
     * Adds an argument to the command
     *
     * @param argument the argument
     */
    public void addArgument(Argument argument) {
        if (arguments.size() > 0 &&
                (arguments.getLast() instanceof InputArgument) &&
                ((InputArgument) arguments.getLast()).isOptional() &&
                !((InputArgument) argument).isOptional()) {
            throw new IllegalStateException("Cannot add non-optional argument after an optional argument!");
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
     * Adds a concatenator to the command
     * Every argument in the list must belong to this command, otherwise the concatenation won't proceed
     *
     * @param concatenator the concatenator
     * @param arguments    the list of arguments
     */
    public void addConcatenator(Concatenator concatenator, Argument... arguments) {
        if (!concatenators.containsKey(concatenator)) {
            concatenators.put(concatenator, new LinkedList<>());
        }
        concatenators.get(concatenator).addAll(Arrays.asList(arguments));
    }

    /**
     * Adds permissions that will be applied on the command
     * Users will need these permissions in the specific channel to use the command
     *
     * @param permissionTypes a list of {@link PermissionType}
     */
    public void addPermissions(PermissionType... permissionTypes) {
        defaultPermissions.addAll(Arrays.asList(permissionTypes));
    }

    /**
     * Sets the command available only for administrators by default
     */
    public void setOnlyForAdministrators() {
        this.onlyForAdministrators = true;
    }

    /**
     * Sets the action listener
     *
     * @param listener the listener
     */
    public void setOnAction(CommandActionEventListener listener) {
        this.actionListener = listener;
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
    public List<Argument> getArguments() {
        return arguments;
    }

    /**
     * @return the map of concatenators
     */
    public Map<Concatenator, LinkedList<Argument>> getConcatenators() {
        return concatenators;
    }

    public Set<PermissionType> getDefaultPermissions() {
        return defaultPermissions;
    }

    public boolean isOnlyForAdministrators() {
        return onlyForAdministrators;
    }

    /**
     * Gets the action listener
     *
     * @return {@link Optional#empty()} when action listener is not specified,
     * otherwise {@link Optional#of(Object)} with the listener
     */
    public Optional<CommandActionEventListener> getActionListener() {
        return Optional.ofNullable(actionListener);
    }

}
