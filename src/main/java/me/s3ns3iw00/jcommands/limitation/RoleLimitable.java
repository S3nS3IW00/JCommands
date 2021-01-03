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
package me.s3ns3iw00.jcommands.limitation;

import org.javacord.api.entity.permission.Role;

import java.util.List;

/**
 * Commands that implements this interface will be able to limited for roles
 */
public interface RoleLimitable {

    /**
     * Sets the roles which can use this command with
     *
     * @param needAllRoles if true all roles will needed to use this command
     * @param roles        the list of the roles
     */
    void setRoles(boolean needAllRoles, Role... roles);

    /**
     * @return the list of the roles
     */
    List<Role> getRoles();

    /**
     * @return true or false depends on if the user needs all the roles to use this command
     */
    boolean isNeedAllRoles();

}
