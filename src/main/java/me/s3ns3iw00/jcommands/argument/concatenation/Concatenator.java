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
package me.s3ns3iw00.jcommands.argument.concatenation;

import me.s3ns3iw00.jcommands.argument.ArgumentResult;

/**
 * Concatenates the arguments' result to a type of value that based on the concatenation process
 * The process is implemented by subclasses overriding {@link Concatenator#concatenate(ArgumentResult...)} method
 */
public abstract class Concatenator {

    private final Class<?> resultType;

    /**
     * Constructs the class with the default requirements
     *
     * @param resultType the type of the result of the concatenation
     */
    public Concatenator(Class<?> resultType) {
        this.resultType = resultType;
    }

    /**
     * Concatenates the arguments using subclass implementation
     *
     * @return the result of the concatenation
     */
    public abstract Object concatenate(ArgumentResult... results);

    public Class<?> getResultType() {
        return resultType;
    }
}
