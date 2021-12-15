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
import me.s3ns3iw00.jcommands.limitation.type.RoleLimitable;
import me.s3ns3iw00.jcommands.limitation.type.RoleLimitation;
import me.s3ns3iw00.jcommands.limitation.type.UserLimitable;
import me.s3ns3iw00.jcommands.limitation.type.UserLimitation;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A {@code Command} that can be only used in private message and can be limited for users and roles.
 * <p>
 * Deprecated since the new command system doesn't support private commands.
 */
@Deprecated
public class PrivateCommand extends Command implements UserLimitable, RoleLimitable {

    private final Set<UserLimitation> userLimitations = new HashSet<>();
    private final Set<RoleLimitation> roleLimitations = new HashSet<>();

    public PrivateCommand(String name, String description) {
        super(name, description);
    }

    /**
     * Sets the users who can use this command
     *
     * @param users the list of the users
     * @deprecated because of the new limit system
     *             use {@link PrivateCommand#addUserLimitation(UserLimitation)} instead
     */
    @Deprecated
    public void setUsers(boolean allowed, User... users) {
    }

    /**
     * Sets the roles which can use this command with<br>
     * Does not take any effect if the {@code roleSourceServer} is not set
     *
     * @param roles        the list of the roles
     * @deprecated because of the new limit system
     *             use {@link PrivateCommand#addRoleLimitation(RoleLimitation)} instead
     */
    @Deprecated
    public void setRoles(boolean allowedRoles, Role... roles) {
    }

    /**
     * Sets the server where the roles will be fetched from<br>
     * If this is not specified the role limitation will be ignored
     *
     * @param roleSource the server
     * @deprecated because of the new limit system
     */
    @Deprecated
    public void setRoleSource(Server roleSource) {
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
     * @return true or false depends on if the user limitation is allowing or disallowing
     * @deprecated because of the new limit system
     */
    @Deprecated
    public boolean isAllowedUsers() {
        return false;
    }

    /**
     * @return an {@code Optional} that is empty when no server was specified otherwise contains a {@code Server}
     * @deprecated because of the new limit system
     */
    @Deprecated
    public Optional<Server> getRoleSource() {
        return Optional.empty();
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
