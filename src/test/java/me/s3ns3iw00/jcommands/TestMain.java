package me.s3ns3iw00.jcommands;

import me.s3ns3iw00.jcommands.argument.ArgumentResult;
import me.s3ns3iw00.jcommands.argument.type.*;
import me.s3ns3iw00.jcommands.argument.util.Choice;
import me.s3ns3iw00.jcommands.argument.validator.type.RegexPredicate;
import me.s3ns3iw00.jcommands.type.ServerCommand;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.Attachment;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Optional;
import java.util.Set;

public class TestMain {

    public static void main(String[] launchArgs) {
        DiscordApi api = new DiscordApiBuilder()
                .setToken("TOKEN_HERE") // <-- Paste your bot TOKEN here
                .setAllIntents()
                .login()
                .join();
        System.out.println("Invite the bot with the following url: " + api.createBotInvite());

        // Initiate the listener.
        CommandHandler.setApi(api);

        // Creating instance of the servers where the bot on
        // -- DON'T FORGET TO REPLACE THIS WITH YOUR SERVER ID --
        Server myServer = api.getServerById(787364551598407700L).orElse(null);

        // Create a command with a name and a description
        ServerCommand introduceCommand = new ServerCommand("iam", "This command is for to introduce yourself.");
        // Let's create our command's first argument.
        StringArgument<String> nameArgument = new StringArgument<>("fullname", "Your first and last name separated with space (Firstname Lastname)", String.class);
        // This argument only accepts two words that are separated with space and either started with capitalized letter.
        // Send back a message to the user when the user's input is not valid for the pattern
        // EPHEMERAL flag means that the response will only be visible for the user
        nameArgument.getArgumentValidator().when(RegexPredicate.notValidFor("(?<first>[A-Z][a-z]+) (?<last>[A-Z][a-z]+)")).thenRespond(event -> {
            event.getResponder().respondNow()
                    .setContent("The name is not valid for the pattern. :face_with_raised_eyebrow:")
                    .setFlags(MessageFlag.EPHEMERAL)
                    .respond();
        });
        // We want to know the user's gender so make an argument that has two acceptable values.
        ComboArgument genderArgument = new ComboArgument("gender", "Your gender", SlashCommandOptionType.LONG);
        genderArgument.addChoice("male", 0);
        genderArgument.addChoice("female", 1);
        // How old is the user?
        NumberArgument<Long> ageArgument = new NumberArgument<>("age", "Your age (number between 0 and 99)", Long.class);
        // Set a range on age and send back a message to the user when the user's input is not between that range
        ageArgument.whenNotInRange(0, 99).thenRespond(event -> {
            event.getResponder().respondNow()
                    .setContent("The age must between 0 and 99. :face_with_raised_eyebrow:")
                    .setFlags(MessageFlag.EPHEMERAL)
                    .respond();
        });
        // Let's ask the user to mention his or her best friend,
        MentionArgument<User> bestFriendArgument = new MentionArgument<>("bestfriend", "Your best friend on Discord", User.class);
        // to mention his or her favourite channel on this server
        ChannelArgument<ServerChannel> favouriteChannelArgument = new ChannelArgument<>("favouritechannel", "Your favourite channel on this server", ServerChannel.class);
        // and to send us a funny picture's url, that is just an optional argument
        AttachmentArgument<Attachment> funnyPictureArgument = new AttachmentArgument<>("funnypicture", "A funny picture (png or jpg)", Attachment.class);
        // Limit the size for the attachment, and send back a response when the uploaded attachment's size exceeds the limit
        funnyPictureArgument.whenAboveMaxSizeInMB(1.0).thenRespond(event -> {
            event.getResponder().respondNow()
                    .setContent("The attachment's size can be 1M at maximum :face_with_raised_eyebrow:")
                    .setFlags(MessageFlag.EPHEMERAL)
                    .respond();
        });
        // Limit the extensions for the attachment, and send back a response when the uploaded attachment's extension is invalid
        funnyPictureArgument.whenInvalidExtension(Set.of("png", "jpg")).thenRespond(event -> {
            event.getResponder().respondNow()
                    .setContent("The uploaded attachment is not valid! The only acceptable extensions are `png` and `jpg` :face_with_raised_eyebrow:")
                    .setFlags(MessageFlag.EPHEMERAL)
                    .respond();
        });
        funnyPictureArgument.setOptional();
        // Add arguments to the command
        introduceCommand.addArgument(nameArgument, genderArgument, ageArgument, bestFriendArgument, favouriteChannelArgument, funnyPictureArgument);
        // Let's make an action listener where we can listen for inputs.
        introduceCommand.setOnAction(event -> {
            // Get the arguments from the event
            ArgumentResult[] args = event.getArguments();

            // Get the value of the first argument
            String fullName = args[0].get();

            // The gender contains the choice
            Choice gender = args[1].get();

            // The age from a NumberArgument so it's a number by default
            // It has been validated so range checking is unnecessary
            long age = args[2].get();

            User bestFriend = args[3].get();
            Channel favouriteChannel = args[4].get();

            // This is an optional argument, so its result is stored in an Optional
            Optional<Attachment> funnyPicture = args[5].get();

            // Something that uses all the arguments
            MessageBuilder responseMessage = new MessageBuilder();
            responseMessage.append("Hi " + fullName + "! You are very " + (gender.getValueAsLong() == 0 ? "handsome" : "beautiful") + " :heart: As I can see you are " + (age >= 10 && age < 20 ? "" : "not ") + "a teenager.");
            if (bestFriend.isBot()) {
                responseMessage.append(" What? Did you know that your best friend is a BOT?");
            }
            if (favouriteChannel.getType() == ChannelType.SERVER_VOICE_CHANNEL) {
                responseMessage.append(" I like voice channels too!");
            }
            funnyPicture.ifPresent(attachment -> responseMessage.append(" Here is your funny picture: ").append(attachment.getUrl()));

            // Send the response message to the user with the responder from the event
            // EPHEMERAL flag means that the response will only be visible for the user
            event.getResponder().respondNow()
                    .setContent(responseMessage.getStringBuilder().toString())
                    .setFlags(MessageFlag.EPHEMERAL)
                    .respond();
        });
        // And don't forget to register the command on the server(s). (I always forget it and never know what's wrong :D)
        CommandHandler.registerCommand(introduceCommand, myServer);
    }

}
