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
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

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
    private String name;
    private CommandType type;
    private LinkedList<List<Argument>> arguments = new LinkedList<>();
    private List<User> allowedUserList = new ArrayList<>(), notAllowedUserList = new ArrayList<>();
    private List<ChannelCategory> allowedCategoryList = new ArrayList<>(), notAllowedCategoryList = new ArrayList<>();
    private List<TextChannel> allowedChannelList = new ArrayList<>(), notAllowedChannelList = new ArrayList<>();
    private Role[] roles;
    private boolean needAllRole;
    private Optional<Server> roleSourceServer = Optional.empty();
    private Optional<CommandAction> action = Optional.empty();

    public Command(String name, CommandType type) {
        this.name = name;
        this.type = type;
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
     * Sets the users who can use this command<br>
     * If the not allowed users has been set this will not take any effect.
     *
     * @param users the list of the users
     */
    public void setAllowedUsers(User... users) {
        allowedUserList = new ArrayList<>(Arrays.asList(users));
    }

    /**
     * Sets the users who cannot use this command<br>
     * If the allowed users has been set this will not take any effect
     *
     * @param users the list of the users
     */
    public void setNotAllowedUsers(User... users) {
        notAllowedUserList = new ArrayList<>(Arrays.asList(users));
    }

    /**
     * Sets the categories where the command will be allowed<br>
     * If the not allowed categories has been set this will not take any effect.
     *
     * @param categories the list of the categories
     */
    public void setAllowedCategories(ChannelCategory... categories) {
        allowedCategoryList = new ArrayList<>(Arrays.asList(categories));
    }

    /**
     * Sets the categories where the command will not be allowed<br>
     * If the allowed categories has been set this will not take any effect
     *
     * @param categories the list of the categories
     */
    public void setNotAllowedCategories(ChannelCategory... categories) {
        notAllowedCategoryList = new ArrayList<>(Arrays.asList(categories));
    }

    /**
     * Sets the channels where the command will be allowed<br>
     * If the not allowed commands has been set this will not take any effect.
     *
     * @param channels the list of the channels
     */
    public void setAllowedChannels(TextChannel... channels) {
        allowedChannelList = new ArrayList<>(Arrays.asList(channels));
    }

    /**
     * Sets the channels where the command will not be allowed<br>
     * If the allowed commands has been set this will not take any effect
     *
     * @param channels the list of the channels
     */
    public void setNotAllowedChannels(TextChannel... channels) {
        notAllowedChannelList = new ArrayList<>(Arrays.asList(channels));
    }

    /**
     * Sets the roles which can use this command with<br>
     * Does not take any effect when the command sent in private
     *
     * @param needAllRole if true all roles will needed to use this command
     * @param roles       the list of the roles
     */
    public void setRoles(boolean needAllRole, Role... roles) {
        this.roles = roles;
        this.needAllRole = needAllRole;
    }

    /**
     * Sets the server where the roles will be fetched from<br>
     * This is useful when we want to check the roles at a private command
     * <p>
     * Safe to use when the command is registered in only one server
     *
     * @param server the server
     */
    public void setRoleSource(Server server) {
        roleSourceServer = Optional.of(server);
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

    public String getName() {
        return name;
    }

    public CommandType getType() {
        return type;
    }

    List<List<Argument>> getArguments() {
        return arguments;
    }

    List<User> getAllowedUserList() {
        return allowedUserList;
    }

    List<User> getNotAllowedUserList() {
        return notAllowedUserList;
    }

    List<ChannelCategory> getAllowedCategoryList() {
        return allowedCategoryList;
    }

    List<ChannelCategory> getNotAllowedCategoryList() {
        return notAllowedCategoryList;
    }

    List<TextChannel> getAllowedChannelList() {
        return allowedChannelList;
    }

    List<TextChannel> getNotAllowedChannelList() {
        return notAllowedChannelList;
    }

    Role[] getRoles() {
        return roles;
    }

    boolean isNeedAllRole() {
        return needAllRole;
    }

    Optional<Server> getRoleSource() {
        return roleSourceServer;
    }

    Optional<CommandAction> getAction() {
        return action;
    }

}
