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
package me.s3ns3iw00.jcommands.argument.util;

import org.javacord.api.interaction.SlashCommandOptionChoice;

/**
 * A helper data class that stores data for creating a {@link SlashCommandOptionChoice}
 */
public class Choice {

    private final String key;
    private final Object value;

    /**
     * Default constructor
     *
     * @param key   the choice's key (the user will see this)
     * @param value the choice's value
     *              can be {@link String} or {@link Number}
     * NOTE: Discord accepts only {@link Long} type, therefore {@link Number#longValue()} is used
     *       That means, decimals are not supported!
     */
    public Choice(String key, Object value) {
        this.key = key;
        this.value = value;

        if (!(value instanceof String || value instanceof Number)) {
            throw new IllegalArgumentException("Value must be String or long");
        }
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    /**
     * Constructs the {@link SlashCommandOptionChoice} instance
     *
     * @return the {@link SlashCommandOptionChoice}
     */
    public SlashCommandOptionChoice getChoice() {
        if (value instanceof String) {
            return SlashCommandOptionChoice.create(key, (String) value);
        } else if (value instanceof Number) {
            return SlashCommandOptionChoice.create(key, ((Number) value).longValue());
        }
        return null;
    }
}
