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

import java.util.HashSet;
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
