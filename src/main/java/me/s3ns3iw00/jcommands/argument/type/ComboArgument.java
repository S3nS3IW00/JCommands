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
import me.s3ns3iw00.jcommands.argument.util.Choice;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Represents an argument that has multiple choices, and they are the only valid values for the user to pick
 * The values are key value pairs
 * The key is {@code String} and the value can be {@code String} or {@code Long}
 */
public class ComboArgument extends InputArgument {

    private final LinkedList<Choice> choices = new LinkedList<>();

    /**
     * Constructs the argument with the default requirements
     *
     * @param name        the argument's name
     * @param description the argument's description
     * @param type        the type of the input value, can be {@code STRING} or {@code LONG}
     */
    public ComboArgument(String name, String description, SlashCommandOptionType type) {
        super(name, description, type);

        if (type != SlashCommandOptionType.STRING && type != SlashCommandOptionType.LONG) {
            throw new IllegalArgumentException("type can be only String or Integer");
        }
    }

    /**
     * Adds a choice
     *
     * @param key   is the name of the argument
     * @param value is the value of the argument as {@code String}
     */
    public void addChoice(String key, String value) {
        if (getType() == SlashCommandOptionType.LONG) {
            throw new IllegalStateException("Value must match with the argument's type: Long");
        }

        choices.add(new Choice(key, value));
    }

    /**
     * Adds a choice
     *
     * @param key   the name of the argument
     * @param value the value of the argument as {@code Long}
     */
    public void addChoice(String key, long value) {
        if (getType() == SlashCommandOptionType.STRING) {
            throw new IllegalStateException("Value must match with the argument's type: String");
        }

        choices.add(new Choice(key, value));
    }

    @Override
    public Object getValue() {
        if (getType() == SlashCommandOptionType.STRING) {
            return super.getValue().toString();
        }
        return super.getValue();
    }

    @Override
    public Class<?> getResultType() {
        return getType() == SlashCommandOptionType.STRING ? String.class : Long.class;
    }

    @Override
    public SlashCommandOption getCommandOption() {
        return SlashCommandOption.createWithChoices(getType(),
                getName(), getDescription(), !isOptional(), choices.stream().map(Choice::getChoice).collect(Collectors.toList()));
    }
}
