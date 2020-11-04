/*
 * Copyright (C) 2020 S3nS3IW00
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
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author S3nS3IW00
 */
public class Command {

    /**
     * These guys are nice
     */
    private String name;
    private CommandType type;
    private LinkedList<List<Argument>> arguments = new LinkedList<>();
    private List<ChannelCategory> allowedCategories = new ArrayList<>(), notAllowedCategories = new ArrayList<>();
    private List<TextChannel> allowed = new ArrayList<>(), notAllowed = new ArrayList<>();
    private Role[] roles;
    private boolean needAllRole;
    private CommandAction action;

    public Command(String name, CommandType type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Adds a list of argument which at least one of are acceptable at the current index
     *
     * @param arguments the list of the arguments
     * @return this class
     */
    public Command arguments(Argument... arguments) {
        this.arguments.add(new ArrayList<>(Arrays.asList(arguments)));
        return this;
    }

    /**
     * Sets the categories where the command will be allowed<br>
     * If the not allowed categories has been set this will not take any effect.
     *
     * @param category the list of the categories
     * @return this class
     */
    public Command allowedCategory(ChannelCategory category) {
        allowedCategories.add(category);
        return this;
    }

    /**
     * Sets the categories where the command will not be allowed<br>
     * If the allowed categories has been set this will not take any effect
     *
     * @param category the list of the categories
     * @return this class
     */
    public Command notAllowedCategory(ChannelCategory category) {
        notAllowedCategories.add(category);
        return this;
    }

    /**
     * Sets the channels where the command will be allowed<br>
     * If the not allowed commands has been set this will not take any effect.
     *
     * @param channels the list of the channels
     * @return this class
     */
    public Command allowed(TextChannel... channels) {
        allowed.addAll(Arrays.asList(channels));
        return this;
    }

    /**
     * Sets the channels where the command will not be allowed<br>
     * If the allowed commands has been set this will not take any effect
     *
     * @param channels the list of the channels
     * @return this class
     */
    public Command notAllowed(TextChannel... channels) {
        notAllowed.addAll(Arrays.asList(channels));
        return this;
    }

    /**
     * Sets the roles which can use this command with
     *
     * @param needAllRole if true all roles will needed to use this command
     * @param roles the list of the roles
     * @return this class
     */
    public Command roles(boolean needAllRole, Role... roles) {
        this.roles = roles;
        this.needAllRole = needAllRole;
        return this;
    }

    /**
     * Sets the action listener of the command
     *
     * @param action is the listener object
     * @return this class
     */
    public Command action(CommandAction action) {
        this.action = action;
        return this;
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

    public String getName() {
        return name;
    }

    public CommandType getType() {
        return type;
    }

    List<List<Argument>> getArguments() {
        return arguments;
    }

    List<ChannelCategory> getAllowedCategories() {
        return allowedCategories;
    }

    List<ChannelCategory> getNotAllowedCategories() {
        return notAllowedCategories;
    }

    List<TextChannel> getAllowed() {
        return allowed;
    }

    List<TextChannel> getNotAllowed() {
        return notAllowed;
    }

    Role[] getRoles() {
        return roles;
    }

    boolean isNeedAllRole() {
        return needAllRole;
    }

    CommandAction getAction() {
        return action;
    }

}
