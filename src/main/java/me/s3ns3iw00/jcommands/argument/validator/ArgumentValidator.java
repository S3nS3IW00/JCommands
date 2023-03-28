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
package me.s3ns3iw00.jcommands.argument.validator;

import me.s3ns3iw00.jcommands.event.listener.ArgumentMismatchEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A {@link Function} that validates the argument's value by specific validation aspects
 *
 * @param <T> the argument's input type
 */
public class ArgumentValidator<T> implements Function<T, Optional<ArgumentMismatchEventListener>> {

    private final List<ArgumentValidation<T>> validations = new ArrayList<>();

    /**
     * Creates a validation
     *
     * @param predicate is the {@link Predicate}
     * @return the {@link ArgumentValidation}
     */
    public ArgumentValidation<T> when(Predicate<T> predicate) {
        ArgumentValidation<T> validation = new ArgumentValidation<>(this, predicate);
        validations.add(validation);

        return validation;
    }

    /**
     * Creates a validation with {@link ArgumentPredicate}
     *
     * @param predicate is the {@link Predicate}
     * @return the {@link ArgumentValidation}
     */
    public ArgumentValidation<T> when(ArgumentPredicate<T> predicate) {
        ArgumentValidation<T> validation = new ArgumentValidation<>(this, predicate.getPredicate());
        validations.add(validation);

        return validation;
    }

    /**
     * Checks whether the given value is valid
     *
     * @param value the function argument
     * @return an {@link Optional} with the validator's {@link ArgumentMismatchEventListener} that is for which the value is not valid
     */
    @Override
    public Optional<ArgumentMismatchEventListener> apply(T value) {
        return validations.stream()
                .filter(validation -> validation.getPredicate().test(value))
                .map(ArgumentValidation::getEvent)
                .findFirst();
    }

    /**
     * Instantiates the class statically
     *
     * @return the {@link ArgumentValidator}
     * @param <T> the generic type of the {@link ArgumentValidator}
     */
    public static <T> ArgumentValidator<T> build() {
        return new ArgumentValidator<>();
    }
}
