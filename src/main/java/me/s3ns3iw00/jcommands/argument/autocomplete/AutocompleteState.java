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
package me.s3ns3iw00.jcommands.argument.autocomplete;

import me.s3ns3iw00.jcommands.Command;
import me.s3ns3iw00.jcommands.argument.Argument;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

import java.util.Map;
import java.util.Optional;

/**
 * Represents a state of autocomplete
 * It contains the {@link Command}, the current {@link Argument} and its value, the other specified arguments and its values, the {@link TextChannel} if presents and the {@link User}
 */
public class AutocompleteState {

    private final Command command;
    private final TextChannel channel;
    private final User sender;

    private final Argument currentArgument;
    private final Object currentValue;
    private final Map<Argument, Object> argumentValues;

    public AutocompleteState(Command command, TextChannel channel, User sender, Argument currentArgument, Object currentValue, Map<Argument, Object> argumentValues) {
        this.command = command;
        this.channel = channel;
        this.sender = sender;
        this.currentArgument = currentArgument;
        this.currentValue = currentValue;
        this.argumentValues = argumentValues;
    }

    public Command getCommand() {
        return command;
    }

    public Optional<TextChannel> getChannel() {
        return Optional.ofNullable(channel);
    }

    public User getSender() {
        return sender;
    }

    public Argument getCurrentArgument() {
        return currentArgument;
    }

    public Object getCurrentValue() {
        return currentValue;
    }

    public Map<Argument, Object> getArgumentValues() {
        return argumentValues;
    }
}
