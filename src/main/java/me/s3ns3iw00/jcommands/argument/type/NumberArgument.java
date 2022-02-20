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

import org.javacord.api.interaction.SlashCommandOptionType;

/**
 * An argument that only accepts number inputs inside long's range
 *
 * @author S3nS3IW00
 */
public class NumberArgument extends ValueArgument {

    // Define default range
    private long min = Long.MIN_VALUE, max = Long.MAX_VALUE;

    public NumberArgument(String name, String description) {
        super(name, description, SlashCommandOptionType.LONG, Long.class);
    }

    /**
     * Sets a range for the number
     * NOTE: this is an inclusive range
     *
     * @param min the minimum
     * @param max the maximum
     */
    public void setRange(long min, long max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Extends the {@link ValueArgument#isValid(Object)} method with range checking
     *
     * @param value the value
     * @return is the value in the range or not
     */
    @Override
    public boolean isValid(Object value) {
        if (super.isValid(value)) {
            try {
                return (long) value >= min && (long) value <= max;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
}
