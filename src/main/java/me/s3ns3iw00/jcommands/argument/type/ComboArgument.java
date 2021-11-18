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

import me.s3ns3iw00.jcommands.argument.Argument;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionChoice;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.LinkedList;

/**
 * Represents an argument that has multiple choices, and they are the only valid values for the user to pick
 *
 * The values are key value pairs
 * The key is {@code String} and the value can be {@code String} or {@code Integer}
 */
public class ComboArgument implements Argument {

    private final String name, description;
    private Object value;

    private final SlashCommandOptionType type;
    private final LinkedList<SlashCommandOptionChoice> choices = new LinkedList<>();

    private boolean optional = false;

    /**
     * Constructs the argument with the default requirements
     *
     * @param name the argument's name
     * @param description the argument's description
     * @param type the type of the input value, can be {@code STRING} or {@code INTEGER}
     */
    public ComboArgument(String name, String description, SlashCommandOptionType type) {
        this.name = name;
        this.description = description;
        this.type = type;

        if (type == SlashCommandOptionType.STRING || type == SlashCommandOptionType.INTEGER) {
            throw new IllegalArgumentException("type can be only String or Integer");
        }
    }

    /**
     * Adds a choice
     *
     * @param key is the name of the argument
     * @param value is the value of the argument as {@code String}
     */
    public void addChoice(String key, String value) {
        choices.add(SlashCommandOptionChoice.create(key, value));
    }

    /**
     * Adds a choice
     *
     * @param key the name of the argument
     * @param value the value of the argument as {@code Integer}
     */
    public void addChoice(String key, int value) {
        choices.add(SlashCommandOptionChoice.create(key, value));
    }

    /**
     * Sets the chosen value
     *
     * @param value the chosen value
     */
    public void choose(Object value) {
        this.value = value;
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
    public String getValue() {
        return value.toString();
    }

    /**
     * Gets the value as Integer
     *
     * NOTE: Don't use if the type of the argument is {@code String}
     *
     * @return the value as integer
     */
    public int getIntValue() {
        return (int) value;
    }

    @Override
    public Class<?> getResultType() {
        return null;
    }

    @Override
    public SlashCommandOption getCommandOption() {
        return SlashCommandOption.createWithChoices(type, name, description, !optional, choices);
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
