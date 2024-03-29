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
package me.s3ns3iw00.jcommands.argument.type;

import me.s3ns3iw00.jcommands.argument.InputArgument;
import me.s3ns3iw00.jcommands.argument.converter.type.URLConverter;
import me.s3ns3iw00.jcommands.argument.validator.ArgumentValidation;
import me.s3ns3iw00.jcommands.argument.validator.type.RegexPredicate;
import me.s3ns3iw00.jcommands.event.listener.ArgumentMismatchEventListener;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.net.URL;

/**
 * An argument that only accepts {@link String} input that is a valid URL (can be parsed to {@link URL})
 *
 * @author S3nS3IW00
 */
public class URLArgument extends InputArgument<String, URL> {

    private final String urlRegex = "(?:(?:https?|ftp):\\/\\/)(?:\\S+(?::\\S*)?@)?(?:(?!10(?:\\.\\d{1,3}){3})(?!127(?:\\.\\d{1,3}){3})(?!169\\.254(?:\\.\\d{1,3}){2})(?!192\\.168(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\x{00a1}-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}-\\x{ffff}0-9]+)(?:\\.(?:[a-z\\x{00a1}-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}-\\x{ffff}0-9]+)*(?:\\.(?:[a-z\\x{00a1}-\\x{ffff}]{2,})))(?::\\d{2,5})?(?:\\/[^\\s]*)?";

    private final ArgumentValidation<String> urlValidation;

    public URLArgument(String name, String description) {
        super(name, description, SlashCommandOptionType.STRING, URL.class);

        // Adds a listener when the input is not valid for the regex
        urlValidation = getArgumentValidator().when(RegexPredicate.notValidFor(urlRegex));

        // Fallback response, can be overwritten with whenInvalidUrl() method
        urlValidation.thenRespond(event -> {
            event.getResponder().respondNow()
                    .setContent("The specified URL is not valid!")
                    .respond();
        });

        // Convert result to URL
        convertResult(new URLConverter());
    }

    /**
     * Returns the validation to be able to set the response that will be sent when the input is not valid for the regex
     *
     * @return the existing validation
     */
    public ArgumentValidation<String> whenInvalidUrl() {
        return urlValidation;
    }

}
