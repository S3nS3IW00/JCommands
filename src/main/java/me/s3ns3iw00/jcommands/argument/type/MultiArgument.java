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

import me.s3ns3iw00.jcommands.argument.Argument;
import org.javacord.api.interaction.SlashCommandOption;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * An argument that accepts exactly one of the specified values
 *
 * @author S3nS3IW00
 * @deprecated don't use, because it is not working
 *             use {@link ComboArgument} instead
 *             it will be removed in the next version
 */
@Deprecated
public class MultiArgument extends Argument {

    private final Collection<String> names;
    private String input;

    public MultiArgument(String... names) {
        super(null, null, null);
        this.names = Collections.unmodifiableCollection(Arrays.asList(names));
    }

    public String getName() {
        StringBuilder nameBuilder = new StringBuilder();
        int i = 0;
        for (String name : names) {
            nameBuilder.append(name);
            if (i + 1 < names.size()) nameBuilder.append("|");
            i++;
        }
        return nameBuilder.toString();
    }

    @Override
    public String getDescription() {
        return null;
    }

    public String getValue() {
        return input;
    }

    public boolean isValid(String input) {
        this.input = input;
        return names.contains(input);
    }

    public Class<?> getResultType() {
        return String.class;
    }

    @Override
    public SlashCommandOption getCommandOption() {
        return null;
    }

}
