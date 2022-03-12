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
package me.s3ns3iw00.jcommands.type;

import me.s3ns3iw00.jcommands.Command;
import me.s3ns3iw00.jcommands.event.listener.BadCategoryEventListener;
import me.s3ns3iw00.jcommands.event.listener.BadChannelEventListener;
import me.s3ns3iw00.jcommands.limitation.type.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * A {@code Command} that only can be used on servers where the command has been registered.<br>
 * Can be limited for users, roles, channels and categories.
 */
public class SlashCommand extends Command implements UserLimitable, RoleLimitable, ChannelLimitable, CategoryLimitable {

    private final Set<UserLimitation> userLimitations = new HashSet<>();
    private final Set<RoleLimitation> roleLimitations = new HashSet<>();
    private final Set<ChannelLimitation> channelLimitations = new HashSet<>();
    private final Set<CategoryLimitation> categoryLimitations = new HashSet<>();

    private BadCategoryEventListener badCategoryListener;
    private BadChannelEventListener badChannelListener;

    public SlashCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public Set<CategoryLimitation> getCategoryLimitations() {
        return categoryLimitations;
    }

    @Override
    public Set<ChannelLimitation> getChannelLimitations() {
        return channelLimitations;
    }

    @Override
    public Set<RoleLimitation> getRoleLimitations() {
        return roleLimitations;
    }

    @Override
    public Set<UserLimitation> getUserLimitations() {
        return userLimitations;
    }

    /**
     * Sets the bad category listener
     *
     * @param listener the listener
     */
    public void setOnBadCategory(BadCategoryEventListener listener) {
        this.badCategoryListener = listener;
    }

    /**
     * Sets the bad channel listener
     *
     * @param listener the listener
     */
    public void setOnBadChannel(BadChannelEventListener listener) {
        this.badChannelListener = listener;
    }

    /**
     * Gets the bad category listener
     *
     * @return {@link Optional#empty()} when bad category listener is not specified,
     * otherwise {@link Optional#of(Object)} with the listener
     */
    public Optional<BadCategoryEventListener> getBadCategoryListener() {
        return Optional.ofNullable(badCategoryListener);
    }

    /**
     * Gets the bad channel mismatch listener
     *
     * @return {@link Optional#empty()} when bad channel listener is not specified,
     * otherwise {@link Optional#of(Object)} with the listener
     */
    public Optional<BadChannelEventListener> getBadChannelListener() {
        return Optional.ofNullable(badChannelListener);
    }

}
