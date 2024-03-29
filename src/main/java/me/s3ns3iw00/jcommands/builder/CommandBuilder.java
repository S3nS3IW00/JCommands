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
package me.s3ns3iw00.jcommands.builder;

import me.s3ns3iw00.jcommands.Command;
import me.s3ns3iw00.jcommands.argument.Argument;
import me.s3ns3iw00.jcommands.argument.InputArgument;
import me.s3ns3iw00.jcommands.argument.concatenation.Concatenator;
import me.s3ns3iw00.jcommands.event.listener.CommandActionEventListener;
import org.javacord.api.entity.permission.PermissionType;

import java.util.Arrays;

/**
 * Useful class that makes {@link Command} creations more comfortable
 *
 * @param <B> the type of the builder
 * @author S3nS3IW00
 */
@SuppressWarnings("unchecked")
public abstract class CommandBuilder<B extends CommandBuilder<B>> {

    /**
     * Calls {@link Command#addArgument(Argument...)}
     *
     * @return this class
     */
    public B arguments(Argument... arguments) {
        getCommand().addArgument(arguments);
        return (B) this;
    }

    /**
     * Calls {@link Command#addConcatenator(Concatenator, me.s3ns3iw00.jcommands.argument.InputArgument...)} on every element of the list
     *
     * @return this class
     */
    public B concatenators(ConcatenatorBuilder... concatenatorBuilders) {
        Arrays.stream(concatenatorBuilders).forEach(builder -> getCommand().addConcatenator(builder.concatenator, builder.arguments));
        return (B) this;
    }

    /**
     * Calls {@link Command#setOnAction(CommandActionEventListener)}
     *
     * @return this class
     */
    public B onAction(CommandActionEventListener listener) {
        getCommand().setOnAction(listener);
        return (B) this;
    }

    /**
     * Calls {@link Command#addPermissions(PermissionType...)}
     *
     * @param permissionTypes the permissions
     * @return this class
     */
    public B permissions(PermissionType... permissionTypes) {
        getCommand().addPermissions(permissionTypes);
        return (B) this;
    }

    /**
     * @return the command itself
     */
    public abstract Command getCommand();

    /**
     * A nested class that unifies parameters of {@link Command#addConcatenator(Concatenator, InputArgument...)} for {@link CommandBuilder#concatenators(ConcatenatorBuilder...)}
     */
    public static class ConcatenatorBuilder {

        final Concatenator concatenator;
        final InputArgument[] arguments;

        public ConcatenatorBuilder(Concatenator concatenator, InputArgument... arguments) {
            this.concatenator = concatenator;
            this.arguments = arguments;
        }

    }

}
