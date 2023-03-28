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
import me.s3ns3iw00.jcommands.argument.validator.ArgumentValidation;
import me.s3ns3iw00.jcommands.event.listener.ArgumentMismatchEventListener;
import org.javacord.api.interaction.SlashCommandOptionType;

/**
 * An argument that only accepts {@link Long} input inside long's range
 *
 * @author S3nS3IW00
 */
public class NumberArgument extends InputArgument<Long, Long> {

    public NumberArgument(String name, String description) {
        super(name, description, SlashCommandOptionType.LONG, Long.class);
    }

    /**
     * Creates an inclusive range validation
     *
     * @param min the minimum
     * @param max the maximum
     * @return the {@link ArgumentValidation} to be able to specify a custom response with {@link ArgumentValidation#thenRespond(ArgumentMismatchEventListener)}
     */
    public ArgumentValidation<Long> whenNotInRange(long min, long max) {
        return getArgumentValidator().when(number -> number <= min || number >= max);
    }

    /**
     * Sets a range for the number
     * NOTE: this is an inclusive range
     *
     * @param min the minimum
     * @param max the maximum
     * @deprecated use {@link NumberArgument#whenNotInRange(long, long)}
     */
    @Deprecated
    public void setRange(long min, long max) {
    }
}
