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
package me.s3ns3iw00.jcommands.argument.concatenation.type;

import me.s3ns3iw00.jcommands.argument.concatenation.Concatenator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Represents a type of {@link Concatenator}
 * <p>
 * Concatenates the arguments' result into a specific type with creating a new instance of it
 * See {@link TypeConcatenator#createInstance(Class, Object...)} for more information
 */
public class TypeConcatenator<C, R> extends Concatenator<C, R> {

    public TypeConcatenator(Class<R> resultType) {
        super(resultType);
    }

    /**
     * Returns result of {@link TypeConcatenator#createInstance(Class, Object...)}
     *
     * @param results the results of the arguments
     * @return the instance
     */
    @Override
    public C concatenate(Object... results) {
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
    private C createInstance(Class<?> clazz, Object... parameters)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        int i = 0;
        boolean match = false;
        while (i < clazz.getConstructors().length && !match) {
            Constructor<?> cons = clazz.getConstructors()[i];

            if (cons.getParameterCount() == parameters.length) {
                int j = 0;
                boolean paramMatch = true;
                while (j < cons.getParameterCount() && paramMatch) {
                    Class<?> consParam = cons.getParameterTypes()[j];
                    Class<?> param = parameters[j].getClass();
                    if ((consParam != param) &&
                            (!consParam.isPrimitive() ||
                                    ((consParam == byte.class && param != Byte.class) ||
                                            (consParam == short.class && param != Short.class) ||
                                            (consParam == int.class && param != Integer.class) ||
                                            (consParam == long.class && param != Long.class) ||
                                            (consParam == float.class && param != Float.class) ||
                                            (consParam == double.class && param != Double.class) ||
                                            (consParam == boolean.class && param != Boolean.class) ||
                                            (consParam == char.class && param != Character.class)))) {
                        paramMatch = false;
                    } else {
                        j++;
                    }
                }

                if (j < cons.getParameterCount()) {
                    i++;
                } else {
                    match = true;
                }
            } else {
                i++;
            }
        }

        if (i < clazz.getConstructors().length) {
            return (C) clazz.getConstructors()[i].newInstance(Arrays.stream(parameters).toArray());
        } else {
            throw new NullPointerException("No constructor found that matches with parameters count, type and order");
        }
    }

}
