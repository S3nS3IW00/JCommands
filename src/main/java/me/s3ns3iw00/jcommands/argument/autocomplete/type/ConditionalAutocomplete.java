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
package me.s3ns3iw00.jcommands.argument.autocomplete.type;

import me.s3ns3iw00.jcommands.argument.autocomplete.Autocomplete;
import me.s3ns3iw00.jcommands.argument.util.ArgumentState;
import me.s3ns3iw00.jcommands.argument.util.Choice;

import java.util.List;

/**
 * A type of {@link Autocomplete} that returns a list of {@link Choice} based on conditions
 */
public class ConditionalAutocomplete extends Autocomplete {

    private final ConditionResult conditionResult;

    public ConditionalAutocomplete(ConditionResult conditionResult) {
        this.conditionResult = conditionResult;
    }

    @Override
    public List<Choice> getResult(ArgumentState state) {
        return conditionResult.getChoices(state);
    }

    /**
     * Functional interface that returns the list of {@link Choice} based on the {@link ArgumentState}
     */
    public interface ConditionResult {

        List<Choice> getChoices(ArgumentState state);

    }


}