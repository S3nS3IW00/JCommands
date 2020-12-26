package me.s3ns3iw00.jcommands.argument.converter.type;

import me.s3ns3iw00.jcommands.MessageCommandHandler;
import me.s3ns3iw00.jcommands.argument.converter.ArgumentResultConverter;

public class ChannelConverter implements ArgumentResultConverter {

    @Override
    public Object convertTo(String value) {
        return MessageCommandHandler.getApi().getServerChannelById(value).orElse(null);
    }

}
