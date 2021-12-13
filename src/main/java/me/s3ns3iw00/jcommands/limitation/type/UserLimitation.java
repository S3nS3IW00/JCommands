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
package me.s3ns3iw00.jcommands.limitation.type;

import me.s3ns3iw00.jcommands.limitation.Limitation;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

/**
 * Represents a specific type of limitation
 * Commands with this limitation do not work in private
 */
public class UserLimitation extends Limitation<User> {

    private final User entity;

    public UserLimitation(Server server, boolean permit, User entity) {
        super(server, permit);
        this.entity = entity;
    }

    @Override
    public User getEntity() {
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
    public static UserLimitation with(Server server, boolean permit, User entity) {
        return new UserLimitation(server, permit, entity);
    }

}
