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

import me.s3ns3iw00.jcommands.CommandHandler;
import me.s3ns3iw00.jcommands.argument.ability.Optionality;
import me.s3ns3iw00.jcommands.argument.converter.ArgumentResultConverter;

import java.util.Optional;

/**
 * This class converts a value into the given type with parsing string values and using {@link ArgumentResultConverter}
 *
 * @author S3nS3IW00
 */
public class ArgumentResult {

    private final Class<?> clazz;
    private final Object value;

    /**
     * Default constructor
     *
     * @param clazz the class the value need to converted to
     * @param value the value that need to be converted
     */
    public ArgumentResult(Class<?> clazz, Object value) {
        this.clazz = clazz;
        this.value = value;
    }

    /**
     * Constructs the class with an {@link Argument}
     *
     * @param argument the argument
     */
    public ArgumentResult(Argument argument) {
        this.clazz = argument.getResultType();

        if (argument instanceof Optionality && ((Optionality) argument).isOptional()) {
            this.value = ((Optionality) argument).getOptionalValue();
        } else {
            this.value = argument.getValue();
        }
    }

    /**
     * Converts the value
     *
     * @param value the value of the argument
     * @return the result of the conversion
     * @throws NullPointerException if converter haven't specified for this type of conversion
     */
    private Object convert(Object value) {
        if (clazz.isAssignableFrom(value.getClass())) {
            return value;
        } else {
            Optional<ArgumentResultConverter> converter = CommandHandler.getArgumentConverter(clazz);
            if (converter.isPresent()) {
                return converter.get().convertTo(value);
            } else {
                if (value.getClass() == String.class) {
                    String s = (String) value;
                    if (Boolean.class == clazz) {
                        return Boolean.parseBoolean(s);
                    } else if (Byte.class == clazz) {
                        return Byte.parseByte(s);
                    } else if (Short.class == clazz) {
                        return Short.parseShort(s);
                    } else if (Integer.class == clazz) {
                        return Integer.parseInt(s);
                    } else if (Long.class == clazz) {
                        return Long.parseLong(s);
                    } else if (Float.class == clazz) {
                        return Float.parseFloat(s);
                    } else if (Double.class == clazz) {
                        return Double.parseDouble(s);
                    } else {
                        throw new NullPointerException("No converter found for type: " + value.getClass() + " -> " + clazz.getName());
                    }
                } else {
                    throw new NullPointerException("No converter found for type: " + value.getClass() + " -> " + clazz.getName());
                }
            }
        }
    }

    /**
     * Runs the process method and returns its result
     *
     * @param <T> the type of clazz
     * @return the converted value with T type
     */
    @SuppressWarnings("unchecked")
    public <T> T get() {
        if (value instanceof Optional) {
            return ((Optional<?>) value).map(o -> (T) Optional.of(clazz.cast(convert(o)))).orElseGet(() -> (T) Optional.empty());
        }

        return (T) clazz.cast(convert(value));
    }

}
