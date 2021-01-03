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

import me.s3ns3iw00.jcommands.limitation.CategoryLimitable;
import me.s3ns3iw00.jcommands.limitation.ChannelLimitable;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.TextChannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An extended version of {@code PrivateCommand} that can be used also on servers and can be limited for channels and categories.
 */
public class GlobalCommand extends PrivateCommand implements ChannelLimitable, CategoryLimitable {

    private boolean allowedChannels, allowedCategories;
    private List<TextChannel> channelList = new ArrayList<>();
    private List<ChannelCategory> categoryList = new ArrayList<>();

    public GlobalCommand(String name) {
        super(name);
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
     * @return true or false depends on if the channel limitation is allowing or disallowing
     */
    @Override
    public boolean isAllowedChannels() {
        return allowedChannels;
    }

    /**
     * @return true or false depends on if the category limitation is allowing or disallowing
     */
    @Override
    public boolean isAllowedCategories() {
        return allowedCategories;
    }
}
