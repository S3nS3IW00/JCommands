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
package me.s3ns3iw00.jcommands;

import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;
import org.javacord.api.interaction.callback.InteractionOriginalResponseUpdater;

import java.util.concurrent.CompletableFuture;

/**
 * A class that takes methods out from {@link SlashCommandInteraction} that are to respond to a command
 *
 * {@link CommandHandler} returns it inside {@link me.s3ns3iw00.jcommands.listener.CommandActionListener}
 */
public class CommandResponder {

    /**
     * The interaction
     */
    private final SlashCommandInteraction interaction;

    public CommandResponder(SlashCommandInteraction interaction) {
        this.interaction = interaction;
    }

    /**
     * Creates an updater that listens for the response
     * In this case a waiting message can be sent to the user
     * NOTE: Late responses need to be sent within 15 minutes, otherwise Discord drops it
     *
     * @return the response updater
     */
    public CompletableFuture<InteractionOriginalResponseUpdater> respondLater() {
        return interaction.respondLater();
    }

    /**
     * Creates a responder to respond immediately to the command
     *
     * @return the immediate responder builder
     */
    public InteractionImmediateResponseBuilder respondNow() {
        return interaction.createImmediateResponder();
    }

}
