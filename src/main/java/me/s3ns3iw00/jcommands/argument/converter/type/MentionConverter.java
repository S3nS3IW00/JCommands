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
package me.s3ns3iw00.jcommands.argument.converter.type;

import me.s3ns3iw00.jcommands.CommandHandler;
import me.s3ns3iw00.jcommands.argument.converter.ArgumentResultConverter;
import org.javacord.api.entity.user.User;

import java.util.concurrent.CompletableFuture;

/**
 * Converts to {@code User} from the given value that expectedly is the id of the {@code User}.
 *
 * @author S3nS3IW00
 */
public class MentionConverter implements ArgumentResultConverter {

    @Override
    public Object convertTo(String value) {
        try {
            CompletableFuture<User> userFuture = CommandHandler.getApi().getUserById(value);
            userFuture.join();
            return userFuture.get();
        } catch (Exception e) {
            return null;
        }
    }

}
