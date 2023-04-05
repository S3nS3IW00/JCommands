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
package me.s3ns3iw00.jcommands.argument.concatenation.type;

import me.s3ns3iw00.jcommands.argument.ArgumentResult;
import me.s3ns3iw00.jcommands.argument.concatenation.Concatenator;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents a type of {@link Concatenator} that concatenates the arguments' result to a {@link String} separated with a delimiter
 *
 * @param <R> the type of the argument result
 */
public class StringConcatenator<R> extends Concatenator<String, R> {

    private final String delimiter;

    public StringConcatenator(String delimiter, Class<R> resultType) {
        super(resultType);
        this.delimiter = delimiter;
    }

    /**
     * Concatenates arguments into {@link String} separated with the specified delimiter
     *
     * @param results that need to be concatenated
     * @return the concatenated String
     */
    @Override
    public String concatenate(ArgumentResult... results) {
        return Arrays.stream(results).map(result -> result.get().toString()).collect(Collectors.joining(delimiter));
    }

}
