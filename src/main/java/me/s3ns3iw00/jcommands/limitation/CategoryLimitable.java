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
package me.s3ns3iw00.jcommands.limitation;

import org.javacord.api.entity.channel.ChannelCategory;

import java.util.List;

/**
 * Commands that implements this interface will be able to limited for categories
 */
public interface CategoryLimitable {

    /**
     * Sets the categories where the command will be allowed
     *
     * @param categories the list of the categories
     */
    void setCategories(boolean allowed, ChannelCategory... categories);

    /**
     * @return the list of the categories
     */
    List<ChannelCategory> getCategories();

    /**
     * @return true or false depends on if the category limitation is allowing or disallowing
     */
    boolean isAllowedCategories();

}
