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

import me.s3ns3iw00.jcommands.CommandHandler;
import me.s3ns3iw00.jcommands.argument.converter.ArgumentResultConverter;
import me.s3ns3iw00.jcommands.argument.type.RegexArgument;

import java.util.Optional;
import java.util.regex.Matcher;

/**
 * This class converts string values into the given types
 *
 * @author S3nS3IW00
 */
public class ArgumentResult {

    private final Class<?> clazz;
    private final Object o;

    /**
     * Converts the string value into the type that is specified in the argument.
     *
     * @param argument the argument
     */
    public ArgumentResult(Argument argument) {
        clazz = argument.getResultType();
        Object value = argument.getValue();
        if (clazz.isAssignableFrom(value.getClass())) {
            o = value;
        } else {
            Optional<ArgumentResultConverter> converter = CommandHandler.getArgumentConverter(clazz);
            if (converter.isPresent()) {
                o = converter.get().convertTo(argument.getValue());
            } else {
                if (value.getClass() == String.class) {
                    String s = (String) value;
                    if (Boolean.class == clazz) {
                        o = Boolean.parseBoolean(s);
                    } else if (Byte.class == clazz) {
                        o = Byte.parseByte(s);
                    } else if (Short.class == clazz) {
                        o = Short.parseShort(s);
                    } else if (Integer.class == clazz) {
                        o = Integer.parseInt(s);
                    } else if (Long.class == clazz) {
                        o = Long.parseLong(s);
                    } else if (Float.class == clazz) {
                        o = Float.parseFloat(s);
                    } else if (Double.class == clazz) {
                        o = Double.parseDouble(s);
                    } else {
                        throw new NullPointerException("No converter found for type: " + clazz.getName());
                    }
                } else {
                    throw new NullPointerException("No converter found for type: " + clazz.getName());
                }
            }
        }
    }

    /**
     * @param <T> the type of clazz
     * @return the converted value with T type
     */
    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) clazz.cast(o);
    }

}
