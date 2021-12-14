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
package me.s3ns3iw00.jcommands.argument;

import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

/**
 * Represents argument that can have multiple value depends on the user input and the restrictions of the argument
 * These arguments can be optional
 */
public abstract class InputArgument extends Argument {

    private boolean optional = false;

    /**
     * Constructs the argument with the default requirements
     *
     * @param name        the argument's name
     * @param description the argument's description
     * @param type        the type of the input value
     */
    public InputArgument(String name, String description, SlashCommandOptionType type) {
        super(name, description, type);
    }

    @Override
    public SlashCommandOption getCommandOption() {
        return SlashCommandOption.create(getType(), getName(), getDescription(), !optional);
    }

    public boolean isOptional() {
        return optional;
    }

    /**
     * Sets the argument optional
     * Only the last argument of the options can be set as optional
     */
    public void setOptional() {
        optional = true;
    }

}
