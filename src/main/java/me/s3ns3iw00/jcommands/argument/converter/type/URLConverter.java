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
package me.s3ns3iw00.jcommands.argument.converter.type;

import me.s3ns3iw00.jcommands.argument.converter.ArgumentResultConverter;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Converts to {@link URL} from the given value that is expectedly is a valid {@link URL}.
 *
 * @author S3nS3IW00
 */
public class URLConverter implements ArgumentResultConverter<String, URL> {

    @Override
    public URL convertTo(String value) {
        try {
            return new URL(value);
        } catch (MalformedURLException e) {
            return null;
        }
    }

}
