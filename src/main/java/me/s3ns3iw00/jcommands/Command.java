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
import me.s3ns3iw00.jcommands.argument.SubArgument;
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
    private final Map<Concatenator, LinkedList<InputArgument>> concatenators = new LinkedHashMap<>();

    private final Set<PermissionType> defaultPermissions = new HashSet<>();

    private boolean onlyForAdministrators = false;
    private boolean nsfw = false;

    private CommandActionEventListener actionListener;

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

        if (!name.matches("^[-_\\p{L}\\p{N}\\p{sc=Deva}\\p{sc=Thai}]{1,32}$")) {
            throw new IllegalArgumentException("Command's name is invalid, it should contain only word characters, '-' and '_' character, and its length must between 1 and 32");
        }

        if (description.isEmpty() || description.length() > 100) {
            throw new IllegalArgumentException("Description's length must between 1 and 100");
        }
    }

    /**
     * Adds an argument to the command
     *
     * @param argument the argument
     */
    public void addArgument(Argument argument) {
        if (!arguments.isEmpty()) {
            // Unique name checking
            if (arguments.stream().anyMatch(arg -> arg.getName().equals(argument.getName()))) {
                throw new IllegalStateException("Arguments must have unique name in the same scope!");
            }

            // SubArgument and InputArgument mix checking
            if ((argument instanceof SubArgument && arguments.stream().anyMatch(arg -> arg instanceof InputArgument)) ||
                    (argument instanceof InputArgument && arguments.stream().anyMatch(arg -> arg instanceof SubArgument))) {
                throw new IllegalStateException("SubArguments and InputArguments cannot be mixed in the same scope!");
            }

            // Optional and non-optional argument order checking
            if (argument instanceof InputArgument &&
                    !((InputArgument) argument).isOptional() &&
                    arguments.getLast() instanceof InputArgument &&
                    ((InputArgument) arguments.getLast()).isOptional()) {
                throw new IllegalStateException("Cannot add non-optional argument after an optional argument!");
            }
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
    public void addConcatenator(Concatenator concatenator, InputArgument... arguments) {
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
     * Sets the command age-restricted
     * NOTE: this cannot be updated for now, so it only takes effect at the creation of the command
     */
    public void setNsfw() {
        this.nsfw = true;
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
    public Map<Concatenator, LinkedList<InputArgument>> getConcatenators() {
        return concatenators;
    }

    public Set<PermissionType> getDefaultPermissions() {
        return defaultPermissions;
    }

    public boolean isOnlyForAdministrators() {
        return onlyForAdministrators;
    }

    public boolean isNsfw() {
        return nsfw;
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
