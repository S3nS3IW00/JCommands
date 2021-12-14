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
import me.s3ns3iw00.jcommands.argument.InputArgument;
import me.s3ns3iw00.jcommands.argument.SubArgument;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Only accepts inputs that are exactly the same as its name
 *
 * @author S3nS3IW00
 */
public class ConstantArgument extends SubArgument {

    public ConstantArgument(String name, String description) {
        super(name, description, SlashCommandOptionType.SUB_COMMAND);
    }

    public Object getValue() {
        return getName();
    }

    public Class<?> getResultType() {
        return String.class;
    }

    @Override
    public SlashCommandOption getCommandOption() {
        return SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, getName(), getDescription(), getArguments().stream().map(Argument::getCommandOption).collect(Collectors.toList()));
    }

    /**
     * Adds arguments to the argument
     *
     * @param argument a list of arguments
     */
    public void addArgument(InputArgument... argument) {
        getArguments().addAll(Arrays.asList(argument));
    }
}
