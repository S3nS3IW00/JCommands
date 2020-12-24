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

import org.javacord.api.entity.user.User;

/**
 * An argument that has a regex that only accepts inputs what are mention tags
 *
 * @author S3nS3IW00
 */
public class MentionArgument extends RegexArgument {

    public MentionArgument(String name) {
        super(name, "\\<@!?\\d+\\>", User.class);
    }

    @Override
    public String getValue() {
        return super.getValue().substring(super.getValue().toCharArray()[2] == '!' ? 3 : 2, super.getValue().length() - 1);
    }

}
