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

import me.s3ns3iw00.jcommands.limitation.type.CategoryLimitable;
import me.s3ns3iw00.jcommands.limitation.type.CategoryLimitation;
import me.s3ns3iw00.jcommands.limitation.type.ChannelLimitable;
import me.s3ns3iw00.jcommands.limitation.type.ChannelLimitation;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.TextChannel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An extended version of {@code PrivateCommand} that can be used also on servers and can be limited for channels and categories.
 *
 * @deprecated use {@link ServerCommand} instead
 */
@Deprecated
public class GlobalCommand extends PrivateCommand implements ChannelLimitable, CategoryLimitable {

    private final Set<ChannelLimitation> channelLimitations = new HashSet<>();
    private final Set<CategoryLimitation> categoryLimitations = new HashSet<>();

    public GlobalCommand(String name, String description) {
        super(name, description);
    }

    /**
     * Sets the channels where the command will be allowed
     *
     * @param channels the list of the channels
     * @deprecated because of the new limit system
     *             use {@link GlobalCommand#addChannelLimitation(ChannelLimitation)} instead
     */
    @Deprecated
    public void setChannels(boolean allowed, TextChannel... channels) {
    }

    /**
     * Sets the categories where the command will be allowed
     *
     * @deprecated because of the new limit system
     *             use {@link GlobalCommand#addCategoryLimitation(CategoryLimitation)} instead
     */
    @Deprecated
    public void setCategories(boolean allowed, ChannelCategory... categories) {
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
}
