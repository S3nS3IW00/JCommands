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

import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.LinkedList;

/**
 * Represents a sub argument
 * Classes that extends this class can have arguments
 *
 * @param <T> the type of arguments that can contain this argument
 */
public abstract class SubArgument<T extends Argument, R> extends Argument<R, R> {

    private final LinkedList<T> arguments = new LinkedList<>();

    /**
     * Constructs the argument with the default requirements
     *
     * @param name        the argument's name
     * @param description the argument's description
     * @param type        the type of the input value
     */
    public SubArgument(String name, String description, SlashCommandOptionType type) {
        super(name, description, type);
    }

    public LinkedList<T> getArguments() {
        return arguments;
    }

    /**
     * Adds an argument to the argument
     *
     * @param argument the argument
     */
    public abstract void addArgument(T argument);
}
