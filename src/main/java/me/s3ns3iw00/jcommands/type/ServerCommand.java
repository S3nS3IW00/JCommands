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
package me.s3ns3iw00.jcommands.type;

import me.s3ns3iw00.jcommands.Command;
import me.s3ns3iw00.jcommands.limitation.type.*;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A {@code Command} that only can be used on servers where the command has been registered.<br>
 * Can be limited for users, roles, channels and categories.
 */
public class ServerCommand extends Command implements UserLimitable, RoleLimitable, ChannelLimitable, CategoryLimitable {

    private final Set<UserLimitation> userLimitations = new HashSet<>();
    private final Set<RoleLimitation> roleLimitations = new HashSet<>();
    private final Set<ChannelLimitation> channelLimitations = new HashSet<>();
    private final Set<CategoryLimitation> categoryLimitations = new HashSet<>();

    public ServerCommand(String name, String description) {
        super(name, description);
    }

    /**
     * Sets the users who can use this command
     *
     * @param users the list of the users
     * @deprecated because of the new limit system
     *             use {@link ServerCommand#addUserLimitation(UserLimitation)} instead
     */
    @Deprecated
    public void setUsers(boolean allowed, User... users) {
    }

    /**
     * Sets the roles which can use this command with
     *
     * @param roles        the list of the roles
     * @deprecated because of the new limit system
     *             use {@link ServerCommand#addRoleLimitation(RoleLimitation)} instead
     */
    @Deprecated
    public void setRoles(boolean allowedRoles, Role... roles) {
    }

    /**
     * Sets the channels where the command will be allowed
     *
     * @param channels the list of the channels
     * @deprecated because of the new limit system
     *             use {@link ServerCommand#addChannelLimitation(ChannelLimitation)} instead
     */
    @Deprecated
    public void setChannels(boolean allowed, TextChannel... channels) {
    }

    /**
     * Sets the categories where the command will be allowed
     *
     * @deprecated because of the new limit system
     *             use {@link ServerCommand#addCategoryLimitation(CategoryLimitation)} instead
     */
    @Deprecated
    public void setCategories(boolean allowed, ChannelCategory... categories) {
    }

    /**
     * @return the list of the users
     * @deprecated because of the new limit system
     */
    @Deprecated
    public List<User> getUsers() {
        return null;
    }

    /**
     * @return the list of the roles
     * @deprecated because of the new limit system
     */
    @Deprecated
    public List<Role> getRoles() {
        return null;
    }

    /**
     * @return true or false depends on if the user needs all the roles to use this command
     * @deprecated because of the new limit system
     */
    @Deprecated
    public boolean isAllowedRoles() {
        return false;
    }

    /**
     * @return the list of the channels
     * @deprecated because of the new limit system
     */
    @Deprecated
    public List<TextChannel> getChannels() {
        return null;
    }

    /**
     * @return the list of the categories
     * @deprecated because of the new limit system
     */
    @Deprecated
    public List<ChannelCategory> getCategories() {
        return null;
    }

    /**
     * @return true or false depends on if the category limitation is allowing or disallowing
     * @deprecated because of the new limit system
     */
    @Deprecated
    public boolean isAllowedCategories() {
        return false;
    }

    /**
     * @return true or false depends on if the channel limitation is allowing or disallowing
     * @deprecated because of the new limit system
     */
    @Deprecated
    public boolean isAllowedChannels() {
        return false;
    }

    /**
     * @return true or false depends on if the user limitation is allowing or disallowing
     * @deprecated because of the new limit system
     */
    @Deprecated
    public boolean isAllowedUsers() {
        return false;
    }

    @Override
    public void addCategoryLimitation(CategoryLimitation limitation) {
        categoryLimitations.add(limitation);
    }

    @Override
    public Set<CategoryLimitation> getCategoryLimitations() {
        return categoryLimitations;
    }

    @Override
    public void addChannelLimitation(ChannelLimitation limitation) {
        channelLimitations.add(limitation);
    }

    @Override
    public Set<ChannelLimitation> getChannelLimitations() {
        return channelLimitations;
    }

    @Override
    public void addRoleLimitation(RoleLimitation limitation) {
        roleLimitations.add(limitation);
    }

    @Override
    public Set<RoleLimitation> getRoleLimitations() {
        return roleLimitations;
    }

    @Override
    public void addUserLimitation(UserLimitation limitation) {
        userLimitations.add(limitation);
    }

    @Override
    public Set<UserLimitation> getUserLimitations() {
        return userLimitations;
    }
}
