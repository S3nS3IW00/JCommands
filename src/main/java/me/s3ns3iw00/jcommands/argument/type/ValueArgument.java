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
package me.s3ns3iw00.jcommands.argument.type;

import me.s3ns3iw00.jcommands.argument.InputArgument;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an argument that accepts a specified type of value
 * <p>
 * The value can be validated with regex by specifying a validator with {@link ValueArgument#validate(String)}}
 */
public class ValueArgument extends InputArgument {

    private Optional<Pattern> validator = Optional.empty();
    private Matcher matcher;

    /**
     * Constructs the argument with the default requirements
     *
     * @param name        the argument's name
     * @param description the argument's description
     * @param type        the type of the input value
     */
    public ValueArgument(String name, String description, SlashCommandOptionType type) {
        super(name, description, type, Matcher.class);
    }

    /**
     * Runs the default constructor and specifies the result type of the value, that the input will be converted to
     *
     * @param name        the argument's name
     * @param description the argument's description
     * @param type        the type of the value
     * @param resultType  the type of the converted value
     */
    public ValueArgument(String name, String description, SlashCommandOptionType type, Class<?> resultType) {
        super(name, description, type, resultType);
    }

    /**
     * Specifies a validator to validate the input
     *
     * @param regex the validation pattern
     */
    public void validate(String regex) {
        validator = Optional.of(Pattern.compile(regex));
    }

    /**
     * If validator is set checks the user input if it's valid for the argument or not
     *
     * @param value the value
     * @return true if validator is not set
     * if validator is set then true or false depends on the validation process result
     */
    public boolean isValid(Object value) {
        return validator.map(pattern -> (matcher = pattern.matcher(String.valueOf(value))).lookingAt()).orElse(true);
    }

    /**
     * Checks that the input is valid and adjusts it
     *
     * @param input the input
     */
    @Override
    public void input(Object input) {
        if (isValid(input)) {
            super.input(input);
        } else {
            super.input(null);
        }
    }

    /**
     * Returns the value that depends on a few aspects
     *
     * @return null if the validation was failed
     * the matcher if the result type is {@link Matcher}
     * otherwise the value itself
     */
    @Override
    public Object getValue() {
        if (super.getValue() == null) {
            return null;
        } else if (validator.isPresent() && getResultType() == Matcher.class) {
            return matcher;
        }
        return super.getValue();
    }
}
