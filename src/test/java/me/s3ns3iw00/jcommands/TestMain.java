package me.s3ns3iw00.jcommands;

import me.s3ns3iw00.jcommands.argument.Argument;
import me.s3ns3iw00.jcommands.argument.type.RegexArgument;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.util.regex.Matcher;

public class TestMain {

    public static void main(String[] launchArgs) {
        DiscordApi api = new DiscordApiBuilder()
                .setToken("TOKEN HERE") // <-- Paste your bot TOKEN here
                .setAllIntents()
                .login()
                .join();
        System.out.println("Invite the bot with the following url: " + api.createBotInvite());

        // Initiate the listener.
        // -- DON'T FORGET TO REPLACE THIS WITH YOUR SERVER ID --
        MessageCommandHandler.setServer(api.getServerById(787364551598407700L).get());

        // Registering the error listener
        MessageCommandHandler.setOnError((type, cmd, sender, msg) -> {
            String errorMessage = null;
            switch (type) {
                case INVALID_COMMAND:
                    errorMessage = "No such command! :face_with_raised_eyebrow:";
                    break;
                case BAD_ARGUMENTS:
                    errorMessage = "Something went wrong! :face_with_raised_eyebrow: Usage: " + cmd.getUsage();
                    break;
                case NO_PERMISSION:
                    errorMessage = "Sorry but you don't have the power to use this command! :face_with_raised_eyebrow:";
                    break;
                case BAD_CATEGORY:
                case BAD_CHANNEL:
                    errorMessage = "I'm not sure about that this is the best place to use this command. :face_with_raised_eyebrow:";
                    break;
            }
            if (msg.isPrivateMessage()) {
                sender.sendMessage(errorMessage);
            } else {
                msg.getChannel().sendMessage(errorMessage);
            }
        });

        // Creating a command
        // -- DON'T FORGET TO REPLACE CATEGORY AND CHANNEL IDs WITH YOURS --

        // Create the command what is will only be available in the server
        Command introduceCommand = new Command("iam", CommandType.SERVER);
        // We want to make this command available in only one category
        introduceCommand.allowedCategory(api.getChannelCategoryById(787365901552451595L).get());
        // But we have two channels in that category where the command should not to work
        introduceCommand.notAllowed(api.getTextChannelById(787366035207618573L).get(), api.getTextChannelById(787366059643502644L).get());
        // This command is only for users who has this role
        introduceCommand.roles(false, api.getRoleById(787366309561368606L).get());
        // Let's create our command's first argument. This argument only accepts letters with a capital letter at the beginning.
        introduceCommand.arguments(new RegexArgument("Firstname,Lastname", "(?<first>[A-Z][a-z]+),(?<last>[A-Z][a-z]+)"));
        // We want to know the user's gender so make an argument that has two acceptable values. (One of them or both could be a regex argument or something else too)
        introduceCommand.arguments(new Argument("male"), new Argument("female"));
        // And lastly the age. The user can give numbers from 0 to 99.
        introduceCommand.arguments(new RegexArgument("your age", "([0-9]|[1-9][0-9])", Integer.class));
        // Let's make an action listener where we can listen for inputs. The difference between raw and args is that when you use RegexArgument you can get the Matcher from the argument so you can play with groups easily.
        introduceCommand.action((sender, raw, args, msg) -> {
            Matcher fullName = args[0].get();
            String firstName = fullName.group("first");
            String lastName = fullName.group("last");

            int age = args[2].get();
            msg.getChannel().sendMessage("Hi " + firstName + " " + lastName + "! As I can see you are " + (age < 20 ? "" : "not " + "a teenager."));
        });
        // And don't forget to register the command. (I always forget it and never know what's wrong :D)
        MessageCommandHandler.registerCommand(introduceCommand);
    }

}
