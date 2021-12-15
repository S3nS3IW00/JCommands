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
import me.s3ns3iw00.jcommands.argument.SubArgument;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents an argument with {@code SUB_COMMAND_GROUP} type that only can contain {@link ConstantArgument}
 * This is for grouping {@code SUB_COMMAND} options because {@code SUB_COMMANDS} cannot be nested
 */
public class GroupArgument extends SubArgument<ConstantArgument> {

    public GroupArgument(String name, String description) {
        super(name, description, SlashCommandOptionType.SUB_COMMAND_GROUP);
    }

    @Override
    public Object getValue() {
        return getName();
    }

    @Override
    public Class<?> getResultType() {
        return String.class;
    }

    @Override
    public SlashCommandOption getCommandOption() {
        return SlashCommandOption.createWithOptions(getType(), getName(), getDescription(), getArguments().stream().map(Argument::getCommandOption).collect(Collectors.toList()));
    }

    /**
     * Adds a {@link ConstantArgument} to the argument
     *
     * @param argument the argument
     */
    public void addArgument(ConstantArgument argument) {
        getArguments().add(argument);
    }

    /**
     * Adds a list of {@link ConstantArgument} to the argument
     *
     * @param arguments the arguments
     */
    public void addArgument(ConstantArgument... arguments) {
        Arrays.stream(arguments).forEach(this::addArgument);
    }

}
