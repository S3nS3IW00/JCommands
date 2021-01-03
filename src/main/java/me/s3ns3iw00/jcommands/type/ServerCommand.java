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
package me.s3ns3iw00.jcommands.type;

import me.s3ns3iw00.jcommands.Command;
import me.s3ns3iw00.jcommands.limitation.CategoryLimitable;
import me.s3ns3iw00.jcommands.limitation.ChannelLimitable;
import me.s3ns3iw00.jcommands.limitation.RoleLimitable;
import me.s3ns3iw00.jcommands.limitation.UserLimitable;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A {@code Command} that only can be used on servers where the command has been registered.<br>
 * Can be limited for users, roles, channels and categories.
 */
public class ServerCommand extends Command implements UserLimitable, RoleLimitable, ChannelLimitable, CategoryLimitable {

    private boolean needAllRoles, allowedUsers, allowedChannels, allowedCategories;
    private List<User> userList = new ArrayList<>();
    private List<Role> roleList;
    private List<TextChannel> channelList = new ArrayList<>();
    private List<ChannelCategory> categoryList = new ArrayList<>();

    public ServerCommand(String name) {
        super(name);
    }

    /**
     * Sets the users who can use this command
     *
     * @param users the list of the users
     */
    @Override
    public void setUsers(boolean allowed, User... users) {
        allowedUsers = allowed;
        userList.addAll(Arrays.asList(users));
    }

    /**
     * Sets the roles which can use this command with<br>
     * Does not take any effect when the command sent in private
     *
     * @param needAllRoles if true all roles will needed to use this command
     * @param roles        the list of the roles
     */
    @Override
    public void setRoles(boolean needAllRoles, Role... roles) {
        this.needAllRoles = needAllRoles;
        roleList = Arrays.asList(roles);
    }

    /**
     * Sets the channels where the command will be allowed
     *
     * @param channels the list of the channels
     */
    @Override
    public void setChannels(boolean allowed, TextChannel... channels) {
        allowedChannels = allowed;
        channelList.addAll(Arrays.asList(channels));
    }

    /**
     * Sets the categories where the command will be allowed
     *
     * @param categories the list of the categories
     */
    @Override
    public void setCategories(boolean allowed, ChannelCategory... categories) {
        allowedCategories = allowed;
        categoryList.addAll(Arrays.asList(categories));
    }

    /**
     * @return the list of the users
     */
    @Override
    public List<User> getUsers() {
        return userList;
    }

    /**
     * @return the list of the roles
     */
    @Override
    public List<Role> getRoles() {
        return roleList;
    }

    /**
     * @return true or false depends on if the user needs all the roles to use this command
     */
    @Override
    public boolean isNeedAllRoles() {
        return needAllRoles;
    }

    /**
     * @return the list of the channels
     */
    @Override
    public List<TextChannel> getChannels() {
        return channelList;
    }

    /**
     * @return the list of the categories
     */
    @Override
    public List<ChannelCategory> getCategories() {
        return categoryList;
    }

    /**
     * @return true or false depends on if the category limitation is allowing or disallowing
     */
    @Override
    public boolean isAllowedCategories() {
        return allowedCategories;
    }

    /**
     * @return true or false depends on if the channel limitation is allowing or disallowing
     */
    @Override
    public boolean isAllowedChannels() {
        return allowedChannels;
    }

    /**
     * @return true or false depends on if the user limitation is allowing or disallowing
     */
    @Override
    public boolean isAllowedUsers() {
        return allowedUsers;
    }
}
