/*
 * Copyright (C) 2020 S3nS3IW00
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An argument that only accepts inputs what are matching the pattern
 *
 * @author S3nS3IW00
 */
public class RegexArgument extends ConstantArgument {

    private String input;
    private Pattern pattern;
    private Matcher matcher;
    private Class<?> type = Matcher.class;

    public RegexArgument(String name, String regex) {
        super(name);
        pattern = Pattern.compile(regex);
    }

    /**
     * This constructor modifies the argument's result type.
     *
     * @param type a non-primitive data type
     */
    public RegexArgument(String name, String regex, Class<?> type) {
        this(name, regex);
        this.type = type;
    }

    /**
     * Validates the input by the specified regex code
     *
     * @param input the string what needs to be validated
     * @return true if the input is valid or false if is not
     */
    @Override
    public boolean isValid(String input) {
        return (matcher = pattern.matcher(this.input = input)).lookingAt();
    }

    public Matcher getMatcher() {
        return matcher;
    }

    @Override
    public String getValue() {
        return input;
    }

    @Override
    public Class<?> getResultType() {
        return type;
    }

}
