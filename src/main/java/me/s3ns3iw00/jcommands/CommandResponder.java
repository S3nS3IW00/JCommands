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
package me.s3ns3iw00.jcommands;

import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionFollowupMessageBuilder;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;
import org.javacord.api.interaction.callback.InteractionOriginalResponseUpdater;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * A class that takes methods out from {@link SlashCommandInteraction} that are to respond to a command
 * <p>
 * {@link CommandHandler} returns it with various events to be able to respond them
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
     * Creates an ephemeral updater that listens for the response
     * In this case a waiting message can be sent to the user
     * NOTE: Late responses need to be sent within 15 minutes, otherwise Discord drops it
     *
     * @return the response updater
     */
    public CompletableFuture<InteractionOriginalResponseUpdater> respondLaterEphemeral() {
        return interaction.respondLater(true);
    }

    /**
     * Creates a responder to respond immediately to the command
     *
     * @return the immediate responder builder
     */
    public InteractionImmediateResponseBuilder respondNow() {
        return interaction.createImmediateResponder();
    }

    /**
     * Creates a followup message builder to send the final result of late response
     *
     * @return the followup message builder
     */
    public InteractionFollowupMessageBuilder followUp() {
        return interaction.createFollowupMessageBuilder();
    }

    /**
     * Creates a responder to respond with a modal to the command
     *
     * @return the {@link CompletableFuture<Void>} that completes when the modal is opened
     */
    public CompletableFuture<Void> respondWithModal(String customId, String title, HighLevelComponent... highLevelComponents) {
        return interaction.respondWithModal(customId, title, highLevelComponents);
    }

    /**
     * Creates a responder to respond with a modal to the command
     *
     * @return the {@link CompletableFuture<Void>} that completes when the modal is opened
     */
    public CompletableFuture<Void> respondWithModal(String customId, String title, List<HighLevelComponent> highLevelComponents) {
        return interaction.respondWithModal(customId, title, highLevelComponents);
    }

}
