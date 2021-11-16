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
import me.s3ns3iw00.jcommands.limitation.RoleLimitable;
import me.s3ns3iw00.jcommands.limitation.UserLimitable;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * A {@code Command} that can be only used in private message and can be limited for users and roles.
 *
 * Deprecated since the new command system doesn't support private commands.
 */
@Deprecated
public class PrivateCommand extends Command implements UserLimitable, RoleLimitable {

    private boolean needAllRoles, allowedUsers;
    private List<User> userList = new ArrayList<>();
    private Optional<Server> roleSourceServer = Optional.empty();
    private List<Role> roleCollection;

    public PrivateCommand(String name) {
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
        userList = Arrays.asList(users);
    }

    /**
     * Sets the roles which can use this command with<br>
     * Does not take any effect if the {@code roleSourceServer} is not set
     *
     * @param needAllRoles if true all roles will needed to use this command
     * @param roles        the list of the roles
     */
    @Override
    public void setRoles(boolean needAllRoles, Role... roles) {
        this.needAllRoles = needAllRoles;
        roleCollection = Arrays.asList(roles);
    }

    /**
     * Sets the server where the roles will be fetched from<br>
     * If this is not specified the role limitation will be ignored
     *
     * @param roleSource the server
     */
    public void setRoleSource(Server roleSource) {
        roleSourceServer = Optional.of(roleSource);
    }

    /**
     * @return the list of the users
     */
    @Override
    public List<User> getUsers() {
        return userList;
    }

    /**
     * @return true or false depends on if the user limitation is allowing or disallowing
     */
    @Override
    public boolean isAllowedUsers() {
        return allowedUsers;
    }

    /**
     * @return an {@code Optional} that is empty when no server was specified otherwise contains a {@code Server}
     */
    public Optional<Server> getRoleSource() {
        return roleSourceServer;
    }

    /**
     * @return the list of the roles
     */
    @Override
    public List<Role> getRoles() {
        return roleCollection;
    }

    /**
     * @return true or false depends on if the user needs all the roles to use this command
     */
    @Override
    public boolean isNeedAllRoles() {
        return needAllRoles;
    }
}
