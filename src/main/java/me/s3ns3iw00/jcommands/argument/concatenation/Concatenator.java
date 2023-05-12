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
package me.s3ns3iw00.jcommands.argument.concatenation;

import me.s3ns3iw00.jcommands.argument.ArgumentResult;
import me.s3ns3iw00.jcommands.argument.converter.ArgumentResultConverter;

import java.util.Optional;

/**
 * Concatenates the arguments' result to a type of value that based on the concatenation process
 * The process is implemented by subclasses overriding {@link Concatenator#concatenate(Object...)} method
 *
 * @param <C> the type of the concatenated result
 * @param <R> the type of the argument result
 */
public abstract class Concatenator<C, R> {

    private final Class<R> resultType;
    private ArgumentResultConverter<C, R> resultConverter;

    /**
     * Constructs the class with the default requirements
     *
     * @param resultType the type of the result of the concatenation
     */
    public Concatenator(Class<R> resultType) {
        this.resultType = resultType;
    }

    /**
     * Concatenates the arguments using subclass implementation
     *
     * @return the result of the concatenation
     */
    public abstract C concatenate(Object... results);

    public Class<R> getResultType() {
        return resultType;
    }

    public void convertResult(ArgumentResultConverter<C, R> resultConverter) {
        this.resultConverter = resultConverter;
    }

    public Optional<ArgumentResultConverter<C, R>> getResultConverter() {
        return Optional.ofNullable(resultConverter);
    }
}
