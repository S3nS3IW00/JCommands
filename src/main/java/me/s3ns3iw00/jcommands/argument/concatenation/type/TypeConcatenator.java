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
package me.s3ns3iw00.jcommands.argument.concatenation.type;

import me.s3ns3iw00.jcommands.argument.ArgumentResult;
import me.s3ns3iw00.jcommands.argument.concatenation.Concatenator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;

/**
 * Represents a type of {@link Concatenator}
 * <p>
 * Concatenates the arguments' result into a specific type with creating a new instance of it
 * See {@link TypeConcatenator#createInstance(Class, ArgumentResult...)} for more information
 */
public class TypeConcatenator extends Concatenator {

    public TypeConcatenator(Class<?> resultType) {
        super(resultType);
    }

    @Override
    public Object concatenate(ArgumentResult... results) {
        try {
            return createInstance(getResultType(), results);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates an instance of the specified class with the arguments' results as parameters
     * Uses a special method to locate constructor with primitive type of parameter with comparing primitive type to its non-primitive pair
     * <p>
     * NOTE: To be able to create the instance, the parameters count, order and type must match with one of the constructors in the requested class
     * For more information about the exceptions see {@link java.lang.reflect.Constructor#newInstance(Object...)}
     *
     * @param clazz      is the requested type
     * @param parameters the results of the arguments
     * @return the instance of the class
     * @throws NullPointerException when the clazz doesn't contain constructor that matches with parameters count, type and order
     */
    public static Object createInstance(Class<?> clazz, ArgumentResult... parameters)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Optional<Constructor<?>> constructor = Arrays.stream(clazz.getConstructors())
                .filter(cons ->
                        cons.getParameterCount() == parameters.length &&
                                Arrays.stream(cons.getParameterTypes())
                                        .filter(consParam ->
                                                Arrays.stream(parameters)
                                                        .map(param -> param.get().getClass())
                                                        .anyMatch(param ->
                                                                (consParam == param) ||
                                                                        (consParam.isPrimitive() &&
                                                                                ((consParam == byte.class && param != Integer.class) ||
                                                                                        (consParam == short.class && param == Short.class) ||
                                                                                        (consParam == int.class && param == Integer.class) ||
                                                                                        (consParam == long.class && param == Long.class) ||
                                                                                        (consParam == float.class && param == Float.class) ||
                                                                                        (consParam == double.class && param == Double.class) ||
                                                                                        (consParam == boolean.class && param == Boolean.class) ||
                                                                                        (consParam == char.class && param == Character.class)))
                                                        )
                                        ).count() == cons.getParameterCount()
                )
                .findFirst();

        if (constructor.isPresent()) {
            return constructor.get().newInstance(Arrays.stream(parameters).map(ArgumentResult::get).toArray());
        } else {
            throw new NullPointerException("No constructor found that matches with parameters count, type and order");
        }
    }

}
