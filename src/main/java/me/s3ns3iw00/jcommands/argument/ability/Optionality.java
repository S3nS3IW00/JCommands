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
package me.s3ns3iw00.jcommands.argument.ability;

import java.util.Optional;

/**
 * Identifies whether an argument can be optional or not
 */
@Deprecated
public interface Optionality {

    boolean isOptional();

    /**
     * Sets the argument optional
     * Only the last argument of the options can be set as optional
     */
    void setOptional();

    /**
     * Gets the value of the argument as optional
     *
     * @return the optional value
     */
    Optional<?> getOptionalValue();

}
