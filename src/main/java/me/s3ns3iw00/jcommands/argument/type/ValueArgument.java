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
package me.s3ns3iw00.jcommands.argument.type;

import me.s3ns3iw00.jcommands.argument.NestedArgument;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an argument that accepts a specified type of value
 *
 * The value can be validated with regex by specifying a validator with {@link ValueArgument#validate(String)}}
 */
public class ValueArgument implements NestedArgument {

    private final String name, description;

    private Object input;
    private Optional<Pattern> validator = Optional.empty();
    private Matcher matcher;

    private final SlashCommandOptionType type;
    private Class<?> resultType = Matcher.class;

    private boolean optional = false;

    /**
     * Constructs the argument with the default requirements
     *
     * @param name the argument's name
     * @param description the argument's description
     * @param type the type of the input value
     */
    public ValueArgument(String name, String description, SlashCommandOptionType type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    /**
     * Runs the default constructor and specifies the result type of the value, that the input will be converted to
     *
     * @param name the argument's name
     * @param description the argument's description
     * @param type the type of the value
     * @param resultType the type of the converted value
     */
    public ValueArgument(String name, String description, SlashCommandOptionType type, Class<?> resultType) {
        this(name, description, type);
        this.resultType = resultType;
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
     * @param input the user input
     * @return true if validator is not set
     *         if validator is set then true or false depends on the validation process result
     */
    public boolean isValid(Object input) {
        this.input = input;
        return validator.map(pattern -> (matcher = pattern.matcher(input.toString())).lookingAt()).orElse(true);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Object getValue() {
        if (validator.isPresent() && resultType == Matcher.class) {
            return matcher;
        }
        return input;
    }

    @Override
    public Class<?> getResultType() {
        return resultType;
    }

    @Override
    public SlashCommandOption getCommandOption() {
        return SlashCommandOption.create(type, getName(), getDescription(), !optional);
    }

    /**
     * Sets the argument optional
     *
     * NOTE: only the last argument of the options can be set as optional, otherwise the command won't work
     */
    public void setOptional() {
        this.optional = true;
    }
}
