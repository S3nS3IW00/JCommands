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

/**
 * An argument that only accepts number inputs inside integer's range (from -2147483648 to 2147483647)
 *
 * @author S3nS3IW00
 */
public class NumberArgument extends RegexArgument {

    // Define default range
    private int min = Integer.MIN_VALUE, max = Integer.MAX_VALUE;

    public NumberArgument(String name) {
        super(name, "^-?\\d+$", Integer.class);
    }

    public void setRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean isValid(String input) {
        if (super.isValid(input)) {
            try {
                int value = Integer.parseInt(input);
                return value >= min && value <= max;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
}
