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

import java.util.function.Predicate;

/**
 * A validation builder that is contained by {@link ArgumentValidator}
 *
 * @param <T> the argument's input type
 */
public class ArgumentValidation<T> {

    private final ArgumentValidator<T> validator;

    private final Predicate<T> predicate;
    private ArgumentMismatchEventListener event;

    /**
     * Constructs the validation
     *
     * @param validator the {@link ArgumentValidator}
     * @param predicate the {@link Predicate}
     */
    public ArgumentValidation(ArgumentValidator<T> validator, Predicate<T> predicate) {
        this.validator = validator;
        this.predicate = predicate;
    }

    /**
     * Creates the {@link ArgumentMismatchEventListener} that gets triggered
     *
     * @param event the {@link ArgumentMismatchEventListener}
     * @return the {@link ArgumentValidator}
     */
    public ArgumentValidator<T> thenRespond(ArgumentMismatchEventListener event) {
        this.event = event;

        return validator;
    }

    public Predicate<T> getPredicate() {
        return predicate;
    }

    public ArgumentMismatchEventListener getEvent() {
        if (event == null) {
            throw new IllegalStateException("Event listener must be specified for every validation!");
        }

        return event;
    }
}
