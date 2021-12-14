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

import org.javacord.api.entity.server.Server;

/**
 * Represents a command limitation
 *
 * @param <T> aspect that the command will be limited by
 */
public abstract class Limitation<T> {

    /**
     * The server to which the limitation belongs
     */
    private final Server server;

    /**
     * It's obvious... I hope...
     */
    private final boolean permit;

    public Limitation(Server server, boolean permit) {
        this.server = server;
        this.permit = permit;
    }

    /**
     * Gets the entity of the current aspect
     *
     * @return the entity
     */
    public abstract T getEntity();

    public Server getServer() {
        return server;
    }

    public boolean isPermit() {
        return permit;
    }

}
