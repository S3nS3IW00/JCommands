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
package me.s3ns3iw00.jcommands.argument;

import me.s3ns3iw00.jcommands.MessageCommandHandler;
import me.s3ns3iw00.jcommands.argument.type.RegexArgument;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.user.User;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;

/**
 * This class converts string values into the given types
 *
 * @author S3nS3IW00
 */
public class ArgumentResult {

    private Class<?> clazz;
    private Object o;

    /**
     * Converts the string value into the type that is specified in the argument.
     *
     * @param argument the argument
     */
    public ArgumentResult(Argument argument) {
        clazz = argument.getResultType();
        if (Boolean.class == clazz) {
            o = Boolean.parseBoolean(argument.getValue());
        } else if (Byte.class == clazz) {
            o = Byte.parseByte(argument.getValue());
        } else if (Short.class == clazz) {
            o = Short.parseShort(argument.getValue());
        } else if (Integer.class == clazz) {
            o = Integer.parseInt(argument.getValue());
        } else if (Long.class == clazz) {
            o = Long.parseLong(argument.getValue());
        } else if (Float.class == clazz) {
            o = Float.parseFloat(argument.getValue());
        } else if (Double.class == clazz) {
            o = Double.parseDouble(argument.getValue());
        } else if (Matcher.class == clazz) {
            o = ((RegexArgument) argument).getMatcher();
        } else if (URL.class == clazz) {
            try {
                o = new URL(argument.getValue());
            } catch (MalformedURLException e) {
                o = null;
            }
        } else if (ServerChannel.class == clazz) {
            o = MessageCommandHandler.getApi().getServerChannelById(argument.getValue()).orElse(null);
        } else if (User.class == clazz) {
            System.out.println(argument.getValue());
            CompletableFuture<User> userFuture = MessageCommandHandler.getApi().getUserById(argument.getValue());
            userFuture.join();
            try {
                o = userFuture.get();
            } catch (Exception e) {
                o = null;
            }
        } else {
            o = argument.getValue();
        }
    }

    /**
     * @param <T> the type of clazz
     * @return the converted value with T type
     */
    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) clazz.cast(o);
    }

}
