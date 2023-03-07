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
package me.s3ns3iw00.jcommands.limitation.type;

import me.s3ns3iw00.jcommands.limitation.Limitation;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.server.Server;

/**
 * Represents a specific type of limitation
 *
 * @deprecated because of the new permission system
 */
@Deprecated
public class CategoryLimitation extends Limitation<ChannelCategory> {

    private final ChannelCategory entity;

    public CategoryLimitation(Server server, boolean permit, ChannelCategory entity) {
        super(server, permit);
        this.entity = entity;
    }

    @Override
    public ChannelCategory getEntity() {
        return entity;
    }

    /**
     * A static method for create an instance of this class
     *
     * @param server the server
     * @param permit permit or deny
     * @param entity the entity
     * @return the instance
     */
    public static CategoryLimitation with(Server server, boolean permit, ChannelCategory entity) {
        return new CategoryLimitation(server, permit, entity);
    }

}
