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
package me.s3ns3iw00.jcommands.argument;

import me.s3ns3iw00.jcommands.event.listener.ArgumentMismatchEventListener;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Optional;

/**
 * Represents an argument
 *
 * @author S3nS3IW00
 */
public abstract class Argument {

    private final String name, description;
    private final SlashCommandOptionType type;

    private ArgumentMismatchEventListener mismatchListener;

    /**
     * Default constructor
     *
     * @param name        the name of the command
     *                    Its length must between 1 and 32
     *                    Can contain only:
     *                    - word characters
     *                    - numbers
     *                    - '-' characters
     * @param description the description of the command
     *                    Its length must between 1 and 100
     * @param type        the type of the argument
     */
    public Argument(String name, String description, SlashCommandOptionType type) {
        this.name = name;
        this.description = description;
        this.type = type;

        if (!name.matches("^[\\w-]{1,32}$")) {
            throw new IllegalArgumentException("Name can contain only word characters, numbers or '-' characters, and its length must between 1 and 32");
        }

        if (description.length() < 1 || description.length() > 100) {
            throw new IllegalArgumentException("Description's length must between 1 and 100");
        }
    }

    /**
     * @return the argument's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the argument's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return type of the argument
     */
    public SlashCommandOptionType getType() {
        return type;
    }

    /**
     * @return the argument's raw value
     */
    public abstract Object getValue();

    /**
     * @return the class of the result's type
     */
    public abstract Class<?> getResultType();

    /**
     * @return the command option that need for to register the argument
     */
    public abstract SlashCommandOption getCommandOption();

    /**
     * Sets the argument mismatch listener
     *
     * @param listener the listener
     */
    public void setOnMismatch(ArgumentMismatchEventListener listener) {
        this.mismatchListener = listener;
    }

    /**
     * Gets the argument mismatch listener
     *
     * @return {@link Optional#empty()} when argument mismatch listener is not specified,
     * otherwise {@link Optional#of(Object)} with the listener
     */
    public Optional<ArgumentMismatchEventListener> getMismatchListener() {
        return Optional.ofNullable(mismatchListener);
    }

}
