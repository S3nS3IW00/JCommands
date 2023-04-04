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
package me.s3ns3iw00.jcommands.argument.validator.type;

import me.s3ns3iw00.jcommands.argument.validator.ArgumentPredicate;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * A regex validation predicate that is true when the input does not match the specified regex
 */
public class RegexPredicate implements ArgumentPredicate<String> {

    private final Pattern pattern;

    public RegexPredicate(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public Predicate<String> getPredicate() {
        return (value) -> !pattern.matcher(value).lookingAt();
    }

    /**
     * Constructs an instance of the class statically
     *
     * @param regex the regex
     * @return the instance
     */
    public static RegexPredicate notValidFor(String regex) {
        return new RegexPredicate(regex);
    }

}
