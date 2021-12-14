package me.s3ns3iw00.jcommands;

import me.s3ns3iw00.jcommands.argument.type.*;
import me.s3ns3iw00.jcommands.limitation.type.CategoryLimitation;
import me.s3ns3iw00.jcommands.limitation.type.ChannelLimitation;
import me.s3ns3iw00.jcommands.limitation.type.RoleLimitation;
import me.s3ns3iw00.jcommands.type.ServerCommand;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.net.URL;
import java.util.regex.Matcher;

public class TestMain {

    public static void main(String[] launchArgs) {
        DiscordApi api = new DiscordApiBuilder()
                .setToken("Nzg3MzY0NzY5NTM5Njg2NDIw.X9T4lQ.L25XLpXGbnTLk93SWCqRWBo3gdg") // <-- Paste your bot TOKEN here
                .setAllIntents()
                .login()
                .join();
        System.out.println("Invite the bot with the following url: " + api.createBotInvite());

        // Initiate the listener.
        CommandHandler.setApi(api);

        // Creating instance of the servers where the bot on
        // -- DON'T FORGET TO REPLACE THIS WITH YOUR SERVER ID --
        Server myServer = api.getServerById(787364551598407700L).get();

        // Registering the error listener
        CommandHandler.setOnError((type, responder) -> {
            String errorMessage = null;
            switch (type) {
                // Occurs when one or more of the arguments are missing or not matching the pattern.
                case BAD_ARGUMENTS:
                    errorMessage = "Something went wrong! :face_with_raised_eyebrow:";
                    break;
                // Occurs when the sender wants to use the command in a category where it is not allowed.
                case BAD_CATEGORY:
                    // Occurs when the sender wants to use the command in a channel where it is not allowed.
                case BAD_CHANNEL:
                    errorMessage = "I'm not sure about that this is the best place to use this command. :face_with_raised_eyebrow:";
                    break;
            }
            responder.respondNow()
                    .setContent(errorMessage)
                    .respond();
        });

        // Creating a command
        // -- DON'T FORGET TO REPLACE CATEGORY, CHANNEL AND ROLE IDs WITH YOURS --

        // Create a command with a name and a description
        ServerCommand introduceCommand = new ServerCommand("iam", "This command is for to introduce yourself.");
        // We want to make this command available in only one category
        introduceCommand.addCategoryLimitation(CategoryLimitation.with(myServer, true, api.getChannelCategoryById(787365901552451595L).get()));
        // But we have two channels in that category where the command should not to work
        introduceCommand.addChannelLimitation(ChannelLimitation.with(myServer, false, api.getTextChannelById(787366035207618573L).get()));
        introduceCommand.addChannelLimitation(ChannelLimitation.with(myServer, false, api.getTextChannelById(787366059643502644L).get()));
        // This command is only for users who have this role
        introduceCommand.addRoleLimitation(RoleLimitation.with(myServer, true, api.getRoleById(787366309561368606L).get()));
        // Let's create our command's first argument.
        ValueArgument nameArgument = new ValueArgument("fullname", "Your first and last name separated with comma (Firstname,Lastname)", SlashCommandOptionType.STRING);
        // This argument only accepts two word separated with comma and each word started with capitalized letter.
        nameArgument.validate("(?<first>[A-Z][a-z]+),(?<last>[A-Z][a-z]+)");
        // We want to know the user's gender so make an argument that has two acceptable values.
        ComboArgument genderArgument = new ComboArgument("gender", "Your gender", SlashCommandOptionType.INTEGER);
        genderArgument.addChoice("male", 0);
        genderArgument.addChoice("female", 1);
        // How old is the user? The user can give numbers from 0 to 99.
        NumberArgument ageArgument = new NumberArgument("age", "Your age (number between 0 and 99)");
        ageArgument.setRange(0, 99);
        // Let's ask the user to mention his or her best friend,
        MentionArgument bestFriendArgument = new MentionArgument("bestfriend", "Your best friend on Discord");
        // to mention his or her favourite channel on this server
        ChannelArgument favouriteChannelArgument = new ChannelArgument("favouritechannel", "Your favourite channel on this server");
        // and to send us a funny picture's url, that is just an optional argument
        URLArgument funnyPictureArgument = new URLArgument("funnypicture", "A funny picture's url");
        funnyPictureArgument.setOptional();
        // Add arguments to the command
        introduceCommand.addArgument(nameArgument, genderArgument, ageArgument, bestFriendArgument, favouriteChannelArgument, funnyPictureArgument);
        // Let's make an action listener where we can listen for inputs.
        // user is the sender, args is an array of the converted arguments and responder is a class that manages responses
        introduceCommand.setAction((sender, args, responder) -> {
            // ValueArgument return as a Matcher by default
            Matcher fullName = args[0].get();
            String firstName = fullName.group("first");
            String lastName = fullName.group("last");

            // The gender contains integer values based on the key
            int gender = args[1].get();

            // The age from a NumberArgument so it's a number by default
            // It has been validated so range checking is unnecessary
            int age = args[2].get();

            User bestFriend = args[3].get();
            Channel favouriteChannel = args[4].get();

            // URL arguments are also getting converted into the corresponding type
            // This is an optional argument, so we need to check that this have been specified
            URL funnyPictureUrl = args.length < 6 ? null : args[5].get();

            // Something that uses all the arguments
            MessageBuilder responseMessage = new MessageBuilder();
            responseMessage.append("Hi " + firstName + " " + lastName + "! You are very " + (gender == 0 ? "handsome" : "beautiful") + " :heart: As I can see you are " + (age >= 10 && age < 20 ? "" : "not ") + "a teenager.");
            if (bestFriend.isBot()) {
                responseMessage.append(" What? Did you know that your best friend is a BOT?");
            }
            if (favouriteChannel.getId() == 791658134153330718L) {
                responseMessage.append(" That's my favourite channel too!");
            }
            if (funnyPictureUrl != null) {
                responseMessage.append(" Here is your funny picture: " + funnyPictureUrl.toString());
            }

            // Send the response message to the user
            responder.respondNow()
                    .setContent(responseMessage.getStringBuilder().toString())
                    .respond();
        });
        // And don't forget to register the command on the server(s). (I always forget it and never know what's wrong :D)
        CommandHandler.registerCommand(introduceCommand, myServer);
    }

}
