package me.s3ns3iw00.jcommands.argument.converter.type;

import me.s3ns3iw00.jcommands.MessageCommandHandler;
import me.s3ns3iw00.jcommands.argument.converter.ArgumentResultConverter;
import org.javacord.api.entity.user.User;

import java.util.concurrent.CompletableFuture;

public class MentionConverter implements ArgumentResultConverter {

    @Override
    public Object convertTo(String value) {
        CompletableFuture<User> userFuture = MessageCommandHandler.getApi().getUserById(value);
        userFuture.join();
        try {
            return userFuture.get();
        } catch (Exception e) {
            return null;
        }
    }

}
