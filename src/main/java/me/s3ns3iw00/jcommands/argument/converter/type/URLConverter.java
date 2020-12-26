package me.s3ns3iw00.jcommands.argument.converter.type;

import me.s3ns3iw00.jcommands.argument.converter.ArgumentResultConverter;

import java.net.MalformedURLException;
import java.net.URL;

public class URLConverter implements ArgumentResultConverter {

    @Override
    public Object convertTo(String value) {
        try {
            return new URL(value);
        } catch (MalformedURLException e) {
            return null;
        }
    }

}
